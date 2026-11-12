package es.laboral.desin.componentes;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
public class LimitedTextField extends TextField {
// 1. CONSTANTES POR DEFECTO
public static final int DEFAULT_MAX_LENGTH = 10;
public static final Color DEFAULT_LIMIT_COLOR = Color.web("#ffcdd2");
private static final PseudoClass LIMIT_REACHED_PSEUDO_CLASS =
PseudoClass.getPseudoClass("limit-reached");
// 2. PROPIEDADES JAVAFX
private final IntegerProperty maxLength =
new SimpleIntegerProperty(this, "maxLength", DEFAULT_MAX_LENGTH);
private final ObjectProperty<Color> limitColor =
new SimpleObjectProperty<>(this, "limitColor", DEFAULT_LIMIT_COLOR);
private final ReadOnlyBooleanWrapper limitReached =
new ReadOnlyBooleanWrapper(this, "limitReached", false);
private final ObjectProperty<EventHandler<LimitEvent>> onLimitReached =
new ObjectPropertyBase<>() {
@Override protected void invalidated() {
setEventHandler(LimitEvent.LIMIT_REACHED, get());
}
@Override public Object getBean() { return LimitedTextField.this; }
@Override public String getName() { return "onLimitReached"; }
};
// Hoja de estilos propia del componente (regla del color de límite)
private String colorStylesheet;
// 3. CONSTRUCTORES
public LimitedTextField() { this(""); }
public LimitedTextField(String text) {
super();
initialize();
setText(text); // pasa por el filtro: si no cabe, se recorta
updateLimitState(); // estado inicial, aunque el texto no cambie
}
// 4. COMPORTAMIENTO PRINCIPAL (Filtro e Intercepción)
private void initialize() {
getStyleClass().add("limited-text-field");
// 4a. Filtro: intercepta todo cambio (teclear, pegar y setText)
setTextFormatter(new TextFormatter<String>(change -> {
int max = getMaxLength();
int newLength = change.getControlNewText().length();
if (newLength <= max) return change;
String inserted = change.getText();
int excess = newLength - max;
int keep = Math.max(0, inserted.length() - excess);
String discarded = inserted.substring(keep);
change.setText(inserted.substring(0, keep));
int caret = change.getRangeStart() + keep;
change.selectRange(caret, caret);
fireEvent(new LimitEvent(LimitEvent.LIMIT_REACHED, max, discarded));
return change;
}));
// 4b. Si se reduce el límite, se recorta el texto que ya había
maxLength.addListener((obs, oldMax, newMax) -> {
int max = Math.max(0, newMax.intValue());
if (getText() != null && getText().length() > max) {
setText(getText().substring(0, max));
}
updateLimitState();
});
textProperty().addListener((obs, oldText, newText) ->
updateLimitState());
// 4c. Si cambia el color, se regenera la hoja de estilos del componente
limitColor.addListener((obs, oldColor, newColor) ->
updateColorStylesheet());
updateColorStylesheet();
}
// 5. CONTROL VISUAL Y ESTADOS
// El componente NO usa setStyle(): el style queda libre para quien lo use
private void updateLimitState() {
int length = getText() == null ? 0 : getText().length();
boolean reached = length >= getMaxLength();
limitReached.set(reached);
// Activa o desactiva la pseudoclase :limit-reached para el CSS
pseudoClassStateChanged(LIMIT_REACHED_PSEUDO_CLASS, reached);
}
// Genera una hoja de estilos (data URI) con la regla del color elegido
private void updateColorStylesheet() {
if (colorStylesheet != null) {
getStylesheets().remove(colorStylesheet);
colorStylesheet = null;
}
Color c = getLimitColor();
if (c != null) {
String css = ".limited-text-field:limit-reached {"
+ " -fx-control-inner-background: " + toWebColor(c) + "; }";
byte[] bytes = css.getBytes(StandardCharsets.UTF_8);
colorStylesheet = "data:text/css;base64,"
+ Base64.getEncoder().encodeToString(bytes);
getStylesheets().add(colorStylesheet);
}
}
private static String toWebColor(Color c) {
return String.format("#%02x%02x%02x",
(int) Math.round(c.getRed() * 255),
(int) Math.round(c.getGreen() * 255),
(int) Math.round(c.getBlue() * 255));
}
// 6. PATRÓN DE MÉTODOS ACCESORES
public final IntegerProperty maxLengthProperty() { return maxLength; }
public final int getMaxLength() { return maxLength.get(); }
public final void setMaxLength(int value) { maxLength.set(value); }
public final ObjectProperty<Color> limitColorProperty() {
return limitColor;
}
public final Color getLimitColor() { return limitColor.get(); }
public final void setLimitColor(Color value) { limitColor.set(value); }
public final ReadOnlyBooleanProperty limitReachedProperty() {
return limitReached.getReadOnlyProperty();
}
public final boolean isLimitReached() { return limitReached.get(); }
public final ObjectProperty<EventHandler<LimitEvent>>
onLimitReachedProperty() {
return onLimitReached;
}
public final EventHandler<LimitEvent> getOnLimitReached() {
return onLimitReached.get();
}
public final void setOnLimitReached(EventHandler<LimitEvent> handler) {
onLimitReached.set(handler);
}
}

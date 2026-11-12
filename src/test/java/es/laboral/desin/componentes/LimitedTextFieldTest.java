package es.laboral.desin.componentes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javafx.css.PseudoClass;
class LimitedTextFieldTest {
private static final PseudoClass LIMIT_REACHED =
PseudoClass.getPseudoClass("limit-reached");
@BeforeAll
static void arrancarJavaFX() {
FxTestSupport.iniciarJavaFX(); // Prepara el entorno JavaFX para el test
}
@Test
void valoresPorDefecto() {
LimitedTextField campo = new LimitedTextField();
// Comprobamos que arranca con límite 10 y el color por defecto
assertEquals(10, campo.getMaxLength());
assertEquals(LimitedTextField.DEFAULT_LIMIT_COLOR,
campo.getLimitColor());
}
@Test
void noPasaDelLimite() {
LimitedTextField campo = new LimitedTextField();
campo.setMaxLength(3); // Cambiamos el límite a 3
campo.appendText("abc"); // Insertamos 3 caracteres
campo.appendText("d"); // Intentamos meter el cuarto
// El valor final debe seguir siendo "abc", ignorando la "d"
assertEquals("abc", campo.getText());
}
@Test
void pegarTextoLargoAvisaConElTextoDescartado() {
LimitedTextField campo = new LimitedTextField();
campo.setMaxLength(3);
String[] descartado = new String[1];
campo.setOnLimitReached(e -> descartado[0] = e.getDiscarded());
campo.appendText("abcdef"); // Simula pegar 6 caracteres de golpe
assertEquals("abc", campo.getText());
assertEquals("def", descartado[0]); // El evento informa del descarte
}
@Test
void alLlegarAlLimiteCambiaElEstado() {
LimitedTextField campo = new LimitedTextField();
campo.setMaxLength(3);
campo.appendText("ab");
assertFalse(campo.isLimitReached());
campo.appendText("c");
assertTrue(campo.isLimitReached());
// La pseudoclase :limit-reached está activa para el CSS
assertTrue(campo.getPseudoClassStates().contains(LIMIT_REACHED));
campo.deletePreviousChar();
assertFalse(campo.isLimitReached());
}
@Test
void setTextDesdeCodigoTambienPasaPorElFiltro() {
// 15 caracteres con el límite por defecto (10)
LimitedTextField campo = new LimitedTextField("123456789012345");
assertEquals("1234567890", campo.getText());
campo.setText("abcdefghijklmnop");
assertEquals("abcdefghij", campo.getText());
}
@Test
void reducirElLimiteRecortaElTexto() {
LimitedTextField campo = new LimitedTextField("abcdef");
campo.setMaxLength(2);
assertEquals("ab", campo.getText());
assertTrue(campo.isLimitReached());
}
@Test
void noPisaElEstiloDelUsuario() {
LimitedTextField campo = new LimitedTextField();
campo.setMaxLength(2);
campo.setStyle("-fx-font-size: 18px;");
campo.appendText("ab"); // Llega al límite
campo.deletePreviousChar(); // Sale del límite
assertEquals("-fx-font-size: 18px;", campo.getStyle());
}
}
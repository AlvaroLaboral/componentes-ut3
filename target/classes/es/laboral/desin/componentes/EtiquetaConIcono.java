import javafx.scene.layout.HBox;
package es.laboral.desin.componentes;

/**
 * Clase EtiquetaConIcono.java
 *
 * @author Alvaro Blanco Marcos
 * @version 1.0
 */
public class EtiquetaConIcono extends HBox {
	@FXML
	private ImageView imagen; // mismo nombre que fx:id
	@FXML
	private Label etiqueta;

	/** Constructor sin parámetros: OBLIGATORIO para FXML y Scene Builder. */
	public EtiquetaConIcono() {
		FXMLLoader loader = new FXMLLoader(EtiquetaConIcono.class.getResource("EtiquetaConIcono.fxml"));
		loader.setRoot(this); // <fx:root> = este objeto
		loader.setController(this); // los @FXML se inyectan aquí
		try {
			loader.load();
		} catch (IOException e) {
			throw new UncheckedIOException("No se pudo cargar EtiquetaConIcono.fxml", e);
		}
		getStyleClass().add("etiqueta-con-icono");
	}

	/** Constructor de conveniencia para crearlo desde Java. */
	public EtiquetaConIcono(String texto) {
		this();
		setTexto(texto);
	}
}

public static final String TEXTO_POR_DEFECTO = "Etiqueta";
private final StringProperty texto =
 new SimpleStringProperty(this, "texto", TEXTO_POR_DEFECTO);
public final StringProperty textoProperty() { return texto; }
public final String getTexto() { return texto.get(); }
public final void setTexto(String value) { texto.set(value); }
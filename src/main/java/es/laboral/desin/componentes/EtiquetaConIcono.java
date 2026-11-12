package es.laboral.desin.componentes;

import javafx.scene.image.Image;
import java.io.IOException;
import java.io.UncheckedIOException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;



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

	public static final String TEXTO_POR_DEFECTO = "Etiqueta";
	private final StringProperty texto = new SimpleStringProperty(this, "texto", TEXTO_POR_DEFECTO);

	public final StringProperty textoProperty() {
		return texto;
	}

	public final String getTexto() {
		return texto.get();
	}

	public final void setTexto(String value) {
		texto.set(value);
	}
	
	public static final double TAMANO_ICONO_POR_DEFECTO = 20.0;

	private final ObjectProperty<Image> icono =
			new SimpleObjectProperty<>(this, "icono", null);
	private final DoubleProperty tamanoIcono =
			new SimpleDoubleProperty(this, "tamanoIcono", TAMANO_ICONO_POR_DEFECTO);

	public final ObjectProperty<Image> iconoProperty() { return icono; }
	public final Image getIcono() { return icono.get(); }
	public final void setIcono(Image value) { icono.set(value); }

	public final DoubleProperty tamanoIconoProperty() { return tamanoIcono; }
	public final double getTamanoIcono() { return tamanoIcono.get(); }
	public final void setTamanoIcono(double value) { tamanoIcono.set(value); }
	
	
	@FXML			
	private void initialize() {
	 etiqueta.textProperty().bind(texto);
	 imagen.imageProperty().bind(icono);
	 imagen.fitWidthProperty().bind(tamanoIcono);
	 imagen.fitHeightProperty().bind(tamanoIcono);
	 // sin icono, la imagen no se ve NI ocupa espacio
	 imagen.visibleProperty().bind(icono.isNotNull());
	 imagen.managedProperty().bind(icono.isNotNull());
	}
	
}




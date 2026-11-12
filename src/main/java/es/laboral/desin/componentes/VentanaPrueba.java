package es.laboral.desin.componentes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VentanaPrueba extends Application {
	@Override
	public void start(Stage stage) {
// Campo 1: límite de 5 y color por defecto (rosa claro)
		LimitedTextField campo = new LimitedTextField();
		campo.setMaxLength(5);
		campo.setOnLimitReached(e -> System.out
				.println("AVISO: máximo " + e.getMaxLength() + " caracteres. Descartado: '" + e.getDiscarded() + "'"));

//Campo 2: límite de 3, color propio y un estilo del usuario
//(el componente debe respetarlo)
		LimitedTextField campo2 = new LimitedTextField();
		campo2.setMaxLength(3);
		campo2.setLimitColor(Color.LIGHTBLUE);
		campo2.setStyle("-fx-font-size: 16px;");
		VBox raiz = new VBox(10, new Label("Límite 5 (color por defecto):"), campo,
				new Label("Límite 3 (azul claro, fuente de 16 px):"), campo2);
		raiz.setPadding(new Insets(20));
		Scene escena = new Scene(raiz, 400, 220);
//Hoja de estilos de la aplicación: usa la pseudoclase del componente
		String css = getClass().getResource("prueba.css").toExternalForm();
		escena.getStylesheets().add(css);
		stage.setTitle("Prueba de LimitedTextField (Java 25)");
		stage.setScene(escena);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}
}
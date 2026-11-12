/**
 * Clase AltaSocioController.java
 *
 * @author Alvaro Blanco Marcos
 */
package es.proyecto.di.AltaSocio;

import java.util.HashSet;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AltaSocioController {

	private final Set<String> dnisRegistrados = new HashSet<>(Set.of("12345678Z"));

	@FXML
	private Button btnCancelar;

	@FXML
	private ComboBox<String> cbProvincia;

	@FXML
	private CheckBox chkBoletin;

	@FXML
	private DatePicker dpFechaNacimiento;

	@FXML
	private Label lblApellidos;

	@FXML
	private Label lblCorreo;

	@FXML
	private Label lblCuota;

	@FXML
	private Label lblDni;

	@FXML
	private Label lblFecha;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblObservaciones;

	@FXML
	private Label lblProvincia;

	@FXML
	private Label lblTelefono;

	@FXML
	private RadioButton rbFamiliar;

	@FXML
	private RadioButton rbGeneral;

	@FXML
	private RadioButton rbInfantil;

	@FXML
	private ToggleGroup tipoCuota;

	@FXML
	private TextArea taObservaciones;

	@FXML
	private TextField txtApellidos;

	@FXML
	private TextField txtCorreo;

	@FXML
	private TextField txtDni;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtTelefono;

	@FXML
	private Label lblPostal;

	@FXML
	private TextField txtPostal;

	@FXML
	private Label errNombre;

	@FXML
	private Label errApellidos;

	@FXML
	private Label errDni;

	@FXML
	private Label errCorreo;

	@FXML
	private Label errPostal;

	@FXML
	private void initialize() {
		lblNombre.setLabelFor(txtNombre);
		lblApellidos.setLabelFor(txtApellidos);
		lblDni.setLabelFor(txtDni);
		lblFecha.setLabelFor(dpFechaNacimiento);
		lblTelefono.setLabelFor(txtTelefono);
		lblCorreo.setLabelFor(txtCorreo);
		lblProvincia.setLabelFor(cbProvincia);
		lblCuota.setLabelFor(rbGeneral);
		lblObservaciones.setLabelFor(taObservaciones);
		lblPostal.setLabelFor(txtPostal);

		errNombre.managedProperty().bind(errNombre.visibleProperty());
		errApellidos.managedProperty().bind(errApellidos.visibleProperty());
		errDni.managedProperty().bind(errDni.visibleProperty());
		errCorreo.managedProperty().bind(errCorreo.visibleProperty());
		errPostal.managedProperty().bind(errPostal.visibleProperty());

		cbProvincia.getItems().addAll("A Coruña", "Álava", "Albacete", "Alicante", "Almería", "Asturias", "Ávila",
				"Badajoz", "Barcelona", "Bizkaia", "Burgos", "Cáceres", "Cádiz", "Cantabria", "Castellón", "Ceuta",
				"Ciudad Real", "Córdoba", "Cuenca", "Gipuzkoa", "Girona", "Granada", "Guadalajara", "Huelva", "Huesca",
				"Illes Balears", "Jaén", "La Rioja", "Las Palmas", "León", "Lleida", "Lugo", "Madrid", "Málaga",
				"Melilla", "Murcia", "Navarra", "Ourense", "Palencia", "Pontevedra", "Salamanca",
				"Santa Cruz de Tenerife", "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia",
				"Valladolid", "Zamora", "Zaragoza");

		txtTelefono.setTextFormatter(
				new TextFormatter<String>(cambio -> cambio.getControlNewText().matches("\\d{0,9}") ? cambio : null));

		txtDni.setTextFormatter(new TextFormatter<String>(cambio -> {
			String nuevo = cambio.getControlNewText().toUpperCase();
			if (nuevo.matches("\\d{0,8}[A-Z]?")) {
				cambio.setText(cambio.getText().toUpperCase());
				return cambio;
			}
			return null;
		}));

		txtPostal.setTextFormatter(
				new TextFormatter<String>(cambio -> cambio.getControlNewText().matches("\\d{0,5}") ? cambio : null));
	}

	@FXML
	void darDeAlta(ActionEvent event) {
		if (!validar()) {
			lblEstado.setText("Revisa los campos marcados con ⚠.");
		    return;
		}
		if (dnisRegistrados.contains(txtDni.getText())) {
			ButtonType corregir = new ButtonType("Corregir el DNI", ButtonBar.ButtonData.CANCEL_CLOSE);
			ButtonType empezar = new ButtonType("Empezar otra alta", ButtonBar.ButtonData.OTHER);

			Alert aviso = new Alert(Alert.AlertType.WARNING, "", corregir, empezar);
			aviso.initOwner(txtDni.getScene().getWindow());
			aviso.setTitle("DNI ya registrado");
			aviso.setHeaderText("Ya hay un socio con el DNI " + txtDni.getText() + ".");
			aviso.setContentText("Corrige el DNI si te has equivocado, o empieza otra alta con el formulario vacío.");
			((Button) aviso.getDialogPane().lookupButton(corregir)).setDefaultButton(true);

			if (aviso.showAndWait().orElse(corregir) == empezar) {
				limpiar(null);
			} else {
				txtDni.requestFocus();
				txtDni.selectAll();
			}
			return;
		}

		dnisRegistrados.add(txtDni.getText());

		String nombre = txtNombre.getText() + " " + txtApellidos.getText();
		limpiar(null);
		lblEstado.setText("✔ Alta completada: " + nombre + ". Puedes dar de alta a otra persona.");
	}

	@FXML
	void limpiar(ActionEvent event) {
		txtNombre.clear();
		txtApellidos.clear();
		txtDni.clear();
		dpFechaNacimiento.setValue(null);
		txtTelefono.clear();
		txtCorreo.clear();
		txtPostal.clear();
		cbProvincia.getSelectionModel().clearSelection();
		rbGeneral.setSelected(true);
		chkBoletin.setSelected(false);
		taObservaciones.clear();

		quitarError(txtNombre, errNombre);
		quitarError(txtApellidos, errApellidos);
		quitarError(txtDni, errDni);
		quitarError(txtCorreo, errCorreo);
		quitarError(txtPostal, errPostal);

		txtNombre.requestFocus();
	}

	@FXML
	void cancelar(ActionEvent event) {
		Stage ventana = (Stage) btnCancelar.getScene().getWindow();

		if (hayDatosEscritos()) {
			ButtonType seguir = new ButtonType("Seguir editando", ButtonBar.ButtonData.CANCEL_CLOSE);
			ButtonType salir = new ButtonType("Salir sin dar de alta", ButtonBar.ButtonData.OK_DONE);

			Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION, "", seguir, salir);
			confirmar.initOwner(ventana);
			confirmar.setTitle("Cancelar alta");
			confirmar.setHeaderText("¿Salir sin dar de alta al socio?");
			confirmar.setContentText("Se perderán los datos que has escrito.");
			((Button) confirmar.getDialogPane().lookupButton(seguir)).setDefaultButton(true);

			if (confirmar.showAndWait().orElse(seguir) != salir) {
				return;
			}
		}
		ventana.close();
	}

	@FXML
	private Label lblEstado;

	private boolean hayDatosEscritos() {
		return !txtNombre.getText().isBlank() || !txtApellidos.getText().isBlank() || !txtDni.getText().isBlank()
				|| dpFechaNacimiento.getValue() != null || !txtTelefono.getText().isBlank()
				|| !txtCorreo.getText().isBlank() || !txtPostal.getText().isBlank() || cbProvincia.getValue() != null
				|| !taObservaciones.getText().isBlank();
	}

	private void mostrarError(TextField campo, Label error, String mensaje) {
		error.setText("⚠ " + mensaje);
		error.setVisible(true);
		campo.setStyle("-fx-border-color: #c62828; -fx-border-radius: 3;");
	}

	private void quitarError(TextField campo, Label error) {
		error.setVisible(false);
		campo.setStyle("");
	}

	private boolean validar() {
		boolean ok = true;

		if (txtNombre.getText().isBlank()) {
			mostrarError(txtNombre, errNombre, "Escribe el nombre. Es obligatorio.");
			ok = false;
		} else {
			quitarError(txtNombre, errNombre);
		}

		if (txtApellidos.getText().isBlank()) {
			mostrarError(txtApellidos, errApellidos, "Escribe los apellidos. Son obligatorios.");
			ok = false;
		} else {
			quitarError(txtApellidos, errApellidos);
		}

		String dni = txtDni.getText();
		if (dni.isBlank()) {
			mostrarError(txtDni, errDni, "Escribe el DNI. Es obligatorio.");
			ok = false;
		} else if (!dni.matches("\\d{8}[A-Z]")) {
			mostrarError(txtDni, errDni, "El DNI tiene 8 números y una letra. Ejemplo: 12345678Z.");
			ok = false;
		} else if (!letraDniCorrecta(dni)) {
			mostrarError(txtDni, errDni, "La letra no corresponde a esos números. Revisa el DNI.");
			ok = false;
		} else {
			quitarError(txtDni, errDni);
		}

		String correo = txtCorreo.getText();
		if (!correo.isBlank() && !correo.matches("[^@\\s]+@[^@\\s]+\\.[a-zA-Z]{2,}")) {
			mostrarError(txtCorreo, errCorreo, "El correo no es válido. Ejemplo: nombre@correo.es.");
			ok = false;
		} else {
			quitarError(txtCorreo, errCorreo);
		}

		String cp = txtPostal.getText();
		if (!cp.isBlank() && !cp.matches("\\d{5}")) {
			mostrarError(txtPostal, errPostal, "El código postal tiene 5 números. Ejemplo: 33201.");
			ok = false;
		} else {
			quitarError(txtPostal, errPostal);
		}

		return ok;
	}

	private boolean letraDniCorrecta(String dni) {
		String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		int numero = Integer.parseInt(dni.substring(0, 8));
		return dni.charAt(8) == letras.charAt(numero % 23);
	}
}

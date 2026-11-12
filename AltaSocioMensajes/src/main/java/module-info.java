module es.proyecto.di.AltaSocio {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens es.proyecto.di.AltaSocio to javafx.fxml;
    exports es.proyecto.di.AltaSocio;
}

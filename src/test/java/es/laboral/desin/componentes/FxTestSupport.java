package es.laboral.desin.componentes;

import javafx.application.Platform;

final class FxTestSupport {
	private static boolean iniciado = false;

	private FxTestSupport() {
	}

	static synchronized void iniciarJavaFX() {
		if (iniciado)
			return;
		try {
			Platform.startup(() -> {
			});
		} catch (IllegalStateException yaIniciado) {
// El entorno ya estaba iniciado, no hacemos nada
		}
		iniciado = true;
	}
}

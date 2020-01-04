package de.andwari.tournamentfx.startup;

import java.net.URL;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import de.andwari.tournamentcore.database.DatabaseManager;
import de.andwari.tournamentfx.password.PasswordHandler;
import de.andwari.tournamentfx.util.FxmlResource;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class AppInitializer {

	@Inject
	FXMLLoader fxmlLoader;

	@Inject
	private PasswordHandler pwHandler;

	public void start(@Observes @StartupScene Stage stage) {
		DatabaseManager.init();
		pwHandler.initDefaultMasterPassword();
		try {

			URL fxmlRes = getClass().getClassLoader().getResource(FxmlResource.MAIN.getPath());
			fxmlLoader.setLocation(fxmlRes);
			TabPane root = fxmlLoader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

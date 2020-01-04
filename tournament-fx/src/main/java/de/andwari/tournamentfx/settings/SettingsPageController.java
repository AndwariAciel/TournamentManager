package de.andwari.tournamentfx.settings;

import java.io.File;

import javax.inject.Inject;

import de.andwari.tournamentfx.password.PasswordHandler;
import de.andwari.tournamentfx.settings.control.DatabaseBackupHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SettingsPageController {

	@FXML
	private BorderPane masterPane;

	@FXML
	private Button btnDatabaseBackup, btnDatabaseRestore, btnChangeMasterPassword;

	@FXML
	private TextField tfBackupPath;

	@Inject
	private DatabaseBackupHandler backupHandler;

	@Inject
	private PasswordHandler pwHandler;

	@FXML
	public void initialize() {
	}

	public void chooseBackupPath() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(getStage());
		if (selectedDirectory != null)
			tfBackupPath.setText(selectedDirectory.getAbsolutePath());
	}

	private Stage getStage() {
		return (Stage) masterPane.getScene().getWindow();
	}

	public void backupDatabase() {
		backupHandler.backupDatabase(tfBackupPath.getText());
	}

	public void restoreDatabase() {
		backupHandler.restoreDatabase(getStage());
	}

	public void changeMasterPassword() {
		pwHandler.changeMasterPassword();
	}
}

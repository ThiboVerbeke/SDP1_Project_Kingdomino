package gui;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.GeboortejaarException;
import exceptions.GebruikersnaamInGebruikException;
import exceptions.GebruikersnaamTeKortLeeg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegistreerSpelerController extends Pane {
	private DomeinController dc;
	private Stage stage;
	private ResourceBundle messages;
	@FXML
	private Label lblGeboortejaar;

	@FXML
	private Label lblNaam;

	@FXML
	private Label lblRegistreren;

	@FXML
	private Button btnRegistreer;
	@FXML
	private Button btnTerug;
	@FXML
	private TextField txtfGeboortejaar;

	@FXML
	private TextField txtfGebruikersnaam;

	private void loadFxmlScreen(String naam) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(naam));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	public RegistreerSpelerController(Stage stage, ResourceBundle messages, DomeinController dc) {
		this.dc = dc;
		this.stage = stage;
		this.messages = messages;
		loadFxmlScreen("RegistreerSpelerFXML.fxml");

		lblRegistreren.setText(messages.getString("btnRegistreren"));
		btnRegistreer.setText(messages.getString("btnRegistreren"));
		lblGeboortejaar.setText(messages.getString("lblGeboortejaar"));
		lblNaam.setText(messages.getString("lblNaam"));
		btnTerug.setText(messages.getString("btnTerug"));
	}

	@FXML
	public void terug(ActionEvent event) {
		try {

			Scene scene = new Scene(new MaakKeuzeController(messages, stage, dc), 600, 400);

			this.stage.setScene(scene);
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void Registreer(ActionEvent event) {
		 try {
		        dc.registreerSpeler(txtfGebruikersnaam.getText(), Integer.parseInt(txtfGeboortejaar.getText()));
		        
		        String confirmationMessage = messages.getString("registration_successful");
		        showConfirmationDialog(confirmationMessage);
		    } catch (GebruikersnaamInGebruikException e) {
		    	String errorMessage = messages.getString("username_in_use");
		        showErrorDialog(errorMessage);
		    }catch(GebruikersnaamTeKortLeeg e) {
		    	String errorMessage = messages.getString("username_too_short");
		    	showErrorDialog(errorMessage);
		    }catch(GeboortejaarException e) {
		    	String errorMessage = messages.getString("birthyear_invalid");
		    	showErrorDialog(errorMessage);
		    }
	}
	private void showErrorDialog(String message) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	private void showConfirmationDialog(String message) {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Info");
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
}
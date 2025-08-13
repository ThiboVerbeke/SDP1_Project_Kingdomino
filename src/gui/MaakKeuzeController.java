package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import domein.DomeinController;
import dto.SpelerDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MaakKeuzeController extends Pane {
	private ResourceBundle messages;
	private DomeinController dc;
	private Stage stage;

	@FXML
	private Button btnAfsluiten;
	@FXML
	private Button btnRegistreren;
	@FXML
	private Button btnTerug;

	@FXML
	private Button btnStarten;

	@FXML
	private Label lblMaakKeuzeTitel;
	
    @FXML
    private ImageView imgAchtergrond;
    
	private void loadFxmlScreen(String naam) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(naam));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public MaakKeuzeController(ResourceBundle messages, Stage stage, DomeinController dc) {
		this.stage = stage;
		this.messages = messages;
		this.dc = dc;
		loadFxmlScreen("MaakKeuzeFXML.fxml");
		imageTonen();
		lblMaakKeuzeTitel.setText(messages.getString("enter_choice"));
		btnRegistreren.setText(messages.getString("btnRegistreren"));
		btnStarten.setText(messages.getString("btnSpelStarten"));
		btnAfsluiten.setText(messages.getString("btnAfsluiten"));
		btnTerug.setText(messages.getString("btnTerug"));
	}

	@FXML
	void afsluiten(ActionEvent event) {
		this.stage.close();
	}

	@FXML
	void registreren(ActionEvent event) {
		try {

			Scene scene = new Scene(new RegistreerSpelerController(stage, messages, dc), 600, 400);
			this.stage.setScene(scene);
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void terug(ActionEvent event) {
		try {
			Scene scene = new Scene(new TaalschermController(stage, dc), 600, 400);

			this.stage.setScene(scene);
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void starten(ActionEvent event) {
		List<String> beschikbareKleuren = new ArrayList<>();
		List<SpelerDTO> beschikbareSpeler = new ArrayList<>();

		List<SpelerDTO> spelerMetKleur = new ArrayList<>();
		int aantal = 1;
		try {

			Scene scene = new Scene(new KiesSpelerEnKleur(messages, stage, aantal, beschikbareKleuren,
					beschikbareSpeler, dc, spelerMetKleur), 600, 400);
			this.stage.setScene(scene);
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void imageTonen()
	{
		Image image = new Image(getClass().getResourceAsStream(
				"/images/achtergrond spelbord.png"));
		imgAchtergrond.setImage(image);	
	}
}

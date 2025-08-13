package gui;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.Taal;

public class TaalschermController extends Pane {

	private Stage stage;
	private DomeinController dc;

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

	public TaalschermController(Stage stage, DomeinController dc) {
		this.stage = stage;
		this.dc = dc;
		loadFxmlScreen("Taalscherm.fxml");
		imageTonen();
		setupImageButtons();
	}

	@FXML
	private Button btnEnglish;

	@FXML
	private Button btnNederlands;

	@FXML
	private Label lblTitel;

    @FXML
    private ImageView imgAchtergrond;

    @FXML
    private ImageView imgNL;

    @FXML
    private ImageView imgUK;
    
	@FXML
	void ChooseEnglish(MouseEvent event) {

		try {

			ResourceBundle messages = Taal.getResourceBundle("EN");
			Scene scene = new Scene(new MaakKeuzeController(messages, stage, dc), 600, 400);
			this.stage.setScene(scene);
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void kiesNederlands(MouseEvent event) {
		try {

			ResourceBundle messages = Taal.getResourceBundle("NL");
			Scene scene = new Scene(new MaakKeuzeController(messages, stage, dc), 600, 400);
			this.stage.setScene(scene);
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imageTonen()
	{
		Image image = new Image(getClass().getResourceAsStream(
				"/images/WelkomScherm.png"));
		imgAchtergrond.setImage(image);	
	}
	
	 private void setupImageButtons() {
	        // Replace buttons with image views
		  Image image = new Image(getClass().getResourceAsStream("/images/VlagUK.png"));
	      Image image2 = new Image(getClass().getResourceAsStream("/images/VlagNL.png"));

	        // Set up event handlers for image views
	      	imgUK.setImage(image);
	      	imgNL.setImage(image2);
	        imgUK.setOnMouseClicked(event -> ChooseEnglish(event));
	        imgNL.setOnMouseClicked(event -> kiesNederlands(event));       
	       

	        // Optional: Set cursor style to indicate the buttons are clickable
	        imgUK.setCursor(Cursor.HAND);
	        imgNL.setCursor(Cursor.HAND);
	    }
	
}


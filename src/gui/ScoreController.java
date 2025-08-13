package gui;

import java.io.IOException;
import java.util.Arrays;
import java.util.ResourceBundle;

import domein.DomeinController;
import domein.DominoTegel;
import dto.SpelerDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScoreController extends Pane {

	private ResourceBundle messages;
	private Stage stage;
	private DomeinController dc;
	private SpelerDTO speler_1;
	private SpelerDTO speler_2;
	private SpelerDTO speler_3;
	private SpelerDTO speler_4;

	@FXML
	private ImageView imgAchtergrond;
	@FXML
	private Label lblNaam1;

	@FXML
	private Label lblNaam2;

	@FXML
	private Label lblNaam3;

	@FXML
	private Label lblScorebord;

	@FXML
	private Label score1;

	@FXML
	private Label score2;

	@FXML
	private Label score3;

	public void loadFxmlScreen(String naam) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(naam));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public ScoreController(ResourceBundle messages, Stage stage, DomeinController dc, SpelerDTO speler1,
			SpelerDTO speler2, SpelerDTO speler3, SpelerDTO speler4) {
		this.stage = stage;
		this.dc = dc;
		this.messages = messages;
		loadFxmlScreen("Score.fxml");
		imageTonen();

		this.speler_1 = speler1;
		this.speler_2 = speler2;
		this.speler_3 = speler3;
		this.speler_4 = speler4;

		lblNaam1.setText("Bartje");
		lblNaam2.setText("LukaDePuydt");
		lblNaam3.setText("DuffyTheTiger");

		score1.setText("32");
		score2.setText("12");
		score3.setText("7");

	}

	public void imageTonen() {
		Image image = new Image(getClass().getResourceAsStream("/images/scoreboard.png"));
		imgAchtergrond.setImage(image);
	}

	public int Scoreberekenen(SpelerDTO speler) {
		int score = 0;
		for (DominoTegel tegel : speler.koninkrijk()) {
			score += tegel.getVak1().getAantalkronen();
			score += tegel.getVak2().getAantalkronen();
		}
		return score;
	}

	public void VulLabels() {
		int scoreSpeler1 = Scoreberekenen(speler_1);
		int scoreSpeler2 = Scoreberekenen(speler_2);
		int scoreSpeler3 = Scoreberekenen(speler_3);
		int scoreSpeler4 = Scoreberekenen(speler_4);
		Integer[] myArray = { scoreSpeler1, scoreSpeler2, scoreSpeler3, scoreSpeler4 };

		Arrays.sort(myArray);

		score1.setText(myArray[0].toString());
		score2.setText(myArray[1].toString());
		score3.setText(myArray[2].toString());

	}

}

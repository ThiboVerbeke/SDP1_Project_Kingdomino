
package gui;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import domein.DomeinController;
import domein.Spel;
import dto.SpelerDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.KleurEnum;

public class KiesSpelerEnKleur extends Pane {
	private int aantalSpelers = 0;
	private ResourceBundle messages;
	private Stage stage;
	private DomeinController dc;

	private List<String> beschikbareKleuren;
	private List<SpelerDTO> beschikbareSpelers;
	private List<SpelerDTO> spelerMetKleur;

	@FXML
	private Button btnVolgende;

	@FXML
	private ChoiceBox<String> choiceBoxSpelerKiezen;

	@FXML
	private ChoiceBox<String> choiceboxKleurKiezen;

	@FXML
	private Label lblKiesSpelerEnKleurTitel;

	@FXML
	private Label lblKleur;
	@FXML
	private Button btnTerug;
	@FXML
	private Label lblSpeler;

	@FXML
	private Label lblSpelerID;

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

	private void updateAvailableChoices() {
		choiceboxKleurKiezen.getItems().clear();
		choiceBoxSpelerKiezen.getItems().clear();
		/*
		 * System.out.println("Before filtering - Available colors: " +
		 * beschikbareKleuren); System.out.println("Before filtering - Chosen colors: "
		 * + gekozenKleuren);
		 * System.out.println("Before filtering - Available players: " +
		 * beschikbareSpelers); System.out.println("Before filtering - Chosen players: "
		 * + gekozenSpelers);
		 */

		if (beschikbareKleuren != null) {
			for (String kleur : beschikbareKleuren) {
				choiceboxKleurKiezen.getItems().add(kleur);
			}
		}
		if (beschikbareSpelers != null) {
			for (SpelerDTO speler : beschikbareSpelers) {
				choiceBoxSpelerKiezen.getItems().add(speler.gebruikersnaam());

			}
		}

	}

	public KiesSpelerEnKleur(ResourceBundle messages, Stage stage, int aantalSpelers, List<String> kleur,
			List<SpelerDTO> speler, DomeinController dc, List<SpelerDTO> spelerMetKleur) {

		this.stage = stage;
		this.aantalSpelers = aantalSpelers;
		this.messages = messages;
		this.dc = dc;

		this.spelerMetKleur = spelerMetKleur;
		if (aantalSpelers > 1) {
			this.beschikbareKleuren = kleur;
			this.beschikbareSpelers = speler;
		} else {
			this.beschikbareKleuren = dc.geefKleurenInTaalString(messages);
			this.beschikbareSpelers = dc.getBeschikbareSpelers();
		}

		loadFxmlScreen("KiesSpelerEnKleur.fxml");

		lblSpelerID.setText(String.valueOf(aantalSpelers));
		btnVolgende.setText(messages.getString("btnVolgende"));
		lblKiesSpelerEnKleurTitel.setText(messages.getString("lblKiesSpelerEnKleurTitel"));
		lblKleur.setText(messages.getString("lblKleur"));
		lblSpeler.setText(messages.getString("lblSpeler"));
		btnTerug.setText(messages.getString("btnTerug"));
		updateAvailableChoices();
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
	void btnVolgende(ActionEvent event) {
		String selectedSpeler = choiceBoxSpelerKiezen.getValue();
		String selectedKleur = choiceboxKleurKiezen.getValue();

		if (selectedSpeler != null && selectedKleur != null) {
			KleurEnum gekozenKleur = KleurEnum.getByTranslation(selectedKleur, messages.getLocale());
			SpelerDTO gekozenSpeler = null;

			for (SpelerDTO speler : beschikbareSpelers) {
				if (speler.gebruikersnaam().equals(selectedSpeler)) {
					gekozenSpeler = speler;
					break;
				}
			}

			if (gekozenSpeler != null && gekozenKleur != null) {
				SpelerDTO spelerMetNieuweKleur = gekozenSpeler.metKleur(gekozenKleur);
				spelerMetKleur.add(spelerMetNieuweKleur);

				beschikbareSpelers.remove(gekozenSpeler);
				beschikbareKleuren.remove(selectedKleur);

				if (aantalSpelers == 3) {

					try {
						Stage stage2 = new Stage();
						Scene scene = new Scene(new NogSpelerToevoegenController(messages, stage2, stage,
								beschikbareKleuren, beschikbareSpelers, dc, spelerMetKleur), 200, 200);
						stage2.setScene(scene);
						stage2.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (aantalSpelers == 4) {

					// maak spel aan
					Spel spel = new Spel(4, spelerMetKleur);
					try {

						Scene scene = new Scene(new SpelbordController(messages, stage, dc, spelerMetKleur, spel), 600,
								800);
						stage.setScene(scene);
						stage.show();
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					aantalSpelers++;

					try {
						Scene scene = new Scene(new KiesSpelerEnKleur(messages, stage, aantalSpelers,
								beschikbareKleuren, beschikbareSpelers, dc, spelerMetKleur), 600, 400);
						stage.setScene(scene);
						stage.show();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			} else {

				System.out.println("Ongeldige selectie.");
			}

		} else {
			System.out.println("Selecteer zowel een speler als een kleur.");
		}

	}

}

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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NogSpelerToevoegenController extends Pane
{
	private Stage stage;
	private DomeinController dc;
	private Stage stage2;
	private ResourceBundle messages;
	private List<String> beschikbareKleuren;
	private List<SpelerDTO> beschikbareSpelers;

	private List<SpelerDTO> spelerMetKleur;
	@FXML
	private Button btnJa;

	@FXML
	private Button btnNee;

	@FXML
	private Label lblToevoegenTitel;

	private void loadFxmlScreen(String naam)
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(naam));
		loader.setRoot(this);
		loader.setController(this);

		try
		{
			loader.load();
		} catch (IOException ex)
		{

			throw new RuntimeException("Failed to load FXML screen: " + naam, ex);
		}
	}

	public NogSpelerToevoegenController(ResourceBundle messages, Stage stage2, Stage stage,
			List<String> beschikbareKleuren, List<SpelerDTO> beschikbareSpelers, DomeinController dc,
			List<SpelerDTO> spelerMetKleur)
	{
		this.stage = stage;
		this.stage2 = stage2;
		this.dc = dc;
		this.spelerMetKleur = spelerMetKleur;

		this.beschikbareKleuren = beschikbareKleuren;
		this.beschikbareSpelers = beschikbareSpelers;
		this.messages = messages;
		loadFxmlScreen("NogSpelerToevoegen.fxml");
		
		lblToevoegenTitel.setText(messages.getString("lblToevoegenTitel"));
		btnJa.setText(messages.getString("btnJa"));
		btnNee.setText(messages.getString("btnNee"));
	}

	@FXML
	void sluitVenster(ActionEvent event)
	{
		stage2.close();

		// maak spel aan
		Spel spel = new Spel(3, spelerMetKleur);
		try
		{

			Scene scene = new Scene(new SpelbordController(messages, stage, dc, spelerMetKleur, spel), 800, 600);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@FXML
	void spelerWordtToegevoegd(ActionEvent event)
	{
		try
		{
			stage2.close();
			Scene scene = new Scene(new KiesSpelerEnKleur(messages, stage, 4, beschikbareKleuren, beschikbareSpelers,
					dc, spelerMetKleur), 800, 600);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e)
		{

			System.err.println("Failed to switch scene: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
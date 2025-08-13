package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import domein.DomeinController;
import domein.DominoTegel;
import domein.Spel;
import domein.Vak;
import dto.SpelerDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpelbordController extends Pane {
	private Stage stage;
	private ResourceBundle messages;
	private DomeinController dc;
	List<DominoTegel> lijstBeschikbaarStartKolom;
	List<Button> knopLijst;
	private int spelerDieAanDeBeurtIs = 1;
	private int currentPositionIndex = 0;
	private SpelerDTO speler1;
	private SpelerDTO speler2;
	private SpelerDTO speler3;
	private SpelerDTO speler4;
	private int teller;
	private int rondeTeller;
	private Set<String> spelersMetGeplaatsteTegel;
	private int newRow;
	private int newColumn;
	List<DominoTegel> koninkrijk1;
	List<DominoTegel> koninkrijk2;
	List<DominoTegel> koninkrijk3;
	List<DominoTegel> koninkrijk4;
	List<SpelerDTO> spelerMetKleur;
	List<SpelerDTO> spelerVolgorde;
	@FXML
	private Button btnStartKolom1;
	@FXML
	private Button btnStartKolom2;
	@FXML
	private Button btnStartKolom3;
	@FXML
	private Button btnStartKolom4;
	@FXML
	private Button btnVolgende;
	@FXML
	private Button btnDraaien;
	@FXML
	private GridPane gridEindkolom;
	@FXML
	private GridPane gridKoninkrijk;
	@FXML
	private GridPane gridKoninkrijkSpeler2;
	@FXML
	private GridPane gridKoninkrijkSpeler3;
	@FXML
	private GridPane gridKoninkrijkSpeler4;
	@FXML
	private GridPane gridStartkolom;
	@FXML
	private GridPane gridGeselecteerdeDominoTegel;
	@FXML
	private ImageView imageView;
	@FXML
	private Label lblSpelerDoen;
	@FXML
	private Label lblSpelerNaam;
	@FXML
	private Label lblEindkolom;
	@FXML
	private Label lblStartkolom;

	public SpelbordController(ResourceBundle messages, Stage stage, DomeinController dc, List<SpelerDTO> spelerMetKleur,
			Spel spel) {
		this.stage = stage;
		this.messages = messages;
		this.dc = dc;
		this.spelerMetKleur = spelerMetKleur;
		dc.spel = spel;
		this.spelersMetGeplaatsteTegel = new HashSet<>();
		spelerVolgorde = new ArrayList<>(this.spelerMetKleur);
		teller = 0;
		rondeTeller = 1;
		knopLijst = new ArrayList<>();
		lijstBeschikbaarStartKolom = spel.getStartKolom();
		loadFxmlScreen("SpelbordSpeler.fxml");
		geselecteerdeTegelTonen(false);
		gridKoninkrijkTonen(false);
		imageTonen();
		eersteLabel();
		vulGridKolommen(true);
		lblStartkolom.setText(messages.getString("lblStartKolom"));
		lblEindkolom.setText(messages.getString("lblEindKolom"));
		btnVolgende.setText(messages.getString("btnVolgende"));
		btnDraaien.setText(messages.getString("btnDraaien"));

	}

	public void gridKoninkrijkTonen(boolean tonen) {
		gridKoninkrijkSpeler2.setVisible(tonen);
		gridKoninkrijkSpeler3.setVisible(tonen);
		gridKoninkrijkSpeler4.setVisible(tonen);
	}

	private void loadFxmlScreen(String naam) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(naam));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException("Failed to load FXML screen: " + naam, ex);
		}
	}

	public void imageTonen() {
		Image image = new Image(getClass().getResourceAsStream(
				"/images/8749913-naadloze-textuur-gras-textuur-groen-gazon-met-stenen-illustratie-van-een-natuur-achtergrond-organisch-gras-voor-het-spel-vector.png"));
		imageView.setImage(image);
	}

	public void eersteLabel() {
		speler1 = spelerMetKleur.get(spelerDieAanDeBeurtIs - 1);
		lblSpelerNaam.setText(speler1.gebruikersnaam());
		lblSpelerDoen.setText(messages.getString("KoningPlaatsen"));
	}

	public void vulGridKolommen(boolean updateStartKolom) {
		if (updateStartKolom) {
			leegmakenLabels(gridStartkolom);
			for (int i = 0; i < dc.spel.getStartKolom().size(); i++) {

				Label startLabel1 = new Label(dc.spel.getStartKolom().get(i).getVak1().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage().toString()) + " ("
						+ dc.spel.getStartKolom().get(i).getVak1().getAantalkronen() + "Kr)");
				Label startLabel2 = new Label(dc.spel.getStartKolom().get(i).getVak2().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage().toString()) + " ("
						+ dc.spel.getStartKolom().get(i).getVak2().getAantalkronen() + "Kr)");
				gridStartkolom.add(startLabel1, 0, i);
				gridStartkolom.add(startLabel2, 1, i);
			}
			gridStartkolom.requestLayout();

			leegmakenLabels(gridEindkolom);
			for (int i = 0; i < dc.spel.getEindKolom().size(); i++) {

				Label endLabel1 = new Label(dc.spel.getEindKolom().get(i).getVak1().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage().toString()) + " ("
						+ dc.spel.getEindKolom().get(i).getVak1().getAantalkronen() + "Kr)");
				Label endLabel2 = new Label(dc.spel.getEindKolom().get(i).getVak2().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage().toString()) + " ("
						+ dc.spel.getEindKolom().get(i).getVak2().getAantalkronen() + "Kr)");
				gridEindkolom.add(endLabel1, 0, i);
				gridEindkolom.add(endLabel2, 1, i);
			}
			gridEindkolom.requestLayout();
		}

	}

	@FXML
	void btnDominotegelDraaien(ActionEvent event) {
		try {
			draaiTegel();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in btnDominotegelDraaien: " + e.getMessage());
		}
	}

	private void draaiTegel() {

		Label currentLabel = getLabelPositie(gridGeselecteerdeDominoTegel, positionMap[currentPositionIndex][0],
				positionMap[currentPositionIndex][1]);

		currentPositionIndex = (currentPositionIndex + 1) % 4;

		newRow = positionMap[currentPositionIndex][0];
		newColumn = positionMap[currentPositionIndex][1];

		if (currentLabel != null) {
			GridPane.setRowIndex(currentLabel, newRow);
			GridPane.setColumnIndex(currentLabel, newColumn);
		}
	}

	private Label getLabelPositie(GridPane grid, int row, int column) {
		for (Node child : grid.getChildren()) {
			if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == column
					&& child instanceof Label) {
				return (Label) child;
			}
		}
		return null;
	}

	private int[][] positionMap = { { 1, 2 }, { 2, 1 }, { 1, 0 }, { 0, 1 } };

	@FXML
	void volgendeSpeler(ActionEvent event) {

		if (!spelersMetGeplaatsteTegel.contains(lblSpelerNaam.getText()) && rondeTeller < 2) {
			toonAlert("Fout", messages.getString("TegelKiezen"));
			return;
		}
		spelersMetGeplaatsteTegel.add(lblSpelerNaam.getText());
		teller++;

		if (teller == spelerMetKleur.size()) {
			if (alleSpelersHebbenGeplaatst()) {
				rondeTeller++;
				if (rondeTeller == 13) {
					try {
						Scene scene = new Scene(
								new ScoreController(messages, stage, dc, speler1, speler2, speler3, speler4), 600, 400);

						stage.setScene(scene);
						stage.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return;
				}
				toonInfo("New Round",
						messages.getString("Ronde") + " " + rondeTeller + " " + messages.getString("Begint"));
				spelersMetGeplaatsteTegel.clear();
			}
			naAllesGekozen();

		} else {
			GridPane grid = switch (spelerDieAanDeBeurtIs) {
			case 1 -> gridKoninkrijk;
			case 2 -> gridKoninkrijkSpeler2;
			case 3 -> gridKoninkrijkSpeler3;
			case 4 -> gridKoninkrijkSpeler4;
			default -> null;
			};
			SpelerDTO currentPlayer = switch (spelerDieAanDeBeurtIs) {
			case 1 -> speler1;
			case 2 -> speler2;
			case 3 -> speler3;
			case 4 -> speler4;
			default -> null;
			};

			currentPositionIndex = 0;

			int gekozenTegelIndex = spelerDieAanDeBeurtIs - 1;

			speelVolgendeRonde(grid, gekozenTegelIndex, currentPlayer);

		}
	}

	private boolean alleSpelersHebbenGeplaatst() {
		return spelersMetGeplaatsteTegel.size() == spelerMetKleur.size();
	}

	public void naAllesGekozen() {
		teller = 0;
		if (spelerMetKleur.size() == 4) {
			spelerVolgorde.clear();
			spelerVolgorde.add(0, speler1);
			spelerVolgorde.add(1, speler2);
			spelerVolgorde.add(2, speler3);
			spelerVolgorde.add(3, speler4);
		}
		if (spelerMetKleur.size() == 3) {
			spelerVolgorde.clear();
			spelerVolgorde.add(0, speler1);
			spelerVolgorde.add(1, speler2);
			spelerVolgorde.add(2, speler3);

		}
		currentPositionIndex = 0;
		leegmakenLabels(gridStartkolom);
		leegmakenLabels(gridEindkolom);

		dc.veranderKolommen(spelerMetKleur.size());

		vulGridKolommen(true);

		spelerDieAanDeBeurtIs = 1;
		if (speler1 != null) {
			lblSpelerNaam.setText(speler1.gebruikersnaam());
		}

		gridKoninkrijkTonen(false);
		updateSpelerUI();
		geselecteerdeTegelTonen(false);

		speler1 = speler2 = speler3 = speler4 = null;

	}

	private void leegmakenLabels(GridPane grid) {
		List<Node> labelsToRemove = new ArrayList<>();
		for (Node node : grid.getChildren()) {
			if (node instanceof Label) {
				labelsToRemove.add(node);
			}
		}
		grid.getChildren().removeAll(labelsToRemove);
	}

	private void speelVolgendeRonde(GridPane grid, int tegelIndex, SpelerDTO speler) {

		lblSpelerNaam.setText(speler.gebruikersnaam());
		lblSpelerDoen.setText(messages.getString("TegelPlaatsen"));

		geselecteerdeTegelTonen(false);
		geselecteerdeTegelTonen(true);

		gridopvullenButton(grid, speler, tegelIndex);
		toonGeselecteerdeTegelsInGrid();

		switch (spelerDieAanDeBeurtIs) {
		case 1 -> {
			gridKoninkrijk.setVisible(true);
			updateGridKleur(gridKoninkrijk, speler1);
			gridKoninkrijkSpeler2.setVisible(false);
			gridKoninkrijkSpeler3.setVisible(false);
			gridKoninkrijkSpeler4.setVisible(false);
		}
		case 2 -> {
			gridKoninkrijk.setVisible(false);
			gridKoninkrijkSpeler2.setVisible(true);
			updateGridKleur(gridKoninkrijkSpeler2, speler2);
			gridKoninkrijkSpeler3.setVisible(false);
			gridKoninkrijkSpeler4.setVisible(false);
		}
		case 3 -> {
			gridKoninkrijkSpeler2.setVisible(false);
			gridKoninkrijk.setVisible(false);
			gridKoninkrijkSpeler3.setVisible(true);
			updateGridKleur(gridKoninkrijkSpeler3, speler3);
			gridKoninkrijkSpeler4.setVisible(false);
		}
		case 4 -> {
			gridKoninkrijkSpeler2.setVisible(false);
			gridKoninkrijk.setVisible(false);
			gridKoninkrijkSpeler3.setVisible(false);
			gridKoninkrijkSpeler4.setVisible(true);
			updateGridKleur(gridKoninkrijkSpeler4, speler4);
		}
		}
		spelerDieAanDeBeurtIs++;
	}

	@FXML
	void btnStartKolom1Druk(ActionEvent event) {
		try {

			if (!dc.spel.getStartKolom().get(0).isSelected()) {

				dc.setEerstGekozenTegel(0);
				speler1 = spelerVolgorde.get(spelerDieAanDeBeurtIs - 1);

				tegelSelectie(0, speler1);
			} else {
				toonAlert("Fout", messages.getString("TegelAlGekozen"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An error occurred in btnStartKolom1Druk.");
		}
	}

	@FXML
	void btnStartKolom2Druk(ActionEvent event) {
		try {

			if (!dc.spel.getStartKolom().get(1).isSelected()) {
				speler2 = spelerVolgorde.get(spelerDieAanDeBeurtIs - 1);

				tegelSelectie(1, speler2);
				dc.setTweedeGekozentegel(1);
			} else {
				toonAlert("Fout", messages.getString("TegelAlGekozen"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An error occurred in btnStartKolom2Druk.");
		}
	}

	@FXML
	void btnStartKolom3Druk(ActionEvent event) {
		try {

			if (!dc.spel.getStartKolom().get(2).isSelected()) {
				speler3 = spelerVolgorde.get(spelerDieAanDeBeurtIs - 1);

				tegelSelectie(2, speler3);
				dc.setDerdeGekozenTegel(2);
			} else {
				toonAlert("Fout", messages.getString("TegelAlGekozen"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An error occurred in btnStartKolom1Druk.");
		}
	}

	@FXML
	void btnStartKolom4Druk(ActionEvent event) {
		try {

			if (!dc.spel.getStartKolom().get(3).isSelected()) {
				speler4 = spelerVolgorde.get(spelerDieAanDeBeurtIs - 1);

				tegelSelectie(3, speler4);
				dc.setVierdeGekozenTegel(3);
			} else {
				toonAlert("Fout", messages.getString("TegelAlGekozen"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An error occurred in btnStartKolom1Druk.");
		}
	}

	public void geselecteerdeTegelTonen(boolean tonen) {
		btnDraaien.setVisible(tonen);
		gridGeselecteerdeDominoTegel.setVisible(tonen);
		btnVolgende.setVisible(tonen);
	}

	private void tegelSelectie(int kolomIndex, SpelerDTO speler) {

		dc.spel.getStartKolom().get(kolomIndex).setSelected(true);
		dc.kiesDominoTegel(dc.spel.getStartKolom().get(kolomIndex).getNummer(), lijstBeschikbaarStartKolom);
		dc.plaatsDominotegelInKoninkrijk(dc.spel.getStartKolom().get(kolomIndex), speler);

		updateSpelerUI();
		gaNaarVolgendeSpeler();

	}

	private void toonAlert(String title, String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void toonInfo(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void gaNaarVolgendeSpeler() {
		spelerDieAanDeBeurtIs = (spelerDieAanDeBeurtIs % spelerMetKleur.size()) + 1;
		if (spelerDieAanDeBeurtIs == 1) {
			int firstSelectedTileIndex = dc.spel.getStartKolom().indexOf(dc.getEerstGekozenTegel());
			if (spelerMetKleur.size() == 4) {
				if (speler1 != null && speler2 != null && speler3 != null && speler4 != null) {
					speelVolgendeRonde(gridKoninkrijk, firstSelectedTileIndex, speler1);
				}
			} else {
				if (speler1 != null && speler2 != null && speler3 != null) {
					speelVolgendeRonde(gridKoninkrijk, firstSelectedTileIndex, speler1);
				}
			}

		} else {
			updateSpelerUI();
		}
	}

	private void gridopvullenButton(GridPane targetGrid, SpelerDTO speler, int dominoTegelIndex) {
		int gridSize = 9;
		targetGrid.setAlignment(Pos.CENTER);

		if (dc.getGekozenTegel(dominoTegelIndex) == null) {
			System.out.println("Invalid domino tile index: " + dominoTegelIndex);
			return;
		}

		for (int row = 0; row < gridSize; row++) {
			for (int col = 0; col < gridSize; col++) {
				if (row == gridSize / 2 && col == gridSize / 2) {

					continue;
				}

				final int currentRow = row;
				final int currentCol = col;

				Button existingButton = vindKnop(targetGrid, currentRow, currentCol);
				if (existingButton != null) {

					continue;
				}

				Button button = new Button("(" + row + "," + col + ")");
				button.setId("button_" + row + "_" + col);
				button.setText("");

				button.setOnAction(e -> {
					int newPosRow = currentRow;
					int newPosCol = currentCol;

					switch (currentPositionIndex) {
					case 0 -> newPosCol = currentCol + 1; // Right
					case 1 -> newPosRow = currentRow + 1; // Below
					case 2 -> newPosCol = currentCol - 1; // Left
					case 3 -> newPosRow = currentRow - 1; // Above
					}

					if (!kanTegelPlaatsen(targetGrid, dominoTegelIndex, currentRow, currentCol, newPosRow, newPosCol)) {
						toonAlert("Fout", messages.getString("TegelNietHier"));
					} else {

						if (newPosRow >= 0 && newPosRow < gridSize && newPosCol >= 0 && newPosCol < gridSize) {

							Button tile2Button = vindKnop(targetGrid, newPosRow, newPosCol);
							if (tile2Button == null) {
								tile2Button = new Button();
								targetGrid.add(tile2Button, newPosCol, newPosRow);
							}

							if (!spelersMetGeplaatsteTegel.contains(speler.gebruikersnaam())) {
								button.setText(dc.getGekozenTegel(dominoTegelIndex).getVak1().getLandschapType()
										.getTranslation(messages.getLocale().getLanguage().toString()));
								tile2Button.setText(dc.getGekozenTegel(dominoTegelIndex).getVak2().getLandschapType()
										.getTranslation(messages.getLocale().getLanguage().toString()));
								spelersMetGeplaatsteTegel.add(speler.gebruikersnaam());
							}
						}
					}
				});

				button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				targetGrid.add(button, col, row);
				knopLijst.add(button);
			}
		}
	}

	private boolean checkPos(int rij, int kolom) {
		return (rij == 4 && kolom == 3) || (rij == 3 && kolom == 4) || (rij == 5 && kolom == 4)
				|| (rij == 4 && kolom == 5);
	}

	public boolean kanTegelPlaatsen(GridPane targetGrid, int tegelIndex, int rij, int kolom, int rij2, int kolom2) {

		if (rondeTeller < 2) {
			if (checkPos(rij, kolom) || checkPos(rij2, kolom2)) {
				// Either Vak1 or Vak2 is in a correct position to be placed.
				return true;
			}
			return false;
		}

		// Controleer of de opgegeven locatie in het centrale vak valt
		if (rij == 4 && kolom == 4) {
			System.out.println("De opgegeven locatie is in het centrale vak.");
			return false;
		}

		// Controleer of de opgegeven locatie bezet is
		if (isLocatieBezet(targetGrid, rij, kolom)) {
			System.out.println("De opgegeven locatie is bezet.");
			return false;
		}

		// Controleer of de opgegeven locatie rondom het centrale vak ligt
		if (isRondomCentraalVak(rij, kolom)) {
			System.out.println("De opgegeven locatie ligt rondom het centrale vak.");
			return true; // Tegel kan worden geplaatst rondom het centrale vak
		}

		// Bepaal de tekst van aangrenzende vakjes rondom elk vak van de nieuwe tegel
		Vak vak1 = dc.getGekozenTegel(tegelIndex).getVak1();
		Vak vak2 = dc.getGekozenTegel(tegelIndex).getVak2();

		boolean vak1Check = checkAangrenzendeVakjes(targetGrid, rij, kolom, vak1);

		boolean vak2Check = checkAangrenzendeVakjes(targetGrid, rij, kolom, vak2);

		if (vak1Check) {
			System.out.println("1 correct");
		}
		if (vak2Check) {
			System.out.println("2 correct");
		}
		return vak1Check || vak2Check;
	}

	public boolean isRondomCentraalVak(int rij, int kolom) {

		return (rij >= 3 && rij <= 5 && kolom >= 3 && kolom <= 5);
	}

	private boolean isValidAdjacent(GridPane targetGrid, int rij, int kolom, String typeToMatch) {
		String adjacentTileType = getTextAt(targetGrid, rij, kolom);
		return adjacentTileType != null && adjacentTileType.equals(typeToMatch);
	}

	public boolean isLocatieBezet(GridPane targetGrid, int rij, int kolom) {
		for (Node node : targetGrid.getChildren()) {
			if (node instanceof Button && GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) {
				int nodeRij = GridPane.getRowIndex(node);
				int nodeKolom = GridPane.getColumnIndex(node);

				if (nodeRij == rij && nodeKolom == kolom) {
					Button button = (Button) node;
					String buttonText = button.getText();

					// Controleer of de tekst van de knop niet leeg is
					if (buttonText != null && !buttonText.isEmpty()) {
						return true; // Er staat al tekst in de knop op deze locatie
					}
				}
			}
		}
		return false; // Locatie is leeg (geen tekst in de knop)
	}

	// Methode om de aangrenzende vakjes rondom een specifieke locatie te
	// controleren
	public boolean checkAangrenzendeVakjes(GridPane targetGrid, int rij, int kolom, Vak vak) {
		String typeToMatch = vak.getLandschapType().toString();

		// Check all four directions; the tile can be placed if at least one direction
		// is valid
		return isValidAdjacent(targetGrid, rij - 1, kolom, typeToMatch) || // Top
				isValidAdjacent(targetGrid, rij + 1, kolom, typeToMatch) || // Bottom
				isValidAdjacent(targetGrid, rij, kolom - 1, typeToMatch) || // Left
				isValidAdjacent(targetGrid, rij, kolom + 1, typeToMatch); // Right
	}

	public String getTextAt(GridPane targetGrid, int rij, int kolom) {
		for (Node node : targetGrid.getChildren()) {
			if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null
					&& GridPane.getRowIndex(node) == rij && GridPane.getColumnIndex(node) == kolom) {
				if (node instanceof Button) {
					return ((Button) node).getText();
				}
			}
		}
		return null; // Geen tekst gevonden op deze locatie
	}

	private void updateGridKleur(GridPane grid, SpelerDTO speler) {
		if (speler == null) {
			System.out.println("Speler object is null, cannot update grid color.");
			return;
		}

		for (Node child : grid.getChildren()) {
			Integer rowIndex = GridPane.getRowIndex(child);
			Integer columnIndex = GridPane.getColumnIndex(child);
			if (rowIndex != null && columnIndex != null && rowIndex == 4 && columnIndex == 4) {
				if (child instanceof Pane) {
					child.setStyle("-fx-background-color: " + speler.kleur() + ";");
					break;
				}
			}
		}
	}

	private Button vindKnop(GridPane grid, int row, int col) {
		for (Node child : grid.getChildren()) {
			if (GridPane.getRowIndex(child) != null && GridPane.getColumnIndex(child) != null) {
				if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
					return (Button) child;
				}
			}
		}
		return null;
	}

	private void toonGeselecteerdeTegelsInGrid() {
		gridGeselecteerdeDominoTegel.getChildren().clear(); // Clear the grid first

		if (lblSpelerNaam.getText().equals(speler1.gebruikersnaam())) {
			if (dc.getEerstGekozenTegel() != null) {
				Label vak1 = new Label(dc.getEerstGekozenTegel().getVak1().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage()).toString());
				Label vak2 = new Label(dc.getEerstGekozenTegel().getVak2().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage()).toString());
				gridGeselecteerdeDominoTegel.add(vak1, 1, 1);
				gridGeselecteerdeDominoTegel.add(vak2, 2, 1);
			}
		} else if (lblSpelerNaam.getText().equals(speler2.gebruikersnaam())) {
			if (dc.getTweedeGekozentegel() != null) {
				Label vak1 = new Label(dc.getTweedeGekozentegel().getVak1().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage()).toString());
				Label vak2 = new Label(dc.getTweedeGekozentegel().getVak2().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage()).toString());
				gridGeselecteerdeDominoTegel.add(vak1, 1, 1);
				gridGeselecteerdeDominoTegel.add(vak2, 2, 1);
			}
		} else if (lblSpelerNaam.getText().equals(speler3.gebruikersnaam())) {
			if (dc.getDerdeGekozenTegel() != null) {
				Label vak1 = new Label(dc.getDerdeGekozenTegel().getVak1().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage()).toString());
				Label vak2 = new Label(dc.getDerdeGekozenTegel().getVak2().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage()).toString());
				gridGeselecteerdeDominoTegel.add(vak1, 1, 1);
				gridGeselecteerdeDominoTegel.add(vak2, 2, 1);
			}
		} else if (lblSpelerNaam.getText().equals(speler4.gebruikersnaam())) {
			if (dc.getVierdeGekozenTegel() != null) {
				Label vak1 = new Label(dc.getVierdeGekozenTegel().getVak1().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage()).toString());
				Label vak2 = new Label(dc.getVierdeGekozenTegel().getVak2().getLandschapType()
						.getTranslation(messages.getLocale().getLanguage()).toString());
				gridGeselecteerdeDominoTegel.add(vak1, 1, 1);
				gridGeselecteerdeDominoTegel.add(vak2, 2, 1);
			}
		}
	}

	private void updateSpelerUI() {
		int huidigeSpelerIndex = (spelerDieAanDeBeurtIs - 1) % spelerMetKleur.size();
		SpelerDTO huidigeSpeler = spelerVolgorde.get(huidigeSpelerIndex);
		lblSpelerNaam.setText(huidigeSpeler.gebruikersnaam());
		lblSpelerDoen.setText(messages.getString("KoningPlaatsen"));
	}

}
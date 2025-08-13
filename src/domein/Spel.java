package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import dto.SpelerDTO;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.KleurEnum;

public class Spel {
	private static final int MIN_DOMINOTEGELS = 36;
	private static final int MAX_DOMINOTEGELS = 48;
	private ResourceBundle messages;
	private List<DominoTegel> spelStapel;
	private List<DominoTegel> startKolom;
	private List<DominoTegel> eindKolom;
	private List<Speler> spelers;
	private SpelerRepository spelerRepository;
	private List<SpelerDTO> beschikbareSpelers;
	private List<KleurEnum> beschikbareKleuren;
	private List<SpelerDTO> gekozenSpelers;
	private List<KleurEnum> gekozenKleur;

	/*
	 * Constructor voor het Spel object. UC2: Start nieuw spel Het spel wordt
	 * geÃ¯nitialiseerd met een aantal spelers en alle spelcomponenten worden
	 * klaargezet.
	 */
	public Spel(int aantalSpelers, List<SpelerDTO> gekozenSpelers) {
		if (aantalSpelers < 3 || aantalSpelers > 4) {
			throw new IllegalArgumentException("Het spel heeft minimaal 3 en maximaal 4 spelers.");
		}

		this.spelStapel = new ArrayList<>();
		this.startKolom = new ArrayList<>();
		this.eindKolom = new ArrayList<>();
		this.spelers = new ArrayList<>();
		this.spelerRepository = new SpelerRepository();
		this.gekozenSpelers = gekozenSpelers;
		this.gekozenKleur = new ArrayList<>();
		this.beschikbareSpelers = new ArrayList<>();
		this.beschikbareKleuren = new ArrayList<>(Arrays.asList(KleurEnum.values()));

		initialiseerSpel(aantalSpelers);
	}

	/*
	 * UC2: Start nieuw spel Initialiseert het spel door dominotegels te maken en te
	 * schudden, en kolommen te vullen volgens het aantal spelers.
	 */
	private void initialiseerSpel(int numberOfPlayers) {
		maakEnSchudDominotegels(numberOfPlayers);
		vulKolom(numberOfPlayers);

	}

	/*
	 * UC2: Start nieuw spel Maakt en schudt dominotegels gebaseerd op het aantal
	 * spelers.
	 */
	private void maakEnSchudDominotegels(int numberOfPlayers) {
		int maxTiles = (numberOfPlayers == 3) ? MIN_DOMINOTEGELS : MAX_DOMINOTEGELS;
		List<Integer> tileNumbers = new ArrayList<>();

		for (int i = 1; i <= maxTiles; i++) {
			tileNumbers.add(i);
		}

		Collections.shuffle(tileNumbers);

		for (int i = 0; i < maxTiles; i++) {
			DominoTegel dominoTegel = new DominoTegel(tileNumbers.get(i));
			spelStapel.add(dominoTegel);
		}
	}

	/*
	 * UC2: Start nieuw spel Vult de start- en eindkolom met dominotegels uit de
	 * spelstapel.
	 */
	private void vulKolom(int numberOfPlayers) {
		int numTiles = (numberOfPlayers == 3) ? 3 : 4;
		int startOffset = 0;
		int endOffset = numTiles;

		List<DominoTegel> takenTilesStartKolom = new ArrayList<>(
				spelStapel.subList(startOffset, startOffset + numTiles));
		List<DominoTegel> takenTilesEindKolom = new ArrayList<>(spelStapel.subList(endOffset, endOffset + numTiles));

		Collections.shuffle(takenTilesStartKolom);
		Collections.shuffle(takenTilesEindKolom);

		startKolom.addAll(takenTilesStartKolom);
		eindKolom.addAll(takenTilesEindKolom);

		spelStapel.subList(startOffset, endOffset + numTiles).clear();
	}

	// startkolom wort eindkolom en vult startkolom opnieuw aan
	public void veranderKolom(int numberOfPlayers, Spel spel) {

		// Properly copy over the end column to the start column
		spel.startKolom = new ArrayList<>(eindKolom); // Ensure this is the only place startKolom is set

		// Clear and repopulate the end column
		spel.eindKolom.clear();
		int numTiles = (numberOfPlayers == 3) ? 3 : 4;
		if (spel.spelStapel.size() >= numTiles) {
			List<DominoTegel> newEndTiles = new ArrayList<>(spel.spelStapel.subList(0, numTiles));
			Collections.shuffle(newEndTiles); // Shuffle new tiles for randomness
			spel.eindKolom.addAll(newEndTiles);
			spel.spelStapel.subList(0, numTiles).clear(); // Remove these tiles from the stack
		}

	}

	private void toonAlert(String title, String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/*
	 * Toont de huidige situatie van het spel. UC2: Start nieuw spel
	 */
	public void toonSpelSituatie() {
		for (Speler speler : spelers) {
			System.out.println("Speler: " + speler.getGebruikersnaam());
			System.out.println("Geboortejaar: " + speler.getGeboortejaar());
		}

		System.out.println("Spelstapel: " + spelStapel);
		System.out.println("Startkolom: " + startKolom);
		System.out.println("Eindkolom: " + eindKolom);
	}

	/*
	 * UC2: Start nieuw spel Voegt een gekozen speler toe aan de lijst van gekozen
	 * spelers en verwijdert de gekozen kleur uit de lijst van beschikbare kleuren.
	 */
	public void addSpelerToGekozen(SpelerDTO speler, KleurEnum kleur) {
		if (!gekozenSpelers.contains(speler)) {
			gekozenSpelers.add(speler);
			gekozenKleur.add(kleur);

			// Remove the chosen color from available colors
			beschikbareKleuren.remove(kleur);

			// Remove the player from available players
			beschikbareSpelers.removeIf(s -> s.gebruikersnaam().equals(speler.gebruikersnaam()));

			// Assuming SpelerRepository has the functionality to mark a player as chosen
			spelerRepository.markeerSpelerAlsGekozen(speler.gebruikersnaam());
		}
	}

	public SpelerDTO getSpelerByGebruikersnaam(String gebruikersnaam) {
		// Delegate to SpelerRepository to find the player
		return spelerRepository.getSpelerByGebruikersnaam(gebruikersnaam);
	}

	/*
	 * UC2: Start nieuw spel Geeft een lijst terug van alle spelers die nog niet
	 * gekozen zijn.
	 */
	public List<SpelerDTO> getBeschikbareSpelers() {
		List<SpelerDTO> beschikbareSpelers = new ArrayList<>();
		for (Speler speler : spelerRepository.geefAlleSpelers()) {
			SpelerDTO spelerDTO = new SpelerDTO(speler);
			if (!gekozenSpelers.contains(spelerDTO)) {
				beschikbareSpelers.add(spelerDTO);
			}
		}
		return beschikbareSpelers;
	}

	/*
	 * UC2: Start nieuw spel Geeft een lijst van beschikbare kleuren terug die nog
	 * niet gekozen zijn door andere spelers.
	 */
	public List<KleurEnum> getBeschikbareKleuren() {
		return beschikbareKleuren.stream().filter(kleur -> !gekozenKleur.contains(kleur)).collect(Collectors.toList());
	}

	public List<DominoTegel> getStartKolom() {
		return startKolom;
	}

	public void setStartKolom(List<DominoTegel> startKolom) {
		this.startKolom = startKolom;
	}

	public List<DominoTegel> getSpelStapel() {
		return spelStapel;
	}

	public void setSpelStapel(List<DominoTegel> spelStapel) {
		this.spelStapel = spelStapel;
	}

	public List<DominoTegel> getEindKolom() {
		return eindKolom;
	}

	public void setEindKolom(List<DominoTegel> eindKolom) {
		this.eindKolom = eindKolom;
	}

	public List<SpelerDTO> getGekozenSpelers() {
		return gekozenSpelers;
	}

	public List<KleurEnum> getGekozenKleur() {
		return gekozenKleur;
	}

	public SpelerRepository getSpelerRepository() {
		return spelerRepository;
	}
}
package cui;

import java.time.Year;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import domein.DominoTegel;
import domein.Spel;
import dto.SpelerDTO;
import exceptions.GebruikersnaamInGebruikException;
import exceptions.GebruikersnaamTeKortLeeg;
import utils.KleurEnum;
import utils.Taal;

public class SpelerApplicatie {
	private final DomeinController domeinController;
	private final Scanner scanner;
	private ResourceBundle messages; // ResourceBundle for localization
	private final List<SpelerDTO> gekozenSpelers = new ArrayList<>();
	private final List<String> gekozenKleuren = new ArrayList<>();

	public SpelerApplicatie(DomeinController domeinController) {
		this.domeinController = domeinController;
		scanner = new Scanner(System.in);
	}

	// Start de applicatie
	public void start() {
		System.out.println("Choose your language / Kies uw taal:");
		System.out.println("1. NL - Nederlands");
		System.out.println("2. EN - English");
		System.out.print("Enter your choice / Voer uw keuze in: ");
		String languageChoice = scanner.nextLine().toUpperCase();

		// Roep de Taal.getResourceBundle-methode aan om de ResourceBundle te krijgen op
		// basis van de gekozen taal
		this.messages = Taal.getResourceBundle(languageChoice);

		keuze(); // Ga verder met de applicatie na het instellen van de ResourceBundle
		System.out.println(messages.getString("thank_you"));
		scanner.close(); // Close the scanner after using it1
	}

	// Laat de gebruiker kiezen uit de beschikbare opties
	private void keuze() {
		boolean startGame = false;

		while (!startGame) {
			System.out.println(messages.getString("menu_options"));
			System.out.print(messages.getString("enter_choice"));

			try {
				int choice = scanner.nextInt();
				System.out.println();
				scanner.nextLine(); // Consume the newline

				boolean newGame = switch (choice) {
				case 1 -> {
					registeerSpeler();
					yield false;
				}
				case 2 -> {
					if (domeinController.getBeschikbareSpelers().size() < 3) {
						System.out.println(messages.getString("te_weinig"));
						yield false;
					} else {
						speelSpel(kiesSpelersEnKleur());

						yield true; // Start the game
					}
				}
				case 3 -> true; // Exit the method and end the program
				default -> {
					System.out.println(messages.getString("invalid_choice"));
					yield false;
				}
				};

				startGame = newGame;
			} catch (InputMismatchException e) {
				System.out.println(messages.getString("invalid_choice"));
				scanner.nextLine(); // Consume the invalid input
			}
		}
	}

	private void speelSpel(int spelers) {
		Spel spel = new Spel(spelers, new ArrayList<>());
		int nummer;
		int spelerDieGeplaatstHebben = 0;
		List<DominoTegel> lijstBeschikbaarStartKolom = spel.getStartKolom();

		do {
			domeinController.kiesKoningNietOpDominotegel(gekozenSpelers); // This modifies gekozenSpelers directly
			SpelerDTO gekozenSpelerKoning = gekozenSpelers.stream().filter(s -> s.koning().isGeplaatstOpStartKolom())
					.findFirst().orElse(null); // Find the first player whose king is now placed

			if (gekozenSpelerKoning != null) {
				System.out.printf("%n%s Kies het nummer van de dominotegel van de startkolom%n",
						gekozenSpelerKoning.gebruikersnaam());

				toonTegels(lijstBeschikbaarStartKolom);

				System.out.print("kies een nummer:");
				nummer = scanner.nextInt();
				scanner.nextLine(); // Consume the newline after number input

				domeinController.kiesDominoTegel(nummer, lijstBeschikbaarStartKolom); // This marks the tile as selected

				DominoTegel gekozenTegel = lijstBeschikbaarStartKolom.stream().filter(t -> t.isSelected()).findFirst()
						.orElse(null); // Find the selected tile

				if (gekozenTegel != null) {
					System.out.printf("speler: %s heeft zijn %s koning geplaatst op dominotegel %d%n%n",
							gekozenSpelerKoning.gebruikersnaam(), gekozenSpelerKoning.kleur(),
							gekozenTegel.getNummer());
					spelerDieGeplaatstHebben++;
					lijstBeschikbaarStartKolom.remove(gekozenTegel);
					domeinController.plaatsDominotegelInKoninkrijk(gekozenTegel, gekozenSpelerKoning);
				}
			}
		} while (spelerDieGeplaatstHebben != spelers);

		toonSituatie(spel);
	}

	private void toonSituatie(Spel spel) {
		// TODO Auto-generated method stub
		System.out.println("Dit is de voorlopige situatie:\n");
		for (SpelerDTO speler : gekozenSpelers) {
			System.out.printf("speler:%s met kleur: %s heeft deze dominoTegels in zijn koninkrijk %n%n",
					speler.gebruikersnaam(), speler.kleur());
			toonTegels(speler.koninkrijk());
			System.out.printf("En zijn %s koning staat op de %s%n", speler.koning().getKleur(),
					domeinController.plaatsKoning(speler.koning()));
		}
		System.out.print(
				"Hier zijn de overgebleven dominotegels op de spelstapel met genummeerde zijde naar bovenkant: \n\n");
		for (DominoTegel tegel : spel.getSpelStapel()) {
			System.out.printf("%d%n", tegel.getNummer());
		}
		System.out.print("Hier heb je de startkolom: \n\n");
		for (DominoTegel tegel : spel.getStartKolom()) {
			System.out.printf("%s %s%n", tegel.getVak1(), tegel.getVak2());
		}
		System.out.print("Hier heb je de eindkolom: \n\n");
		for (DominoTegel tegel : spel.getEindKolom()) {
			System.out.printf("%s %s%n", tegel.getVak1(), tegel.getVak2());
		}

	}

	public void toonTegels(List<DominoTegel> startKolom) {
		System.out.println();
		for (DominoTegel dominoTegel : startKolom) {
			System.out.printf("%d %s %s %n", dominoTegel.getNummer(), dominoTegel.getVak1().getLandschapType(),
					dominoTegel.getVak2().getLandschapType());
		}
	}

	// Laat de gebruiker spelers en kleuren kiezen
	//
	private int kiesSpelersEnKleur() {
		System.out.println(messages.getString("num_players_choice"));
		int numPlayers = scanner.nextInt();
		scanner.nextLine(); // Consume the newline

		if (numPlayers != 3 && numPlayers != 4) {
			System.out.println(messages.getString("invalid_num_players"));

		}

		List<SpelerDTO> beschikbareSpelers = domeinController.getBeschikbareSpelers();
		List<KleurEnum> beschikbareKleuren = new ArrayList<>(List.of(KleurEnum.values()));

		for (int index = 1; index <= numPlayers; index++) {
			System.out.println(messages.getString("available_players") + "\n");
			for (int i = 0; i < beschikbareSpelers.size(); i++) {
				SpelerDTO speler = beschikbareSpelers.get(i);
				int leeftijd = Year.now().getValue() - speler.geboortejaar();
				System.out.println((i + 1) + ". " + speler.gebruikersnaam() + " - " + leeftijd + " "
						+ messages.getString("years_old"));
			}
			System.out.printf("%nKies het nummer van speler %d: ", index);
			int choiceS = scanner.nextInt();
			scanner.nextLine(); // Consume the newline

			if (choiceS < 1 || choiceS > beschikbareSpelers.size()) {
				System.out.println(messages.getString("invalid_player_selection"));
				index--; // Retry the player selection
				continue;
			}

			SpelerDTO gekozenSpeler = beschikbareSpelers.get(choiceS - 1);
			gekozenSpelers.add(gekozenSpeler);

			beschikbareSpelers.remove(choiceS - 1);

			boolean geldigeKleur = false;
			do {
				System.out.println();
				System.out.println(messages.getString("available_colors") + "\n");
				for (KleurEnum kleur : beschikbareKleuren) {
					String translatedColorName = kleur.getTranslation(messages.getLocale().getLanguage());
					System.out.println(translatedColorName);
				}
				System.out.printf("%n" + messages.getString("choose_color_for_player"), index);
				String gekozenKleurInput = scanner.nextLine().trim();

				KleurEnum gekozenKleur = KleurEnum.getByTranslation(gekozenKleurInput, messages.getLocale());
				if (gekozenKleur != null && beschikbareKleuren.contains(gekozenKleur)) {
					gekozenKleuren.add(gekozenKleur.name());

					gekozenSpeler = gekozenSpeler.metKleur(gekozenKleur);
					gekozenSpeler.koning().setKleur(gekozenKleur);
					beschikbareKleuren.remove(gekozenKleur);
					geldigeKleur = true;
				} else {
					System.out.println(messages.getString("invalid_color_selection"));
				}
			} while (!geldigeKleur);

		}
		printGekozenSpelerEnKleur();
		return numPlayers;
	}

	// Registreert een nieuwe speler
	private void registeerSpeler() {
		System.out.println(messages.getString("registration"));
		System.out.print(messages.getString("enter_username"));
		String username = scanner.nextLine();
		System.out.print(messages.getString("enter_year_of_birth"));
		int yearOfBirth = scanner.nextInt();
		scanner.nextLine(); // Consume the newline

		try {
			domeinController.registreerSpeler(username, yearOfBirth);
			System.out.println(messages.getString("registration_successful"));
		} catch (GebruikersnaamInGebruikException ex) {
			System.out.println(messages.getString("username_in_use"));
		} catch (GebruikersnaamTeKortLeeg ex) {
			System.out.println(messages.getString("birthyear_invalid"));
		}

		// Print chosen colors for each player
		printGekozenSpelerEnKleur();
	}

	// Print de gekozen kleuren voor elke speler
	private void printGekozenSpelerEnKleur() {
		System.out.println(messages.getString("playerColors"));
		for (int i = 0; i < gekozenSpelers.size(); i++) {
			System.out.println(gekozenSpelers.get(i).gebruikersnaam() + ": " + gekozenKleuren.get(i));
		}
	}
}

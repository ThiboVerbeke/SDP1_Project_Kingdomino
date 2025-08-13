package domein;

import java.util.List;

import dto.SpelerDTO;
import exceptions.GebruikersnaamInGebruikException;
import persistentie.SpelerMapper;

public class SpelerRepository {

	private final SpelerMapper mapper;
	private List<Speler> beschikbareSpelers; // Available players list

	public SpelerRepository() {
		mapper = new SpelerMapper();
		beschikbareSpelers = mapper.geefAlleSpelers(); // Initialize available players list from the mapper
	}

	public void voegToe(Speler speler) {
		if (bestaatSpeler(speler.getGebruikersnaam()))
			throw new GebruikersnaamInGebruikException();

		mapper.voegToe(speler);
		beschikbareSpelers.add(speler); // Add the new player to the available players list
	}

	private boolean bestaatSpeler(String gebruikersnaam) {
		return beschikbareSpelers.stream().anyMatch(speler -> speler.getGebruikersnaam().equals(gebruikersnaam));
	}

	public List<Speler> geefBeschikbareSpelers() {
		return beschikbareSpelers;
	}

	public void markeerSpelerAlsGekozen(String gebruikersnaam) {
		beschikbareSpelers.removeIf(speler -> speler.getGebruikersnaam().equals(gebruikersnaam));
	}

	public SpelerDTO getSpelerByGebruikersnaam(String gebruikersnaam) {
		for (Speler speler : beschikbareSpelers) { // or another list that holds all players
			if (speler.getGebruikersnaam().equals(gebruikersnaam)) {
				return new SpelerDTO(speler); // Assuming you have a constructor in SpelerDTO that accepts a Speler
			}
		}
		return null; // or throw an exception if the player is not found
	}

	public List<Speler> geefAlleSpelers() {
		return mapper.geefAlleSpelers();
	}
}

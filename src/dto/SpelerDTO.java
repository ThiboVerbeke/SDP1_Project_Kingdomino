package dto;

import java.util.List;

import domein.DominoTegel;
import domein.Kasteel;
import domein.Koning;
import domein.Speler;
import domein.StartTegel;
import utils.KleurEnum;

public record SpelerDTO(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld, Koning koning,
		KleurEnum kleur, List<DominoTegel> koninkrijk, StartTegel startTegel, Kasteel kasteel) {
	public SpelerDTO(Speler speler) {
		this(speler.getGebruikersnaam(), speler.getGeboortejaar(), speler.getAantalGewonnen(),
				speler.getAantalGespeeld(), speler.getKoning(), speler.getKleur(), speler.getKoninkrijk(),
				speler.getStartTegel(), speler.getKasteel());
	}

	public SpelerDTO metKleur(KleurEnum gekozenKleur) {
		return new SpelerDTO(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld, koning, gekozenKleur,
				koninkrijk, startTegel, kasteel);
	}

	public SpelerDTO metKoninkrijk(List<DominoTegel> koninkrijk) {
		return new SpelerDTO(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld, koning, kleur, koninkrijk,
				startTegel, kasteel);
	}

}

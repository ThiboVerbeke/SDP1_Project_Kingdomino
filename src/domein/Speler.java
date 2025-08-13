package domein;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import exceptions.GeboortejaarException;
import exceptions.GebruikersnaamTeKortLeeg;
import utils.KleurEnum;

public class Speler {
	private String gebruikersnaam;
	private int geboortejaar;
	private int aantalGewonnen, aantalGespeeld;
	private StartTegel startTegel;
	private Kasteel kasteel;
	private Koning koning;

	private List<DominoTegel> koninkrijk;
	private final static int MIN_GEBOORTEJAAR = 1900;
	private final static int MAX_GEBOORTEJAAR = Year.now().getValue() - 6;
	private KleurEnum kleur;

	public Speler(String gebruikersnaam, int geboortejaar) {
		this(gebruikersnaam, geboortejaar, 0, 0);
	}

	public Speler(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		setAantalGewonnen(aantalGewonnen);
		setAantalGespeeld(aantalGespeeld);

		koning = new Koning();
		kasteel = new Kasteel();
		startTegel = new StartTegel();
		kleur = KleurEnum.BLUE;
		koninkrijk = new ArrayList<>();

	}

	public List<DominoTegel> getKoninkrijk() {
		return koninkrijk;
	}

	public void setKoninkrijk(List<DominoTegel> koninkrijk) {
		this.koninkrijk = koninkrijk;
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	private void setGebruikersnaam(String gebruikersnaam) {

		if (gebruikersnaam.length() < 6) {
			throw new GebruikersnaamTeKortLeeg();
		}
		if (gebruikersnaam == null || gebruikersnaam.isBlank() || gebruikersnaam.isEmpty()) {
			throw new GebruikersnaamTeKortLeeg();
		}
		this.gebruikersnaam = gebruikersnaam;
	}

	private void setGeboortejaar(int geboortejaar) {
		if (geboortejaar < MIN_GEBOORTEJAAR) {
			throw new GeboortejaarException();
		}
		if (geboortejaar > MAX_GEBOORTEJAAR) {
			throw new GeboortejaarException();
		}
		this.geboortejaar = geboortejaar;
	}

	public int getGeboortejaar() {
		return geboortejaar;
	}

	public int getAantalGewonnen() {
		return aantalGewonnen;
	}

	private void setAantalGewonnen(int aantalGewonnen) {
		if (aantalGewonnen < 0) {
			throw new IllegalArgumentException("Het aantal gewonnen spellen mag niet negatief zijn");
		}
		this.aantalGewonnen = aantalGewonnen;
	}

	public int getAantalGespeeld() {
		return aantalGespeeld;
	}

	private void setAantalGespeeld(int aantalGespeeld) {
		if (aantalGespeeld < 0) {
			throw new IllegalArgumentException("Het aantal gespeelde spellen mag niet negatief zijn");
		}
		this.aantalGespeeld = aantalGespeeld;
	}

	public void setKleur(KleurEnum kleur) {
		this.kleur = kleur;
	}

	public KleurEnum getKleur() {
		return kleur;
	}

	public StartTegel getStartTegel() {
		return startTegel;
	}

	public void setStartTegel(StartTegel startTegel) {
		this.startTegel = startTegel;
	}

	public Kasteel getKasteel() {
		return kasteel;
	}

	public void setKasteel(Kasteel kasteel) {
		this.kasteel = kasteel;
	}

	public Koning getKoning() {
		return koning;
	}

	public void setKoning(Koning koning) {
		this.koning = koning;
	}

}

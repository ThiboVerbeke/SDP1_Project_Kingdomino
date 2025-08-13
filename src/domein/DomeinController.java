package domein;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import dto.SpelerDTO;
import persistentie.SpelerMapper;
import utils.KleurEnum;

public class DomeinController {

	public Spel spel;
	private DominoTegel eersteGekozenTegel;
	private DominoTegel tweedeGekozenTegel;
	private DominoTegel derdeGekozenTegel;
	private DominoTegel vierdeGekozenTegel;

	/**
	 * Constructor voor het domeincontroller object maakt een nieuw spel object aan
	 * met 3 spelers
	 */
	public DomeinController() {

		this.spel = new Spel(3, new ArrayList<>());// 3 omdat er minimum 3 spelers moeten zijn

	}

	public DominoTegel getEerstGekozenTegel() {
		return eersteGekozenTegel;
	}

	public void setEerstGekozenTegel(int index) {
		this.eersteGekozenTegel = spel.getStartKolom().get(index);
	}

	public DominoTegel getTweedeGekozentegel() {
		return tweedeGekozenTegel;
	}

	public void setTweedeGekozentegel(int index) {
		this.tweedeGekozenTegel = spel.getStartKolom().get(index);
	}

	public DominoTegel getDerdeGekozenTegel() {
		return derdeGekozenTegel;
	}

	public void setDerdeGekozenTegel(int index) {
		this.derdeGekozenTegel = spel.getStartKolom().get(index);
	}

	public DominoTegel getVierdeGekozenTegel() {
		return vierdeGekozenTegel;
	}

	public void setVierdeGekozenTegel(int index) {
		this.vierdeGekozenTegel = spel.getStartKolom().get(index);
	}

	/**
	 * UC1 - Registreer nieuwe speler Registreert een nieuwe speler in het systeem
	 * met een gebruikersnaam en geboortejaar.
	 * 
	 * @param gebruikersnaam De gebruikersnaam van de nieuwe speler.
	 * @param geboortejaar   Het geboortejaar van de nieuwe speler.
	 * @see Speler
	 * @see Spel#voegToe
	 */
	public void registreerSpeler(String gebruikersnaam, int geboortejaar) {
		Speler nieuweSpeler = new Speler(gebruikersnaam, geboortejaar);
		spel.getSpelerRepository().voegToe(nieuweSpeler);
	}

	/**
	 * UC2 - Start nieuw spel Geeft een lijst van beschikbare SpelerDTO objecten
	 * terug die niet al gekozen zijn voor het spel.
	 * 
	 * @return Lijst van beschikbare spelers.
	 * 
	 */
	public List<SpelerDTO> getBeschikbareSpelers() {
		return spel.getBeschikbareSpelers();
	}

	/**
	 * UC2 - Start nieuw spel Vertaalt en geeft een lijst van beschikbare kleuren
	 * terug gebaseerd op de huidige taalinstellingen.
	 * 
	 * @param messages Resource bundel met locale specificaties voor vertaling.
	 * @return Een lijst van vertaalde kleurnamen.
	 * 
	 */
	public List<String> geefKleurenInTaalString(ResourceBundle messages) {
		List<KleurEnum> beschikbareKleuren = spel.getBeschikbareKleuren();
		List<String> kleurenvertaling = new ArrayList<>();
		for (KleurEnum kleur : beschikbareKleuren) {
			kleurenvertaling.add(kleur.getTranslation(messages.getLocale().getLanguage()));
		}
		return kleurenvertaling;
	}

	/**
	 * UC2 - Start nieuw spel Voegt een gekozen speler toe aan de lijst van spelers
	 * in het spel met een gespecificeerde kleur.
	 * 
	 * @param speler De SpelerDTO die toegevoegd moet worden.
	 * @param kleur  De KleurEnum die de speler zal vertegenwoordigen.
	 * 
	 */
	public void voegSpelerToe(SpelerDTO speler, KleurEnum kleur) {
		spel.addSpelerToGekozen(speler, kleur);
	}

	/**
	 * UC2 - Start nieuw spel Selecteert willekeurig een speler die zijn koning nog
	 * niet op een dominotegel heeft geplaatst.
	 * 
	 * @param spelerLijst De lijst van spelers waaruit een koning gekozen moet
	 *                    worden.
	 * 
	 */
	public void kiesKoningNietOpDominotegel(List<SpelerDTO> spelerLijst) {
		List<SpelerDTO> nietGeplaatsteKoningenSpeler = new ArrayList<>();
		for (SpelerDTO speler : spelerLijst) {
			if (!speler.koning().isGeplaatstOpStartKolom()) {
				nietGeplaatsteKoningenSpeler.add(speler);
			}
		}

		if (!nietGeplaatsteKoningenSpeler.isEmpty()) {
			Random random = new Random();
			int willekeurigeIndex = random.nextInt(nietGeplaatsteKoningenSpeler.size());
			SpelerDTO gekozenKoningSpeler = nietGeplaatsteKoningenSpeler.get(willekeurigeIndex);
			gekozenKoningSpeler.koning().setGeplaatstOpStartKolom(true);
			spelerLijst.set(spelerLijst.indexOf(gekozenKoningSpeler), gekozenKoningSpeler);
		}
	}

	/**
	 * UC2 - Start nieuw spel Zoekt in een lijst van DominoTegel-objecten naar een
	 * tegel met het opgegeven nummer en markeert deze als geselecteerd.
	 * 
	 * @param nummer Het nummer van de dominotegel die geselecteerd moet worden.
	 * @param lijst  De lijst van DominoTegel-objecten waaruit gekozen moet worden.
	 * 
	 */
	public void kiesDominoTegel(int nummer, List<DominoTegel> lijst) {
		for (DominoTegel tegel : lijst) {
			if (nummer == tegel.getNummer()) {
				tegel.setSelected(true);
				break; // No need to continue once the correct tile is found and marked
			}
		}
	}

	/*
	 * UC2 bepaalt de locatie waar de koning is geplaatst (startkolom of eindkolom)
	 * en retourneert deze informatie als een String. Als de koning niet op een van
	 * beide kolommen is geplaatst, wordt null geretourneerd.
	 */
	public String waarGeplaatst(Koning koning) {
		String plaats = null;
		if (koning.isGeplaatstOpStartKolom()) {
			plaats = "startkolom";
		}
		if (koning.isGeplaatstOpEindKolom()) {
			plaats = "eindkolom";
		}
		return plaats;
	}

	/**
	 * UC2 - Start nieuw spel Voegt een DominoTegel toe aan het koninkrijk van een
	 * speler.
	 * 
	 * @param dominoTegel De DominoTegel die aan het koninkrijk toegevoegd moet
	 *                    worden.
	 * @param speler      De SpelerDTO die de tegel in zijn koninkrijk ontvangt.
	 * 
	 */
	public void plaatsDominotegelInKoninkrijk(DominoTegel dominoTegel, SpelerDTO speler) {
		List<DominoTegel> lijst = new ArrayList<>();
		lijst.add(dominoTegel);

		speler = speler.metKoninkrijk(lijst);
	}

	/**
	 * UC2 - Start nieuw spel Bepaalt de locatie waar de koning is geplaatst
	 * (startkolom of eindkolom) en retourneert deze informatie als een String.
	 * 
	 * @param koning De Koning waarvan de locatie bepaald moet worden.
	 * @return De plaatsnaam van de kolom of null als de koning niet is geplaatst.
	 * 
	 */
	public String plaatsKoning(Koning koning) {
		String plaats = null;
		if (koning.isGeplaatstOpStartKolom()) {
			plaats = "startkolom";
		} else if (koning.isGeplaatstOpEindKolom()) {
			plaats = "eindkolom";
		}
		return plaats;
	}

	/**
	 * UC2 - Start nieuw spel Past de kleur aan van drie belangrijke onderdelen van
	 * de speler: de starttegel, de koning en het kasteel.
	 * 
	 * @param speler De SpelerDTO wiens onderdelen van kleur veranderd moeten
	 *               worden.
	 * @param kleur  De nieuwe kleur voor de starttegel, koning en kasteel.
	 * 
	 */
	public void setkleurStartTegelKoningKasteel(SpelerDTO speler, KleurEnum kleur) {
		speler.koning().setKleur(kleur);
		speler.startTegel().setKleur(kleur);
		speler.kasteel().setKleur(kleur);
	}

	/**
	 * UC3 - Speel spelBereken de scores voor alle spelers in het spel.
	 * 
	 * @param spel Het spel waarvoor de scores worden berekend.
	 * 
	 */
	public void berekenScoresVoorAlleSpelers(Spel spel) {
		SpelerMapper spelerMapper = new SpelerMapper();
		for (SpelerDTO spelerDTO : spel.getGekozenSpelers()) {
			SpelerDTO speler = spelerMapper.geefSpelerDTO(spelerDTO.gebruikersnaam());
			if (speler != null) {
				List<DominoTegel> koninkrijk = speler.koninkrijk();
				int totaleScore = 0;
				Set<Vak> bezocht = new HashSet<>();
				for (DominoTegel tegel : koninkrijk) {
					if (!bezocht.contains(tegel.getVak1())) {
						totaleScore += berekenScoreVoorGebied(tegel.getVak1(), bezocht, koninkrijk);
					}
					if (!bezocht.contains(tegel.getVak2())) {
						totaleScore += berekenScoreVoorGebied(tegel.getVak2(), bezocht, koninkrijk);
					}
				}
				System.out.println("Speler " + speler.gebruikersnaam() + " heeft een score van: " + totaleScore);

				// Update aantalGewonnen en aantalGespeeld in de database
				int nieuweAantalGewonnen = spelerDTO.aantalGewonnen() + 1;
				int nieuweAantalGespeeld = spelerDTO.aantalGespeeld() + 1;
				spelerMapper.updateGewonnenEnGespeeld(spelerDTO.gebruikersnaam(), nieuweAantalGewonnen,
						nieuweAantalGespeeld);
			}
		}
	}

	/**
	 * UC3 - Speel spel Bereken de score voor een gebied, gegeven het startvak, de
	 * bezochte vakken en het koninkrijk van de speler.
	 * 
	 * @param startVak   Het startvak van het te berekenen gebied.
	 * @param bezocht    Een verzameling van bezochte vakken.
	 * @param koninkrijk Het koninkrijk van de speler.
	 * @return De score voor het gebied.
	 * 
	 */
	private int berekenScoreVoorGebied(Vak startVak, Set<Vak> bezocht, List<DominoTegel> koninkrijk) {
		if (bezocht.contains(startVak))
			return 0;

		int score = 0;
		int aantalVakjes = 0;
		int aantalKronen = startVak.getAantalkronen();
		bezocht.add(startVak);
		aantalVakjes++;

		// Check buren in het koninkrijk
		for (DominoTegel tegel : koninkrijk) {
			if (tegel.getVak1().equals(startVak) || tegel.getVak2().equals(startVak)) {
				Vak anderVak = tegel.getVak1().equals(startVak) ? tegel.getVak2() : tegel.getVak1();
				if (anderVak.getLandschapType() == startVak.getLandschapType() && !bezocht.contains(anderVak)) {
					aantalKronen += anderVak.getAantalkronen();
					bezocht.add(anderVak);
					aantalVakjes++;
				}
			}
		}
		score = aantalVakjes * aantalKronen;
		return score;
	}

	/**
	 * UC2 - Start nieuw spel Voert een speelronde uit waarbij een speler een
	 * dominotegel kiest uit een stapel.
	 * 
	 * @param speler De SpelerDTO die deelneemt aan de ronde.
	 * @param stapel De lijst van DominoTegel-objecten waaruit de speler kiest.
	 * 
	 */
	public void speelRonde(SpelerDTO speler, List<DominoTegel> stapel) {
		int aantalGespeeld = 0;
		DominoTegel bovensteTegel = stapel.get(0);
	}

	/**
	 * UC2 - Start nieuw spelBereken de leeftijd van een speler op basis van het
	 * huidige jaar en het geboortejaar van de speler.
	 * 
	 * @param speler De SpelerDTO waarvan de leeftijd berekend moet worden.
	 * @return De berekende leeftijd van de speler.
	 * 
	 */
	public int berekenLeeftijdSpeler(SpelerDTO speler) {
		return Year.now().getValue() - speler.geboortejaar();
	}

	/**
	 * Verandert de grootte van kolommen binnen het spel.
	 * 
	 * @param size De nieuwe grootte voor de kolommen.
	 *
	 */
	public void veranderKolommen(int size) {
		spel.veranderKolom(size, spel);
	}

	/**
	 * Haalt een gekozen dominotegel op uit de startkolom op basis van de index.
	 * 
	 * @param index De index van de tegel in de startkolom.
	 * @return De gekozen DominoTegel, of null als de index ongeldig is.
	 * @use UC2 - Start nieuw spel
	 */
	public DominoTegel getGekozenTegel(int index) {
		if (index >= 0 && index < spel.getStartKolom().size()) {
			return spel.getStartKolom().get(index);
		}
		return null; // Consider throwing an exception if the index is invalid
	}

	/**
	 * UC5 - Speel beurt Selecteert een DominoTegel uit een lijst van beschikbare
	 * tegels.
	 * 
	 * @param tegelIndex        De index van de te selecteren tegel.
	 * @param beschikbareTegels De lijst van beschikbare DominoTegels.
	 * @throws IllegalArgumentException Als de tegelindex ongeldig is.
	 * 
	 */
	public void selecteerDominoTegel(int tegelIndex, List<DominoTegel> beschikbareTegels) {
		if (tegelIndex >= 0 && tegelIndex < beschikbareTegels.size()) {
			DominoTegel gekozenTegel = beschikbareTegels.get(tegelIndex);
			gekozenTegel.setSelected(true);
		} else {
			throw new IllegalArgumentException("Ongeldige tegelindex");
		}
	}
}
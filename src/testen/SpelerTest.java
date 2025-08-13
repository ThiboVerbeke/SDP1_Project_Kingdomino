package testen;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Year;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Speler;

public class SpelerTest {
	private Speler speler;

	private final static int MIN_GEBOORTEJAAR = 1900;
	private final static int MAX_GEBOORTEJAAR = Year.now().getValue() - 6;

	@BeforeEach
	public void setUp() {
		speler = new Speler("gebruikersnaam", 1990);
	}

	@Test
	void maakSpeler_alleGegevensCorrect_maaktObject() {
		speler = new Speler("avatar", 2003, 4, 25);
		Assertions.assertEquals("avatar", speler.getGebruikersnaam());
		Assertions.assertEquals(2003, speler.getGeboortejaar());
		Assertions.assertEquals(4, speler.getAantalGewonnen());
		Assertions.assertEquals(25, speler.getAantalGespeeld());
	}

	@Test
	void maakSpeler_correcteGebruikersnaamGeboortejaar_maaktObject() {
		speler = new Speler("avatar", 2003);
		Assertions.assertEquals("avatar", speler.getGebruikersnaam());
		Assertions.assertEquals(2003, speler.getGeboortejaar());
		Assertions.assertEquals(0, speler.getAantalGewonnen());
		Assertions.assertEquals(0, speler.getAantalGespeeld());
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, 5, 10 })
	public void maakSpeler_setAantalGespeeld_correctAantalGespeeld(int aantalGespeeld) {
		speler = new Speler("gebruikersnaam", 1990, 0, aantalGespeeld);
		assertEquals(aantalGespeeld, speler.getAantalGespeeld());
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, 5, 10 })
	public void maakSpeler_setAantalGewonnen_correctAantalGewonnen(int aantalGewonnen) {
		speler = new Speler("gebruikersnaam", 1990, aantalGewonnen, 0);
		assertEquals(aantalGewonnen, speler.getAantalGewonnen());
	}

	@ParameterizedTest
	@ValueSource(strings = { "newUsername", "anotherUsername" })
	public void maakSpeler_setGebruikersnaamWithValidInput_validUsername(String username) {
		speler = new Speler(username, 1990);
		assertEquals(username, speler.getGebruikersnaam());
	}

	@ParameterizedTest
	@ValueSource(strings = { "short", "", " " })
	public void maakSpeler_setGebruikersnaamWithInvalidInput_invalidUsername(String username) {
		assertThrows(IllegalArgumentException.class, () -> new Speler(username, 1990));
	}

	@ParameterizedTest
	@ValueSource(ints = { 2000, 2010 })
	public void maakSpeler_setGeboortejaarWithValidInput_validBirthYear(int geboortejaar) {
		speler = new Speler("gebruikersnaam", geboortejaar);
		assertEquals(geboortejaar, speler.getGeboortejaar());
	}

	@ParameterizedTest
	@ValueSource(ints = { 1800, 1899 })
	public void maakSpeler_setGeboortejaarWithInvalidInput_invalidBirthYear(int geboortejaar) {
		assertThrows(IllegalArgumentException.class, () -> new Speler("gebruikersnaam", geboortejaar));
	}

	private static Stream<Integer> generateValidBirthYears() {
		return IntStream.rangeClosed(MIN_GEBOORTEJAAR, MAX_GEBOORTEJAAR).boxed();
	}

	@ParameterizedTest
	@MethodSource("generateValidBirthYears")
	public void maakSpeler_setGeboortejaarWithinBounds_validBirthYear(int geboortejaar) {
		speler = new Speler("gebruikersnaam", geboortejaar);
		assertEquals(geboortejaar, speler.getGeboortejaar());
	}

	private static Stream<Integer> generateInvalidBirthYears() {
		return Stream.of(MIN_GEBOORTEJAAR - 1, MAX_GEBOORTEJAAR + 1);
	}

	@ParameterizedTest
	@MethodSource("generateInvalidBirthYears")
	public void maakSpeler_setGeboortejaarOutsideBounds_invalidBirthYear(int geboortejaar) {
		assertThrows(IllegalArgumentException.class, () -> new Speler("gebruikersnaam", geboortejaar));
	}

}

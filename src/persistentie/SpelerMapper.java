package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Speler;
import dto.SpelerDTO;

public class SpelerMapper {

	private static final String INSERT_SPELER = "INSERT INTO ID429429_g31.Speler (gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld)"
			+ "VALUES (?, ?, ?, ?)";

	public void voegToe(Speler speler) {
		Connectie ssh = new Connectie();
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) {
			query.setString(1, speler.getGebruikersnaam());
			query.setInt(2, speler.getGeboortejaar());
			query.setInt(3, speler.getAantalGewonnen());
			query.setInt(4, speler.getAantalGespeeld());

			query.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		ssh.closeConnection();
	}

	// Voor controle als speler al bestaat
	public Speler geefSpeler(String gebruikersnaam) {
		Connectie ssh = new Connectie();
		Speler speler = null;

		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement query = conn
						.prepareStatement("SELECT * FROM ID429429_g31.Speler WHERE gebruikersnaam = ?")) {
			query.setString(1, gebruikersnaam);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					int geboortejaar = rs.getInt("geboortejaar");
					int aantalGewonnen = rs.getInt("aantalGewonnen");
					int aantalGespeeld = rs.getInt("aantalGespeeld");

					speler = new Speler(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		ssh.closeConnection();
		return speler;
	}

	public List<Speler> geefAlleSpelers() {
		List<Speler> spelers = new ArrayList<>();
		Connectie ssh = new Connectie();
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement query = conn.prepareStatement("SELECT * FROM Speler");
				ResultSet rs = query.executeQuery()) {

			while (rs.next()) {
				String gebruikersnaam = rs.getString("gebruikersnaam");
				int geboortejaar = rs.getInt("geboortejaar");
				Speler speler = new Speler(gebruikersnaam, geboortejaar);
				spelers.add(speler);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		ssh.closeConnection();
		return spelers;
	}

	public void updateGewonnenEnGespeeld(String gebruikersnaam, int aantalGewonnen, int aantalGespeeld) {
		try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
				PreparedStatement query = conn.prepareStatement(
						"UPDATE Speler SET aantalGewonnen = ?, aantalGespeeld = ? WHERE gebruikersnaam = ?")) {
			query.setInt(1, aantalGewonnen);
			query.setInt(2, aantalGespeeld);
			query.setString(3, gebruikersnaam);

			query.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public SpelerDTO geefSpelerDTO(String gebruikersnaam) {
		Speler speler = geefSpeler(gebruikersnaam);
		if (speler != null) {
			return new SpelerDTO(speler);
		}
		return null;
	}

}
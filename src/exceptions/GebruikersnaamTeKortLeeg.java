package exceptions;

public class GebruikersnaamTeKortLeeg extends RuntimeException {
	

	public GebruikersnaamTeKortLeeg() {
		super("Gebruikersnaam is te kort of leeg");
	}

	public GebruikersnaamTeKortLeeg(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	

}

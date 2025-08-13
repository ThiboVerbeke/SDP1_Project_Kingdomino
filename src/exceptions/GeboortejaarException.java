package exceptions;

public class GeboortejaarException extends RuntimeException {

	public GeboortejaarException(){
		super("Het geboortejaar is ongeldig. U moet minstens 6 jaar oud zijn.")	;
	}
	
	public GeboortejaarException(String message) {
		super(message);
	}
}

package domein;

public class DominoTegel implements Comparable<DominoTegel> {

	private int nummer;
	private Vak vak1;
	private Vak vak2;
	private boolean isSelected = false;

	public DominoTegel(int nummer) {

		setNummer(nummer);

		this.vak1 = new Vak();
		this.vak2 = new Vak();
	}

	public int getNummer() {
		return nummer;
	}

	public Vak getVak1() {
		return vak1;
	}

	public Vak getVak2() {
		return vak2;
	}

	public final void setNummer(int nummer) {
		this.nummer = nummer;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public int compareTo(DominoTegel other) {
		return Integer.compare(this.nummer, other.nummer);
	}

}

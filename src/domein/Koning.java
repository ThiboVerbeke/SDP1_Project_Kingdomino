package domein;

import utils.KleurEnum;

public class Koning
{
	private KleurEnum kleur;
	private boolean isGeplaatstOpStartKolom;
	private boolean isGeplaatstOpEindKolom;

	public Koning()
	{

		isGeplaatstOpStartKolom = false;
		isGeplaatstOpEindKolom = false;
	}

	public KleurEnum getKleur()
	{
		return kleur;
	}

	public void setKleur(KleurEnum kleur)
	{
		boolean isValidKleur = false;
		for (KleurEnum enumKleur : KleurEnum.values())
		{
			if (enumKleur.equals(kleur))
			{
				isValidKleur = true;
			}
		}

		// Als de kleur niet overeenkomt met een waarde in de enumlijst
		if (!isValidKleur)
		{
			throw new IllegalArgumentException("Ongeldige kleur: " + kleur);
			// Of je kunt hier andere passende acties ondernemen
		}
		this.kleur = kleur;
	}

	public boolean isGeplaatstOpStartKolom()
	{
		return isGeplaatstOpStartKolom;
	}

	public void setGeplaatstOpStartKolom(boolean isGeplaatstOpStartKolom)
	{
		this.isGeplaatstOpStartKolom = isGeplaatstOpStartKolom;
	}

	public boolean isGeplaatstOpEindKolom()
	{
		return isGeplaatstOpEindKolom;
	}

	public void setGeplaatstOpEindKolom(boolean isGeplaatstOpEindKolom)
	{
		this.isGeplaatstOpEindKolom = isGeplaatstOpEindKolom;
	}

}

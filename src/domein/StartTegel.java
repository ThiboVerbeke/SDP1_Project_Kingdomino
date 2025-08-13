package domein;

import utils.KleurEnum;

public class StartTegel
{
	private KleurEnum kleur;

	public StartTegel()
	{

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

}

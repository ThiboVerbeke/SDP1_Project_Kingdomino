package domein;

import java.util.Random;

import utils.LandschapTypeEnum;

public class Vak
{
	private LandschapTypeEnum landschapType;
	private int aantalkronen;

	public Vak()
	{
		Random rand = new Random();
		int randomIndex = rand.nextInt(LandschapTypeEnum.values().length);
		this.landschapType = LandschapTypeEnum.values()[randomIndex];
		this.aantalkronen = (rand.nextInt(4)); // Willekeurig getal tussen 0 en 3
	}

	public LandschapTypeEnum getLandschapType()
	{
		return landschapType;
	}

	public int getAantalkronen()
	{
		return aantalkronen;
	}

}

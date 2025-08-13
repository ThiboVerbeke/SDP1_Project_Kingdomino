package utils;

import java.util.Locale;

public enum LandschapTypeEnum {
	AARDE("Aarde", "Earth"), BOS("Bos", "Forest"), GRAS("Gras", "Gras"), MIJN("Mijn", "Mine"), WATER("Water", "Water"),
	ZAND("Zand", "Sand");

	private final String nlTranslation;
	private final String enTranslation;

	LandschapTypeEnum(String nlTranslation, String enTranslation) {
		this.nlTranslation = nlTranslation;
		this.enTranslation = enTranslation;
	}

	public String getTranslation(String languageCode) {
		switch (languageCode) {
		case "nl":
			return nlTranslation;
		default:
			return enTranslation;
		}
	}

	public static LandschapTypeEnum getByTranslation(String translation, Locale locale) {
		LandschapTypeEnum[] values = LandschapTypeEnum.values();
		for (LandschapTypeEnum landschapType : values) {
			String translatedlandschapType = landschapType.getTranslation(locale.getLanguage());
			if (translatedlandschapType.equalsIgnoreCase(translation)) {
				return landschapType;
			}
		}
		return null; // Return null if no matching color is found
	}
}

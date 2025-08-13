package utils;

import java.util.Locale;

public enum KleurEnum {
	RED("Rood", "Red"), BLUE("Blauw", "Blue"), GREEN("Groen", "Green"), YELLOW("Geel", "Yellow");

	private final String nlTranslation;
	private final String enTranslation;

	KleurEnum(String nlTranslation, String enTranslation) {
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

	public static KleurEnum getByTranslation(String translation, Locale locale) {
		KleurEnum[] values = KleurEnum.values();
		for (KleurEnum kleur : values) {
			String translatedColorName = kleur.getTranslation(locale.getLanguage());
			if (translatedColorName.equalsIgnoreCase(translation)) {
				return kleur;
			}
		}
		return null; // Return null if no matching color is found
	}

}

package utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Taal {
	public static ResourceBundle getResourceBundle(String language) {
		ResourceBundle bundle = null;

		if (language.equals("NL")) {
			bundle = ResourceBundle.getBundle("messages", new Locale("nl", "NL"));
		} else if (language.equals("EN")) {
			bundle = ResourceBundle.getBundle("messages", new Locale("en", "US"));
		}

		return bundle;
	}
}

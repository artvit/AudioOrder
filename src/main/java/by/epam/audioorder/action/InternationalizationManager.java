package by.epam.audioorder.action;

import java.util.Locale;
import java.util.ResourceBundle;

public class InternationalizationManager {
    private static final String BUNDLE = "i18n.i18n";

    private InternationalizationManager() { }

    public static String getProperty(String key, Locale  locale) {
        return ResourceBundle.getBundle(BUNDLE, locale).getString(key);
    }

    public static String getProperty(String key) {
        return ResourceBundle.getBundle(BUNDLE, Locale.ROOT).getString(key);
    }
}

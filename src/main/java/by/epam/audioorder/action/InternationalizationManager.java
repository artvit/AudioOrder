package by.epam.audioorder.action;

import java.util.ResourceBundle;

public class InternationalizationManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.i18n");

    private InternationalizationManager() { }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}

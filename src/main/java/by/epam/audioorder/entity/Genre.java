package by.epam.audioorder.entity;

import by.epam.audioorder.action.InternationalizationManager;

import java.util.Locale;

public enum Genre {
    ANY,
    POP,
    ROCK,
    RAP,
    HOUSE_DANCE,
    ALTERNATIVE,
    INSTRUMENTAL,
    METAL,
    DUBSTEP,
    JAZZ,
    DNB,
    TRANCE,
    ACOUSTIC,
    REGGAE,
    CLASSICAL;

    public String toLocalizedString(Locale locale) {
        return InternationalizationManager.getProperty("genre." + this.name().toLowerCase(), locale);
    }

    @Override
    public String toString() {
        return InternationalizationManager.getProperty("genre." + this.name().toLowerCase());
    }
}

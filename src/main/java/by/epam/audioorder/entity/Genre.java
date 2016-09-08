package by.epam.audioorder.entity;

import by.epam.audioorder.action.InternationalizationManager;

public enum Genre {
    ANY,
    POP,
    ROCK,
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

    public Genre[] getValues() {
        return Genre.values();
    }

    @Override
    public String toString() {
        return InternationalizationManager.getProperty("genre." + this.name().toLowerCase());
    }
}

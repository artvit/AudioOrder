package by.bsu.audioorder.entity;

import java.util.Objects;

public class Track {
    private long trackId;
    private String title;
    private Artist artist;
    private Genre genre;
    private int duration;
    private String path;
    private double cost;
    private int releasedYear;
    private boolean bought;

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getReleasedYear() {
        return releasedYear;
    }

    public void setReleasedYear(int releasedYear) {
        this.releasedYear = releasedYear;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return trackId == track.trackId &&
                duration == track.duration &&
                Double.compare(track.cost, cost) == 0 &&
                releasedYear == track.releasedYear &&
                Objects.equals(title, track.title) &&
                Objects.equals(artist, track.artist) &&
                genre == track.genre &&
                Objects.equals(path, track.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackId, title, artist, genre, duration, path, cost, releasedYear);
    }
}

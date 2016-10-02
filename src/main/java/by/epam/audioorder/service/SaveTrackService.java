package by.epam.audioorder.service;

import by.epam.audioorder.dao.ArtistDAO;
import by.epam.audioorder.dao.TrackDAO;
import by.epam.audioorder.entity.Artist;
import by.epam.audioorder.entity.Genre;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveTrackService {
    private static final Logger LOGGER = LogManager.getLogger();

    public void addTrack(String artistName, String title, int year, Genre genre, int duration, double cost, String link) {
        try {
            ArtistDAO artistDAO = new ArtistDAO();
            Artist artist = artistDAO.findByName(artistName);
            if (artist == null) {
                artist = new Artist();
                artist.setName(artistName);
                artistDAO.insert(artist);
            }
            Track track = new Track();
            track.setArtist(artist);
            track.setGenre(genre);
            track.setTitle(title);
            track.setReleasedYear(year);
            track.setCost(cost);
            track.setPath(link);
            track.setDuration(duration);
            TrackDAO trackDAO = new TrackDAO();
            trackDAO.insert(track);
        } catch (DAOException e) {
            LOGGER.error("Exception in DAO");
        }
    }

    public void saveTrack(Track track, String artistName, String title, int year, Genre genre, int duration, double cost, String link) {
        try {
            if (!track.getArtist().getName().equals(artistName)) {
                ArtistDAO artistDAO = new ArtistDAO();
                Artist artist = artistDAO.findByName(artistName);
                if (artist == null) {
                    artist = new Artist();
                    artist.setName(artistName);
                    artistDAO.insert(artist);
                }
                track.setArtist(artist);
            }
            if (track.getGenre() != genre) {
                track.setGenre(genre);
            }
            if (!track.getTitle().equals(title)) {
                track.setTitle(title);
            }
            if (track.getReleasedYear() != year) {
                track.setReleasedYear(year);
            }
            if (track.getCost() != cost) {
                track.setCost(cost);
            }
            if (track.getDuration() != duration) {
                track.setDuration(duration);
            }
            if (link != null && !track.getPath().equals(link)) {
                track.setPath(link);
            }
            TrackDAO trackDAO = new TrackDAO();
            trackDAO.update(track);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}

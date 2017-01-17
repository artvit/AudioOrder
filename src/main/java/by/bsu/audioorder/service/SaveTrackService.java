package by.bsu.audioorder.service;

import by.bsu.audioorder.dao.ArtistDAO;
import by.bsu.audioorder.dao.TrackDAO;
import by.bsu.audioorder.entity.Genre;
import by.bsu.audioorder.entity.Artist;
import by.bsu.audioorder.entity.Track;
import by.bsu.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveTrackService {
    private static final Logger LOGGER = LogManager.getLogger();

    public boolean addTrack(String artistName, String title, int year, Genre genre, int duration, double cost, String link) {
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
            return true;
        } catch (DAOException e) {
            LOGGER.error("Exception in DAO", e);
            return false;
        }
    }

    public boolean saveTrack(Track track, String artistName, String title, int year, Genre genre, int duration, double cost, String link) {
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
            return true;
        } catch (DAOException e) {
            LOGGER.error("Cannot update track " + track.getTrackId());
            return false;
        }
    }
}

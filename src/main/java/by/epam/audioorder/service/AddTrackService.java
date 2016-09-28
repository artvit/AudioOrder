package by.epam.audioorder.service;

import by.epam.audioorder.dao.ArtistDAO;
import by.epam.audioorder.dao.TrackDAO;
import by.epam.audioorder.entity.Artist;
import by.epam.audioorder.entity.Genre;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.exception.DAOException;

public class AddTrackService {
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
            e.printStackTrace();
        }
    }
}

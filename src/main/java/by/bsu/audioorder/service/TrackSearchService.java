package by.bsu.audioorder.service;

import by.bsu.audioorder.dao.TrackDAO;
import by.bsu.audioorder.entity.Genre;
import by.bsu.audioorder.entity.Track;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TrackSearchService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int ROWS_PER_PAGE = 30;

    public SearchResult<Track> searchTrack(String searchQuery, Genre genre, int page, User user) {
        TrackDAO trackDAO = new TrackDAO();
        List<Track> tracks = null;
        try {
            if ((searchQuery == null || searchQuery.isEmpty()) && (genre == Genre.ANY || genre == null)) {
                tracks = trackDAO.findAll(page, ROWS_PER_PAGE);
            } else {
                tracks = trackDAO.search(searchQuery, genre, page, ROWS_PER_PAGE);
            }
            if (user != null) {
                for (Track track : tracks) {
                    if (trackDAO.checkUserBoughtTrack(user, track)) {
                        track.setBought(true);
                    }
                }
            }
        } catch (DAOException e) {
            LOGGER.error("Cannot get track list data from DAO", e);
        }
        return new SearchResult<>(tracks, trackDAO.getPagesNumber());
    }
}

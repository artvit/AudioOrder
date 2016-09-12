package by.epam.audioorder.service;

import by.epam.audioorder.dao.TrackDAO;
import by.epam.audioorder.entity.Genre;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TrackSearchService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final int ROWS_PER_PAGE = 30;

    public SearchResult<Track> searchTrack(String searchQuery, Genre genre, int page) {
        TrackDAO trackDAO = new TrackDAO();
        List<Track> tracks = null;
        try {
            if ((searchQuery == null || searchQuery.isEmpty()) && (genre == Genre.ANY || genre == null)) {
                tracks = trackDAO.findAll(page, ROWS_PER_PAGE);
            } else {
                tracks = trackDAO.search(searchQuery, genre, page, ROWS_PER_PAGE);
            }
        } catch (DAOException e) {
            LOGGER.error("Cannot get track list data from DAO", e);
        }
        return new SearchResult<>(tracks, trackDAO.getPagesNumber());
    }
}

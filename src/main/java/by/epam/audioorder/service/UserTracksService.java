package by.epam.audioorder.service;

import by.epam.audioorder.dao.TrackDAO;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserTracksService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final int ROWS_PER_PAGE = 30;

    public SearchResult<Track> getTracksForUser(User user, int page) {
        TrackDAO trackDAO = new TrackDAO();
        List<Track> tracks = null;
        try {
            tracks = trackDAO.findTracksForUser(user, page, ROWS_PER_PAGE);
        } catch (DAOException e) {
            LOGGER.error("DAO exception", e);
        }
        return new SearchResult<>(tracks, trackDAO.getPagesNumber());
    }
}

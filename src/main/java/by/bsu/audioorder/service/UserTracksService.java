package by.bsu.audioorder.service;

import by.bsu.audioorder.dao.TrackDAO;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.exception.DAOException;
import by.bsu.audioorder.entity.Track;
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

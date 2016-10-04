package by.epam.audioorder.service;

import by.epam.audioorder.dao.TrackDAO;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrackInfoService {
    private static final Logger LOGGER = LogManager.getLogger();

    public Track getTrackInfo(long id) {
        TrackDAO trackDAO = new TrackDAO();
        Track track = null;
        try {
            track = trackDAO.findById(id);

        } catch (DAOException e) {
            LOGGER.error("Track was not found for id " + id, e);
        }
        return track;
    }

    public boolean checkUserHasTrack(User user, Track track) {
        TrackDAO trackDAO = new TrackDAO();
        boolean result = false;
        try {
            result = trackDAO.checkUserBoughtTrack(user, track);
        } catch (DAOException e) {
            LOGGER.error("Exception in DAO", e);
        }
        return result;
    }
}

package by.epam.audioorder.service;

import by.epam.audioorder.dao.TrackDAO;
import by.epam.audioorder.entity.Track;
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
}

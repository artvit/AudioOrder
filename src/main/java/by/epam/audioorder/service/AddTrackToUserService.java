package by.epam.audioorder.service;

import by.epam.audioorder.dao.BonusDAO;
import by.epam.audioorder.dao.TrackDAO;
import by.epam.audioorder.entity.Bonus;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AddTrackToUserService {
    private static final Logger LOGGER = LogManager.getLogger();

    public void addTracksToUser(List<Track> tracks, User user, List<Bonus> usedBonuses) {
        TrackDAO trackDAO = new TrackDAO();
        for (Track track : tracks) {
            try {
                trackDAO.saveUserTrack(user, track);
                LOGGER.info("Track " + track.getTrackId() + " was saved to user " + user.getLogin() + " successfully");
            } catch (DAOException e) {
                LOGGER.error("Cannot add track " + track.getTrackId() + " to user " + user.getUserId(), e);
            }
        }
        BonusDAO bonusDAO = new BonusDAO();
        for (Bonus bonus : usedBonuses) {
            try {
                bonusDAO.delete(bonus);
            } catch (DAOException e) {
                LOGGER.error("Cannot delete bonus " + bonus.getBonusId() + " from database");
            }
        }
    }
}

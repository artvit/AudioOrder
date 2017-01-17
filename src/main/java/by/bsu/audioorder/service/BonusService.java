package by.bsu.audioorder.service;

import by.bsu.audioorder.dao.BonusDAO;
import by.bsu.audioorder.entity.Bonus;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BonusService {
    private static final Logger LOGGER = LogManager.getLogger();

    public List<Bonus> bonusesForUser(User user) {
        BonusDAO bonusDAO = new BonusDAO();
        List<Bonus> bonuses = null;
        try {
            bonuses = bonusDAO.findForUser(user);
        } catch (DAOException e) {
            LOGGER.error("Cannot get bonuses for user " + user.getLogin(), e);
        }
        return bonuses;
    }

    public Bonus bonusForId(long id) {
        BonusDAO bonusDAO = new BonusDAO();
        Bonus bonus = null;
        try {
            bonus = bonusDAO.findById(id);
        } catch (DAOException e) {
            LOGGER.error("Cannot get bonuses for id " + id, e);
        }
        return bonus;
    }
}

package by.bsu.audioorder.service;

import by.bsu.audioorder.dao.BonusDAO;
import by.bsu.audioorder.entity.Genre;
import by.bsu.audioorder.entity.Bonus;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveBonusService {
    private static final Logger LOGGER = LogManager.getLogger();

    public boolean saveBonus(long userId, Genre genre, int yearAfter, int yearBefore, double bonusValue, double sale) {
        Bonus bonus = new Bonus();
        User user = new User();
        user.setUserId(userId);
        bonus.setUser(user);
        bonus.setGenre(genre);
        bonus.setYearAfter(yearAfter);
        bonus.setYearBefore(yearBefore);
        bonus.setBonusValue(bonusValue);
        bonus.setSale(sale);
        BonusDAO bonusDAO = new BonusDAO();
        try {
            bonusDAO.insert(bonus);
        } catch (DAOException e) {
            LOGGER.error("Cannot create new bonus", e);
            return false;
        }
        return true;
    }
}

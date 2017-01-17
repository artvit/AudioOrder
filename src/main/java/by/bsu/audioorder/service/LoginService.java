package by.bsu.audioorder.service;

import by.bsu.audioorder.action.PasswordHandler;
import by.bsu.audioorder.dao.UserDAO;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginService {
    private static final Logger LOGGER = LogManager.getLogger();

    public User authenticate(String login, String password) {
        UserDAO userDAO = new UserDAO();
        User user = null;
        try {
            user = userDAO.findUserByLogin(login);
            if (user != null && password != null && user.getPasswordHash() != null) {
                if (!PasswordHandler.hashPassword(password).equals(user.getPasswordHash())) {
                    user = null;
                }
            }
        } catch (DAOException e) {
            LOGGER.error("DAO Exception", e);
        }
        return user;
    }
}

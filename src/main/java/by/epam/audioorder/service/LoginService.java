package by.epam.audioorder.service;

import by.epam.audioorder.action.PasswordHandler;
import by.epam.audioorder.dao.UserDAO;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.exception.DAOException;

public class LoginService {
    public User authenticate(String login, String password) {
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findUserByLogin(login);
            if (PasswordHandler.validateUser(user, password)) {
                return user;
            } else {
                return null;
            }
        } catch (DAOException e) {
            return null;
        }
    }
}

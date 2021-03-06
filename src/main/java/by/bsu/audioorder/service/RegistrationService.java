package by.bsu.audioorder.service;

import by.bsu.audioorder.action.InternationalizationManager;
import by.bsu.audioorder.action.PasswordHandler;
import by.bsu.audioorder.dao.UserDAO;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.exception.DAOException;
import by.bsu.audioorder.exception.ServiceException;

import java.util.regex.Pattern;

public class RegistrationService {
    private static final Pattern LOGIN_PATTERN = Pattern.compile("\\w{4,60}");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("\\w{8,60}");

    public void signUp(String login, String email, String password, String passwordConfirm) throws ServiceException {
        if (!LOGIN_PATTERN.matcher(login).matches()) {
            throw new ServiceException(InternationalizationManager.getProperty("registration.error.login"));
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new ServiceException(InternationalizationManager.getProperty("registration.error.password"));
        }
        if (!password.equals(passwordConfirm)) {
            throw new ServiceException(InternationalizationManager.getProperty("registration.error.confirmation"));
        }
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setPasswordHash(PasswordHandler.hashPassword(password));
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.insert(user);
        } catch (DAOException e) {
            throw new ServiceException(InternationalizationManager.getProperty("registration.error.taken-login-email"));
        }
    }
}

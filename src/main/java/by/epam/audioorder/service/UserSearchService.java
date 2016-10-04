package by.epam.audioorder.service;

import by.epam.audioorder.dao.UserDAO;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserSearchService {
    private static final Logger LOGGER = LogManager.getLogger();

    private int ROWS_PER_PAGE = 30;

    public SearchResult<User> searchUser(String searchQuery, int page) {
        UserDAO userDAO = new UserDAO();
        List<User> users = null;
        try {
            if (searchQuery == null || searchQuery.isEmpty()) {
                users = userDAO.findAll(page, ROWS_PER_PAGE);
            } else {
                users = userDAO.search(searchQuery, page, ROWS_PER_PAGE);
            }
        } catch (DAOException e) {
            LOGGER.error("Cannot get user list data from DAO", e);
        }
        return new SearchResult<>(users, userDAO.getPagesNumber());
    }

    public User findUserById(long id) {
        UserDAO userDAO = new UserDAO();
        User user = null;
        try {
            user = userDAO.findById(id);
        } catch (DAOException e) {
            LOGGER.error("Cannot find user with id " + id);
        }
        return user;
    }
}

package by.epam.audioorder.dao;

import by.epam.audioorder.entity.Track;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.exception.DAOException;
import by.epam.audioorder.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String USER_ID = "user_id";
    private static final String LOGIN = "login";
    private static final String PASS_HASH = "passhash";
    private static final String EMAIL = "email";
    private static final String ROLE = "role";
    private static final String BALANCE = "balance";

    private static final String ORDER_LOGIN = " ORDER BY " + LOGIN;
    private static final String LIMIT = " LIMIT ? OFFSET ?";
    private static final String USER_ALL_COUNT_ROWS = "SELECT COUNT(*) FROM user";

    private static final String USER_BY_LOGIN =
            "SELECT " + USER_ID + ", " +
                        LOGIN + ", " +
                        PASS_HASH + ", " +
                        EMAIL + ", " +
                        ROLE +", " +
                        BALANCE +
                    " FROM user WHERE " + LOGIN + " = ?";
    private static final String USER_ALL =
            "SELECT " + USER_ID + ", " +
                        LOGIN + ", " +
                        PASS_HASH + ", " +
                        EMAIL + ", " +
                        ROLE +", " +
                        BALANCE +
                    " FROM user";
    private static final String USER_SEARCH =
            "SELECT " + USER_ID + ", " +
                    LOGIN + ", " +
                    PASS_HASH + ", " +
                    EMAIL + ", " +
                    ROLE +", " +
                    BALANCE +
                    " FROM user WHERE " + LOGIN + " LIKE ? OR " + EMAIL + " LIKE ?";
    private static final String USER_BY_ID =
            "SELECT " + USER_ID + ", " +
                        LOGIN + ", " +
                        PASS_HASH + ", " +
                        EMAIL + ", " +
                        ROLE +", " +
                        BALANCE +
                    " FROM user WHERE " + USER_ID + " = ?;";
    private static final String INSERT_USER = "INSERT INTO user (" +
            LOGIN + ", " +
            PASS_HASH + ", " +
            EMAIL + ", " +
            ROLE +", " +
            BALANCE +
            ") VALUES (?, ?, ?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM user WHERE " + USER_ID + " = ?;";
    private static final String UPDATE_USER = "UPDATE user SET " +
            LOGIN + " = ?," +
            PASS_HASH + " = ?," +
            EMAIL + " = ?," +
            ROLE + " = ?," +
            BALANCE + " = ? " +
            "WHERE " + USER_ID + " = ?";

    private int pagesNumber = 0;

    public User findUserByLogin(String login) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            User user;
            if (result.next()) {
                user = createUser(result);
                LOGGER.info("Successful reading from database");
            } else {
                throw new DAOException("No result was found for login " + login);
            }
            return user;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    @Override
    public List<User> findAll(int page, int rowsPerPage) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_ALL + ORDER_LOGIN + LIMIT)) {
            statement.setInt(1, rowsPerPage);
            statement.setInt(2, (page - 1) * rowsPerPage);
            ResultSet result = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (result.next()) {
                User user = createUser(result);
                users.add(user);
            }
            try (PreparedStatement statementCount = connection.prepareStatement(USER_ALL_COUNT_ROWS)) {
                ResultSet resultCount = statementCount.executeQuery();
                if (resultCount.next()) {
                    pagesNumber = (int) Math.ceil(resultCount.getInt(1) / rowsPerPage);
                }
            }
            LOGGER.info("Successful reading from database");
            return users;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    public List<User> search(String searchQuery, int page, int rowsPerPage) throws DAOException {
        if (searchQuery == null ||  searchQuery.isEmpty()) {
            throw new DAOException("Empty search query passed to search method");
        }
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_SEARCH + ORDER_LOGIN + LIMIT)) {
            statement.setString(1, "%" + searchQuery + "%");
            statement.setString(2, "%" + searchQuery + "%");
            statement.setInt(3, rowsPerPage);
            statement.setInt(4, (page - 1) * rowsPerPage);
            ResultSet result = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (result.next()) {
                User user = createUser(result);
                users.add(user);
            }
            try (PreparedStatement statementCount = connection.prepareStatement(USER_ALL_COUNT_ROWS)) {
                ResultSet resultCount = statementCount.executeQuery();
                if (resultCount.next()) {
                    pagesNumber = (int) Math.ceil(resultCount.getInt(1) / rowsPerPage);
                }
            }
            LOGGER.info("Successful reading from database");
            return users;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    @Override
    public User findById(long id) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            User user;
            if (result.next()) {
                user = createUser(result);
                LOGGER.info("Successful reading from database");
            } else {
                throw new DAOException("No result was found for id " + id);
            }
            return user;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    @Override
    public void delete(User entity) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setLong(1, entity.getUserId());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("User " + entity.getLogin() + " was deleted");
            } else {
                LOGGER.info("Delete successful");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    @Override
    public void insert(User entity) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPasswordHash());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getRole().toString().toLowerCase());
            statement.setDouble(5, entity.getBalance());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("New user was not inserted");
            } else {
                LOGGER.info("Insertion successful");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    @Override
    public void update(User entity) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPasswordHash());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getRole().toString().toLowerCase());
            statement.setDouble(5, entity.getBalance());
            statement.setLong(6, entity.getUserId());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("User "  + entity.getLogin() + " was not updated");
            } else {
                LOGGER.info("Update successful");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    public int getPagesNumber() {
        return pagesNumber;
    }
    
    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong(USER_ID));
        user.setLogin(resultSet.getString(LOGIN));
        user.setPasswordHash(resultSet.getString(PASS_HASH));
        user.setEmail(resultSet.getString(EMAIL));
        user.setRole(resultSet.getString(ROLE));
        user.setBalance(resultSet.getDouble(BALANCE));
        return user;
    }
}

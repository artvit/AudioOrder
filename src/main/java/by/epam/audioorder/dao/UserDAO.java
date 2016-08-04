package by.epam.audioorder.dao;

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
    private static final String PASS = "pass";
    private static final String EMAIL = "email";
    private static final String ROLE = "role";
    private static final String BALANCE = "balance";

    private static final String USER_BY_LOGIN =
            "SELECT " + USER_ID + ", " +
                        LOGIN + ", " +
                        PASS + ", " +
                        EMAIL + ", " +
                        ROLE +", " +
                        BALANCE +
                    " FROM user WHERE login = ?";
    private static final String USER_ALL =
            "SELECT " + USER_ID + ", " +
                        LOGIN + ", " +
                        PASS + ", " +
                        EMAIL + ", " +
                        ROLE +", " +
                        BALANCE +
                    " FROM user;";
    private static final String USER_BY_ID =
            "SELECT " + USER_ID + ", " +
                        LOGIN + ", " +
                        PASS + ", " +
                        EMAIL + ", " +
                        ROLE +", " +
                        BALANCE +
                    " FROM user WHERE user_id = ?;";
    private static final String INSERT_USER = "INSERT INTO user (" +
            LOGIN + ", " +
            PASS + ", " +
            EMAIL + ", " +
            ROLE +", " +
            BALANCE +
            ") VALUES (?, ?, ?, ?, ?);";

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
    public List<User> findAll() throws DAOException {
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(USER_ALL)) {
            ResultSet result = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (result.next()) {
                User user = createUser(result);
                users.add(user);
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
             PreparedStatement statement = connection.prepareStatement(USER_BY_ID)){
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
    public void delete(User entity) {
//TODO
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
            statement.close();
            connection.close();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    @Override
    public void update(User entity) {
//TODO
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(USER_ID));
        user.setLogin(resultSet.getString(LOGIN));
        user.setPasswordHash(resultSet.getString(PASS));
        user.setEmail(resultSet.getString(EMAIL));
        user.setRole(resultSet.getString(ROLE));
        user.setBalance(resultSet.getDouble(BALANCE));
        return user;
    }
}

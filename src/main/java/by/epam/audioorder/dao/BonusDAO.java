package by.epam.audioorder.dao;

import by.epam.audioorder.entity.Bonus;
import by.epam.audioorder.entity.Comment;
import by.epam.audioorder.entity.Genre;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.exception.DAOException;
import by.epam.audioorder.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class BonusDAO extends AbstractDAO<Bonus>{
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String BONUS_TABLE = "bonus";
    private static final String ID = "bonus_id";
    private static final String USER_ID = "user_id";
    private static final String AFTER = "tracks_after";
    private static final String BEFORE = "tracks_before";
    private static final String GENRE = "genre";
    private static final String BONUS = "bonus";
    private static final String SALE = "sale";

    private static final String FIND_BY_ID = "SELECT " +
            ID + ", " +
            USER_ID + ", " +
            AFTER + ", " +
            BEFORE + ", " +
            GENRE + ", " +
            BONUS + ", " +
            SALE +
            " FROM " + BONUS_TABLE + " WHERE " + ID + " = ?";
    private static final String FIND_FOR_USER = "SELECT " +
            ID + ", " +
            USER_ID + ", " +
            AFTER + ", " +
            BEFORE + ", " +
            GENRE + ", " +
            BONUS + ", " +
            SALE +
            " FROM " + BONUS_TABLE + " WHERE " + USER_ID + " = ?";
    private static final String DELETE_BONUS = "DELETE FROM " + BONUS_TABLE + " WHERE " + ID + " = ?";
    private static final String INSERT_BONUS = "INSERT INTO " + BONUS_TABLE + "(" +
            USER_ID + ", " +
            AFTER + ", " +
            BEFORE + ", " +
            GENRE + ", " +
            BONUS + ", " +
            SALE +") VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public List<Bonus> findAll(int page, int rowsPerPage) throws DAOException {
        return null;
    }

    @Override
    public Bonus findById(long id) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Bonus bonus = null;
            if (result.next()) {
                bonus = createBonus(result);
                LOGGER.info("Successful reading from database");
            }
            return bonus;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    public List<Bonus> findForUser(User user) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_FOR_USER)) {
            statement.setLong(1, user.getUserId());
            ResultSet result = statement.executeQuery();
            List<Bonus> bonuses = new ArrayList<>();
            while (result.next()) {
                Bonus bonus = createBonus(result);
                bonuses.add(bonus);
            }
            LOGGER.info("Successful reading for user " + user.getLogin());
            return bonuses;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    @Override
    public void delete(Bonus entity) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BONUS)) {
            statement.setLong(1, entity.getBonusId());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("Bonus " + entity.getBonusId() + " was not deleted");
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
    public void insert(Bonus entity) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BONUS)) {
            statement.setLong(1, entity.getUser().getUserId());
            statement.setInt(2, entity.getYearAfter());
            statement.setInt(3, entity.getYearBefore());
            statement.setString(4, entity.getGenre().name().toLowerCase());
            statement.setDouble(5, entity.getBonusValue());
            statement.setDouble(6, entity.getSale());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("Bonus " + entity.getBonusId() + " was not deleted");
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
    public void update(Bonus entity) throws DAOException {

    }

    private Bonus createBonus(ResultSet resultSet) throws SQLException {
        Bonus bonus = new Bonus();
        bonus.setBonusId(resultSet.getLong(ID));
        User user = new User();
        user.setUserId(resultSet.getLong(USER_ID));
        bonus.setUser(user);
        bonus.setGenre(Genre.valueOf(resultSet.getString(GENRE).toUpperCase()));
        bonus.setYearAfter(resultSet.getInt(AFTER));
        bonus.setYearBefore(resultSet.getInt(BEFORE));
        bonus.setBonusValue(resultSet.getDouble(BONUS));
        bonus.setSale(resultSet.getDouble(SALE));
        return bonus;
    }
}

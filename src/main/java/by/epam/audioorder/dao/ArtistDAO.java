package by.epam.audioorder.dao;

import by.epam.audioorder.entity.Artist;
import by.epam.audioorder.exception.DAOException;
import by.epam.audioorder.pool.ConnectionPool;
import by.epam.audioorder.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class ArtistDAO {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String ID = "artist_id";
    private static final String NAME = "name";

    private static final String ARTIST_BY_LOGIN = "SELECT " + ID + ", " + NAME + " FROM artist WHERE " + NAME + " = ?";
    private static final String ARTIST_BY_ID = "SELECT " + ID + ", " + NAME + " FROM artist WHERE " + ID + " = ?";
    private static final String INSERT_ARTIST = "INSERT INTO artist (" + NAME + ") VALUES (?)";
    private static final String DELETE_ARTIST = "DELETE FROM artist WHERE " + ID + " = ?";
    private static final String UPDATE_ARTIST = "UPDATE artist SET " + NAME + " = ? WHERE " + ID + " = ?";

    public Artist findById(long id) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ARTIST_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Artist artist = null;
            if (result.next()) {
                artist = createArtist(result);
                LOGGER.info("Successful reading from database");
            }
            return artist;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    public void delete(Artist entity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ARTIST)) {
            statement.setLong(1, entity.getArtistId());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("Artist was deleted");
            } else {
                LOGGER.info("Delete successful");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    public void insert(Artist entity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("New user was not inserted");
            } else {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setArtistId(resultSet.getLong(1));
                    LOGGER.info("Insertion successful");
                }
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    public void update(Artist entity) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ARTIST)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getArtistId());
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

    public Artist findByName(String name) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ARTIST_BY_LOGIN)) {
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            Artist artist = null;
            if (result.next()) {
                artist = createArtist(result);
                LOGGER.info("Successful reading from database");
            }
            return artist;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    private Artist createArtist(ResultSet resultSet) throws SQLException {
        Artist artist = new Artist();
        artist.setArtistId(resultSet.getLong(ID));
        artist.setName(resultSet.getString(NAME));
        return artist;
    }
}

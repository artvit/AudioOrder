package by.epam.audioorder.dao;

import by.epam.audioorder.entity.Artist;
import by.epam.audioorder.entity.Genre;
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

public class TrackDAO extends AbstractDAO<Track> {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String ID = "track_id";
    private static final String TITLE = "title";
    private static final String ARTIST_NAME = "name";
    private static final String ARTIST_ID = "artist_id";
    private static final String DURATION = "duration";
    private static final String COST = "cost";
    private static final String GENRE = "genre";
    private static final String PATH = "link";
    private static final String RELEASED = "released";
    private static final String USER_ID = "user_id";

    private static final String TRACK_ALL_COUNT_ROWS = "SELECT COUNT(*) FROM track";

    private static final String TRACK_ALL = "SELECT " +
            ID +  ", " +
            TITLE +  ", " +
            ARTIST_NAME + ", " +
            ARTIST_ID + ", " +
            DURATION + ", " +
            COST + ", " +
            PATH + ", " +
            RELEASED + ", " +
            GENRE + " FROM track LEFT JOIN artist USING(artist_id)";
    private static final String TRACK_BY_ID = "SELECT " +
            ID +  ", " +
            TITLE +  ", " +
            ARTIST_NAME + ", " +
            ARTIST_ID + ", " +
            DURATION + ", " +
            COST + ", " +
            PATH + ", " +
            RELEASED + ", " +
            GENRE + " FROM track LEFT JOIN artist USING(artist_id) WHERE " + ID +" = ?";
    private static final String DELETE_TRACK = "DELETE FROM track WHERE " + ID + " = ?";
    private static final String INSERT_TRACK = "INSERT INTO track (" +
            ARTIST_ID + ", " +
            TITLE + ", " +
            GENRE + ", " +
            DURATION + ", " +
            PATH + ", " +
            COST + ", " +
            RELEASED + ") VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_TRACK = "UPDATE track SET " +
            ARTIST_ID + " = ?, " +
            TITLE + " = ?, " +
            GENRE + " = ?, " +
            DURATION + " = ?, " +
            PATH + " = ?, " +
            COST + " = ?, " +
            RELEASED + " = ?" + " WHERE " + ID + " = ?";
    private static final String TRACKS_FOR_USER = "SELECT " +
            ID +  ", " +
            TITLE +  ", " +
            ARTIST_NAME + ", " +
            ARTIST_ID + ", " +
            DURATION + ", " +
            COST + ", " +
            PATH + ", " +
            RELEASED + ", " +
            GENRE +
            " FROM user_track LEFT JOIN track USING(track_id) LEFT JOIN artist USING(artist_id)" +
            "WHERE " + USER_ID + " = ?";

    private static final String LIMIT = " LIMIT ? OFFSET ?";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String SEARCH_TITLE_ARTIST = "(" + TITLE + " LIKE ? OR " + ARTIST_NAME + " LIKE ?)";
    private static final String SEARCH_GENRE = GENRE + " = ?";

    private int pagesNumber;

    @Override
    public List<Track> findAll(int page, int rowsPerPage) throws DAOException {
        try (Connection connection = getConnection();
              PreparedStatement statement = connection.prepareStatement(TRACK_ALL + LIMIT)) {
            statement.setInt(1, rowsPerPage);
            statement.setInt(2, (page - 1) * rowsPerPage);
            ResultSet result = statement.executeQuery();
            List<Track> tracks = new ArrayList<>();
            while (result.next()) {
                Track track = createTrack(result);
                tracks.add(track);
            }
            try (PreparedStatement statementCount = connection.prepareStatement(TRACK_ALL_COUNT_ROWS)) {
                ResultSet resultCount = statementCount.executeQuery();
                if (resultCount.next()) {
                    pagesNumber = (int) Math.ceil(resultCount.getInt(1) / rowsPerPage);
                }
            }
            LOGGER.info("Successful reading from database");
            return tracks;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    public List<Track> findTracksForUser(User user, int page, int rowsPerPage) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(TRACKS_FOR_USER + LIMIT)) {
            statement.setLong(1, user.getUserId());
            statement.setInt(2, rowsPerPage);
            statement.setInt(3, (page - 1) * rowsPerPage);
            ResultSet result = statement.executeQuery();
            List<Track> tracks = new ArrayList<>();
            while (result.next()) {
                Track track = createTrack(result);
                tracks.add(track);
            }
            try (PreparedStatement statementCount = connection.prepareStatement(TRACK_ALL_COUNT_ROWS)) {
                ResultSet resultCount = statementCount.executeQuery();
                if (resultCount.next()) {
                    pagesNumber = (int) Math.ceil(resultCount.getInt(1) / rowsPerPage);
                }
            }
            LOGGER.info("Successful reading from database");
            return tracks;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    public List<Track> search(String searchQuery, Genre genre, int page, int rowsPerPage) throws DAOException {
        String sqlQuery = TRACK_ALL + WHERE;
        boolean statementChanged = false;
        boolean isQuery = false;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            isQuery = true;
            sqlQuery += SEARCH_TITLE_ARTIST;
            statementChanged = true;
        }
        boolean isGenre = false;
        if (genre != null && genre != Genre.ANY) {
            isGenre = true;
            if (statementChanged) {
                sqlQuery += AND;
            }
            sqlQuery += SEARCH_GENRE;
        }
        sqlQuery += LIMIT;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            int parametersCounter = 1;
            if (isQuery) {
                statement.setString(parametersCounter++, "%" + searchQuery + "%");
                statement.setString(parametersCounter++, "%" + searchQuery + "%");
            }
            if (isGenre) {
                statement.setString(parametersCounter++, genre.name().toLowerCase());
            }
            statement.setInt(parametersCounter++, rowsPerPage);
            statement.setInt(parametersCounter, (page - 1) * rowsPerPage);
            ResultSet result = statement.executeQuery();
            List<Track> tracks = new ArrayList<>();
            while (result.next()) {
                Track track = createTrack(result);
                tracks.add(track);
            }
            try (PreparedStatement statementCount = connection.prepareStatement(TRACK_ALL_COUNT_ROWS)) {
                ResultSet resultCount = statementCount.executeQuery();
                if (resultCount.next()) {
                    pagesNumber = (int) Math.ceil(resultCount.getInt(1) / rowsPerPage);
                }
            }
            LOGGER.info("Successful reading from database");
            return tracks;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    @Override
    public Track findById(long id) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(TRACK_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Track track;
            if (result.next()) {
                track = createTrack(result);
                LOGGER.info("Successful reading from database");
            } else {
                throw new DAOException("No result was found for id " + id);
            }
            return track;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    @Override
    public void delete(Track entity) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TRACK)) {
            statement.setLong(1, entity.getTrackId());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("New user was deleted");
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
    public void insert(Track entity) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_TRACK)) {
            statement.setLong(1, entity.getArtist().getArtistId());
            statement.setString(2, entity.getTitle());
            statement.setString(3, entity.getGenre().name().toLowerCase());
            statement.setInt(4, entity.getDuration());
            statement.setString(5, entity.getPath());
            statement.setDouble(6, entity.getCost());
            statement.setInt(7, entity.getReleasedYear());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("New track was not inserted");
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
    public void update(Track entity) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TRACK)) {
            statement.setLong(1, entity.getArtist().getArtistId());
            statement.setString(2, entity.getTitle());
            statement.setString(3, entity.getGenre().name().toLowerCase());
            statement.setInt(4, entity.getDuration());
            statement.setString(5, entity.getPath());
            statement.setDouble(6, entity.getCost());
            statement.setInt(7, entity.getReleasedYear());
            statement.setLong(8, entity.getTrackId());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("Update was not complete");
            } else {
                LOGGER.info("Insertion successful");
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

    private Track createTrack(ResultSet resultSet) throws SQLException {
        Track track = new Track();
        track.setTrackId(resultSet.getLong(ID));
        track.setTitle(resultSet.getString(TITLE));
        Artist artist = new Artist();
        artist.setArtistId(resultSet.getLong(ARTIST_ID));
        artist.setName(resultSet.getString(ARTIST_NAME));
        track.setArtist(artist);
        track.setDuration(resultSet.getInt(DURATION));
        track.setCost(resultSet.getDouble(COST));
        track.setGenre(Genre.valueOf(resultSet.getString(GENRE).toUpperCase()));
        track.setReleasedYear(resultSet.getInt(RELEASED));
        track.setPath(resultSet.getString(PATH));
        return track;
    }
}

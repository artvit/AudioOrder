package by.epam.audioorder.dao;

import by.epam.audioorder.entity.Genre;
import by.epam.audioorder.entity.Track;
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
    private static final String ARTIST = "name";
    private static final String DURATION = "duration";
    private static final String COST = "cost";
    private static final String GENRE = "genre";

    private static final String TRACK_ALL = "SELECT " +
            ID +  ", " +
            TITLE +  ", " +
            ARTIST + ", " +
            DURATION + ", " +
            COST + ", " +
            GENRE + " FROM track LEFT JOIN artist USING(artist_id)";

    @Override
    public List<Track> findAll() throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(TRACK_ALL)) {
            ResultSet result = statement.executeQuery();
            List<Track> tracks = new ArrayList<>();
            while (result.next()) {
                Track track = createTrack(result);
                tracks.add(track);
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
        return null;
    }

    @Override
    public void delete(Track entity) throws DAOException {

    }

    @Override
    public void insert(Track entity) throws DAOException {

    }

    @Override
    public void update(Track entity) throws DAOException {

    }

    private Track createTrack(ResultSet resultSet) throws SQLException {
        Track track = new Track();
        track.setTrackId(resultSet.getLong(ID));
        track.setTitle(resultSet.getString(TITLE));
        track.setArtist(resultSet.getString(ARTIST));
        track.setDuration(resultSet.getInt(DURATION));
        track.setCost(resultSet.getDouble(COST));
        track.setGenre(Genre.valueOf(resultSet.getString(GENRE).toUpperCase()));
        return track;
    }
}

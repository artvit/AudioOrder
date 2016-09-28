package by.epam.audioorder.dao;

import by.epam.audioorder.entity.Comment;
import by.epam.audioorder.entity.Track;
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

public class CommentDAO extends AbstractDAO<Comment> {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String COMMENT_TABLE = "comment";

    private static final String ID = COMMENT_TABLE + ".comment_id";
    private static final String TRACK_ID = COMMENT_TABLE + ".track_id";
    private static final String USER_ID = COMMENT_TABLE + ".user_id";
    private static final String TEXT = COMMENT_TABLE + ".text";
    private static final String DATE = COMMENT_TABLE + ".date";

    private static final String LIMIT = " LIMIT ? OFFSET ?";
    private static final String COMMENT_FOR_TRACK_COUNT_ROWS = "SELECT COUNT(*) FROM comment WHERE " + TRACK_ID + " = ?";

    private static final String FIND_BY_TRACK = "SELECT " +
            ID + ", " +
            TRACK_ID + ", " +
            USER_ID + ", " +
            TEXT + ", " +
            DATE + " FROM comment WHERE " + TRACK_ID + " = ? ORDER BY " + DATE + " DESC";
    private static final String FIND_BY_ID = "SELECT " +
            ID + ", " +
            TRACK_ID + ", " +
            USER_ID + ", " +
            TEXT + ", " +
            DATE + " FROM comment WHERE " + ID + " = ?";
    private static final String INSERT_COMMENT = "INSERT INTO " + COMMENT_TABLE +
            " (" + TRACK_ID + ", " + USER_ID + ", " + TEXT + ", " + DATE+ ") VALUES (?, ?, ?, ?);";
    private static final String DELETE_COMMENT = "DELETE FROM " + COMMENT_TABLE + " WHERE " + ID + " = ?";

    private int pagesNumber = 0;

    @Override
    public List<Comment> findAll(int page, int rowsPerPage) throws DAOException {
        return null;
    }

    @Override
    public Comment findById(long id) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Comment comment = null;
            if (result.next()) {
                comment = createComment(result);
                LOGGER.info("Successful reading from database");
            }
            return comment;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    @Override
    public void delete(Comment entity) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_COMMENT)) {
            statement.setLong(1, entity.getCommentId());
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("Comment was deleted");
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
    public void insert(Comment entity) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_COMMENT)) {
            statement.setLong(1, entity.getTrack().getTrackId());
            statement.setLong(2, entity.getUser().getUserId());
            statement.setString(3, entity.getText());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getTime()));
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new DAOException("New comment was not inserted");
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
    public void update(Comment entity) throws DAOException {

    }

    public List<Comment> findForTrack(Track track, int page, int rowsPerPage) throws DAOException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TRACK + LIMIT)) {
            statement.setLong(1, track.getTrackId());
            statement.setInt(2, rowsPerPage);
            statement.setInt(3, (page - 1) * rowsPerPage);
            ResultSet result = statement.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while (result.next()) {
                Comment comment = createComment(result);
                comments.add(comment);
            }
            try (PreparedStatement statementCount = connection.prepareStatement(COMMENT_FOR_TRACK_COUNT_ROWS)) {
                statementCount.setLong(1, track.getTrackId());
                ResultSet resultCount = statementCount.executeQuery();
                if (resultCount.next()) {
                    pagesNumber = (int) Math.ceil(resultCount.getInt(1) / rowsPerPage);
                }
            }
            LOGGER.info("Successful reading from database");
            return comments;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Cannot get connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error in SQL", e);
        }
    }

    private Comment createComment(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(resultSet.getLong(ID));
        comment.setText(resultSet.getString(TEXT));
        comment.setTime(LocalDateTime.ofInstant(resultSet.getTimestamp(DATE).toInstant(), ZoneOffset.ofHours(0)));
        User user = new User();
        user.setUserId(resultSet.getLong(USER_ID));
        comment.setUser(user);
        Track track = new Track();
        track.setTrackId(resultSet.getLong(TRACK_ID));
        comment.setTrack(track);
        return comment;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }
}

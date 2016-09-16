package by.epam.audioorder.service;

import by.epam.audioorder.dao.CommentDAO;
import by.epam.audioorder.dao.TrackDAO;
import by.epam.audioorder.dao.UserDAO;
import by.epam.audioorder.entity.Comment;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class TrackCommentService {
    private static final Logger LOGGER = LogManager.getLogger();

    private int ROWS_PER_PAGE = 10;

    public SearchResult<Comment> findCommentsForTrack(Track track, int page) {
        CommentDAO commentDAO = new CommentDAO();
        List<Comment> feedback = null;
        try {
            feedback = commentDAO.findForTrack(track, page, ROWS_PER_PAGE);
            UserDAO userDAO =  new UserDAO();
            for (Comment comment : feedback) {
                User user = userDAO.findById(comment.getUser().getUserId());
                comment.setUser(user);
                comment.setTrack(track);
            }
        } catch (DAOException e) {
            LOGGER.error("Error in DAO", e);
        }
        return new SearchResult<>(feedback, commentDAO.getPagesNumber());
    }

    public boolean addComment(long trackId, String commentText, String login) {
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findUserByLogin(login);
            TrackDAO trackDAO = new TrackDAO();
            Track track = trackDAO.findById(trackId);
            Comment comment = new Comment();
            comment.setTrack(track);
            comment.setUser(user);
            comment.setText(commentText);
            comment.setTime(LocalDateTime.now(ZoneOffset.ofHours(0)));
            CommentDAO commentDAO = new CommentDAO();
            commentDAO.insert(comment);
            return true;
        } catch (DAOException e) {
            LOGGER.error("Error in DAO", e);
        }
        return false;
    }

    public boolean deleteComment(long commentId) {
        try {
            CommentDAO commentDAO = new CommentDAO();
            Comment comment = commentDAO.findById(commentId);
            commentDAO.delete(comment);
            return true;
        } catch (DAOException e) {
            return false;
        }
    }
}

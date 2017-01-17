package by.bsu.audioorder.command;

import by.bsu.audioorder.service.SearchResult;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.config.ParameterName;
import by.bsu.audioorder.entity.Comment;
import by.bsu.audioorder.entity.Track;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.service.TrackCommentService;
import by.bsu.audioorder.service.TrackInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrackInfoCommand implements Command{
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int page = 1;
        String pageParameter = request.getParameter(ParameterName.PAGE);
        if (pageParameter != null) {
            try {
                page = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
                LOGGER.warn("No page parameter");
            }
        }
        long trackId = 0;
        String trackIdParameter = request.getParameter(ParameterName.ID);
        if (trackIdParameter != null) {
            try {
                trackId = Long.parseLong(trackIdParameter);
            } catch (NumberFormatException e) {
                LOGGER.warn("No id parameter");
            }
        } else {
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        TrackInfoService trackInfoService = new TrackInfoService();
        Track track = trackInfoService.getTrackInfo(trackId);
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        if (user != null) {
            track.setBought(trackInfoService.checkUserHasTrack(user, track));
        }
        TrackCommentService trackCommentService = new TrackCommentService();
        SearchResult<Comment> feedback = trackCommentService.findCommentsForTrack(track, page);
        request.setAttribute(AttributeName.TRACK, track);
        request.setAttribute(AttributeName.FEEDBACK, feedback.getResults());
        request.setAttribute(AttributeName.NUMBER_OF_PAGES, feedback.getNumberOfPages());
        request.setAttribute(AttributeName.PAGE, page);
        return new CommandResult(Page.TRACK_INFO, CommandResult.Type.FORWARD);
    }
}

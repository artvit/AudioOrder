package by.epam.audioorder.command;

import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParamenterName;
import by.epam.audioorder.entity.Comment;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.service.SearchResult;
import by.epam.audioorder.service.TrackCommentService;
import by.epam.audioorder.service.TrackInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrackInfoCommand implements Command{
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int page = 1;
        String pageParameter = request.getParameter(ParamenterName.PAGE);
        if (pageParameter != null) {
            try {
                page = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
                LOGGER.warn("No page parameter");
            }
        }
        long trackId = 0;
        String trackIdParameter = request.getParameter(ParamenterName.ID);
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
        TrackCommentService trackCommentService = new TrackCommentService();
        SearchResult<Comment> feedback = trackCommentService.findCommentsForTrack(track, page);
        request.setAttribute(AttributeName.TRACK, track);
        request.setAttribute(AttributeName.FEEDBACK, feedback.getResults());
        request.setAttribute(AttributeName.NUMBER_OF_PAGES, feedback.getNumberOfPages());
        request.setAttribute(AttributeName.PAGE, page);
        return new CommandResult(Page.TRACK_INFO, CommandResult.Type.FORWARD);
    }
}

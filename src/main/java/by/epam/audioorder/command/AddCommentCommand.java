package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.service.TrackCommentService;
import by.epam.audioorder.service.TrackInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCommentCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        long trackId = 0;
        String trackIdParameter = request.getParameter(ConfigurationManager.getProperty("param.id"));
        try {
            trackId = Long.parseLong(trackIdParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("No id parameter");
            return new CommandResult(ConfigurationManager.getProperty("page.error"), CommandResult.Type.FORWARD);
        }
        TrackInfoService trackInfoService = new TrackInfoService();
        Track track = trackInfoService.getTrackInfo(trackId);
        String login = (String) request.getSession().getAttribute(ConfigurationManager.getProperty("attr.login"));
        String commentText = request.getParameter(ConfigurationManager.getProperty("param.comment.text"));
        TrackCommentService trackCommentService = new TrackCommentService();
        boolean addResult = trackCommentService.addComment(trackId, commentText, login);
        if (addResult) {
            String resultPage = ConfigurationManager.getProperty("url.trackinfo") + "?" +
                    ConfigurationManager.getProperty("param.id") + "=" + track.getTrackId() +
                    "&" + ConfigurationManager.getProperty("param.command") + "=" + ConfigurationManager.getProperty("command.track.info");
            return new CommandResult(resultPage, CommandResult.Type.REDIRECT);
        } else {
            return new CommandResult(ConfigurationManager.getProperty("page.error"), CommandResult.Type.FORWARD);
        }
    }
}

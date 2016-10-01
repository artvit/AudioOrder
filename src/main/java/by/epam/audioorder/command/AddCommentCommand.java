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
        long trackId;
        String trackIdParameter = request.getParameter(ConfigurationManager.getProperty("param.id"));
        try {
            trackId = Long.parseLong(trackIdParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("No id parameter");
            return new CommandResult(ConfigurationManager.getProperty("page.error"), CommandResult.Type.FORWARD);
        }
        String login = (String) request.getSession().getAttribute(ConfigurationManager.getProperty("attr.login"));
        String commentText = request.getParameter(ConfigurationManager.getProperty("param.comment.text"));
        if (commentText != null && !commentText.isEmpty()) {
            TrackCommentService trackCommentService = new TrackCommentService();
            boolean addResult = trackCommentService.addComment(trackId, commentText, login);
            if (!addResult) {
                return new CommandResult(ConfigurationManager.getProperty("page.error"), CommandResult.Type.FORWARD);
            }
        }
        String resultPage = (String) request.getSession().getAttribute(ConfigurationManager.getProperty("attr.lastpage"));
        return new CommandResult(resultPage, CommandResult.Type.REDIRECT);
    }
}

package by.epam.audioorder.command;

import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParameterName;
import by.epam.audioorder.service.TrackCommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCommentCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        long trackId;
        String trackIdParameter = request.getParameter(ParameterName.ID);
        try {
            trackId = Long.parseLong(trackIdParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("No id parameter");
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        String login = (String) request.getSession().getAttribute(AttributeName.LOGIN);
        String commentText = request.getParameter(ParameterName.COMMENT_TEXT);
        if (commentText != null && !commentText.isEmpty()) {
            TrackCommentService trackCommentService = new TrackCommentService();
            boolean addResult = trackCommentService.addComment(trackId, commentText, login);
            if (!addResult) {
                return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
            }
        }
        String resultPage = (String) request.getSession().getAttribute(AttributeName.LAST_PAGE);
        return new CommandResult(resultPage, CommandResult.Type.REDIRECT);
    }
}

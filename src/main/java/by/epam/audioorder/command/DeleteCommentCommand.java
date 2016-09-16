package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.action.IdParameterParser;
import by.epam.audioorder.service.TrackCommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCommentCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        long commentId = 0;
        String commentIdParameter = request.getParameter(ConfigurationManager.getProperty("param.id"));
        IdParameterParser parameterParser = new IdParameterParser();
        if (parameterParser.pasre(commentIdParameter)) {
            commentId = parameterParser.getResult();
        } else {
            return new CommandResult(ConfigurationManager.getProperty("page.error"), CommandResult.Type.REDIRECT);
        }
        TrackCommentService trackCommentService = new TrackCommentService();
        boolean result = trackCommentService.deleteComment(commentId);
        if (result) {
            String lastPage = (String) request.getSession().getAttribute(ConfigurationManager.getProperty("attr.lastpage"));
            return new CommandResult(lastPage, CommandResult.Type.REDIRECT);
        } else {
            return new CommandResult(ConfigurationManager.getProperty("page.error"), CommandResult.Type.REDIRECT);
        }
    }
}

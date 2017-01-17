package by.bsu.audioorder.command;

import by.bsu.audioorder.action.IdParameterParser;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.config.ParameterName;
import by.bsu.audioorder.service.TrackCommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCommentCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        long commentId;
        String commentIdParameter = request.getParameter(ParameterName.ID);
        IdParameterParser parameterParser = new IdParameterParser();
        if (parameterParser.parse(commentIdParameter)) {
            commentId = parameterParser.getResult();
        } else {
            return new CommandResult(Page.ERROR, CommandResult.Type.REDIRECT);
        }
        TrackCommentService trackCommentService = new TrackCommentService();
        boolean result = trackCommentService.deleteComment(commentId);
        if (result) {
            String lastPage = (String) request.getSession().getAttribute(AttributeName.LAST_PAGE);
            return new CommandResult(lastPage, CommandResult.Type.REDIRECT);
        } else {
            return new CommandResult(Page.ERROR, CommandResult.Type.REDIRECT);
        }
    }
}

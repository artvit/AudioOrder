package by.epam.audioorder.command;

import by.epam.audioorder.action.IdParameterParser;
import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParamenterName;
import by.epam.audioorder.service.TrackCommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCommentCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        long commentId;
        String commentIdParameter = request.getParameter(ParamenterName.ID);
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

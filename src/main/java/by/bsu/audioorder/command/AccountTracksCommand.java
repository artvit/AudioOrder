package by.bsu.audioorder.command;

import by.bsu.audioorder.service.SearchResult;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.config.ParameterName;
import by.bsu.audioorder.entity.Track;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.service.UserTracksService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountTracksCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        int page = 1;
        String pageParameter = request.getParameter(ParameterName.PAGE);
        if (pageParameter != null) {
            try {
                page = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
                LOGGER.warn("No page parameter");
            }
        }
        UserTracksService userTracksService = new UserTracksService();
        SearchResult<Track> searchResult = userTracksService.getTracksForUser(user, page);
        request.setAttribute(AttributeName.RESULTS, searchResult.getResults());
        request.setAttribute(AttributeName.PAGE, page);
        request.setAttribute(AttributeName.NUMBER_OF_PAGES, searchResult.getNumberOfPages());
        request.setAttribute(AttributeName.SECTION, AttributeName.SECTION_TRACKS);
        return new CommandResult(Page.ACCOUNT, CommandResult.Type.FORWARD);
    }
}

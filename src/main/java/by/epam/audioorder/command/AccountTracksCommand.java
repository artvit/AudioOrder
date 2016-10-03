package by.epam.audioorder.command;

import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParamenterName;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.service.SearchResult;
import by.epam.audioorder.service.UserSearchService;
import by.epam.audioorder.service.UserTracksService;
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
        String pageParameter = request.getParameter(ParamenterName.PAGE);
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

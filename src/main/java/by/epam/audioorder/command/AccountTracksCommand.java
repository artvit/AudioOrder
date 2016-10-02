package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
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
        User user = (User) request.getSession().getAttribute(ConfigurationManager.getProperty("attr.user"));
        int page = 1;
        String pageParameter = request.getParameter(ConfigurationManager.getProperty("param.page"));
        if (pageParameter != null) {
            try {
                page = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
                LOGGER.warn("No page parameter");
            }
        }
        UserTracksService userTracksService = new UserTracksService();
        SearchResult<Track> searchResult = userTracksService.getTracksForUser(user, page);
        request.setAttribute(ConfigurationManager.getProperty("attr.results"), searchResult.getResults());
        request.setAttribute(ConfigurationManager.getProperty("attr.page"), page);
        request.setAttribute(ConfigurationManager.getProperty("attr.numofpages"), searchResult.getNumberOfPages());
        request.setAttribute(ConfigurationManager.getProperty("attr.account.section"), ConfigurationManager.getProperty("attr.account.section.tracks"));
        return new CommandResult(ConfigurationManager.getProperty("page.account"), CommandResult.Type.FORWARD);
    }
}

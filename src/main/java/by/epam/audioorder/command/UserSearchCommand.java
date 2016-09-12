package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.service.SearchResult;
import by.epam.audioorder.service.UserSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserSearchCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String searchQuery = request.getParameter(ConfigurationManager.getProperty("param.search"));
        int page = 1;
        String pageParameter = request.getParameter(ConfigurationManager.getProperty("param.page"));
        if (pageParameter != null) {
            try {
                page = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
                LOGGER.warn("No page parameter");
            }
        }
        UserSearchService service = new UserSearchService();
        SearchResult<User> result = service.searchUser(searchQuery, page);
        request.setAttribute(ConfigurationManager.getProperty("attr.results"), result.getResults());
        request.setAttribute(ConfigurationManager.getProperty("attr.page"), page);
        request.setAttribute(ConfigurationManager.getProperty("attr.numofpages"), result.getNumberOfPages());
        request.setAttribute(ConfigurationManager.getProperty("attr.search"), searchQuery);
        return new CommandResult(ConfigurationManager.getProperty("page.users"), CommandResult.Type.FORWARD);
    }
}

package by.epam.audioorder.command;

import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParamenterName;
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
        String searchQuery = request.getParameter(ParamenterName.SEARCH);
        int page = 1;
        String pageParameter = request.getParameter(ParamenterName.PAGE);
        if (pageParameter != null) {
            try {
                page = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
                LOGGER.warn("No page parameter");
            }
        }
        UserSearchService service = new UserSearchService();
        SearchResult<User> result = service.searchUser(searchQuery, page);
        request.setAttribute(AttributeName.RESULTS, result.getResults());
        request.setAttribute(AttributeName.PAGE, page);
        request.setAttribute(AttributeName.NUMBER_OF_PAGES, result.getNumberOfPages());
        request.setAttribute(AttributeName.SEARCH, searchQuery);
        return new CommandResult(Page.USERS, CommandResult.Type.FORWARD);
    }
}

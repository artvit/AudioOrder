package by.bsu.audioorder.command;

import by.bsu.audioorder.service.SearchResult;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.config.ParameterName;
import by.bsu.audioorder.entity.Genre;
import by.bsu.audioorder.entity.Track;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.service.TrackSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrackSearchCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String searchQuery = request.getParameter(ParameterName.SEARCH);
        Genre genre;
        String genreValue = request.getParameter(ParameterName.GENRE);
        if (genreValue == null) {
            genre = Genre.ANY;
        } else {
            genre = Genre.valueOf(genreValue.toUpperCase());
        }
        int page = 1;
        String pageParameter = request.getParameter(ParameterName.PAGE);
        if (pageParameter != null) {
            try {
                page = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
                LOGGER.warn("No page parameter");
            }
        }
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        TrackSearchService service = new TrackSearchService();
        SearchResult<Track> result = service.searchTrack(searchQuery, genre, page, user);
        request.setAttribute(AttributeName.RESULTS, result.getResults());
        request.setAttribute(AttributeName.PAGE, page);
        request.setAttribute(AttributeName.NUMBER_OF_PAGES, result.getNumberOfPages());
        request.setAttribute(AttributeName.SEARCH, searchQuery);
        request.setAttribute(AttributeName.GENRE, genre);
        return new CommandResult(Page.TRACKS, CommandResult.Type.FORWARD);
    }
}

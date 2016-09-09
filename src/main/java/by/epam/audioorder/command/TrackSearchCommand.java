package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.entity.Genre;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.service.SearchResult;
import by.epam.audioorder.service.TrackSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrackSearchCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String searchQuery = request.getParameter(ConfigurationManager.getProperty("param.search"));
        Genre genre;
        String genreValue = request.getParameter(ConfigurationManager.getProperty("param.genre"));
        if (genreValue == null) {
            genre = Genre.ANY;
        } else {
            genre = Genre.valueOf(genreValue.toUpperCase());
        }
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter(ConfigurationManager.getProperty("param.page")));
        } catch (NumberFormatException e) {
            LOGGER.warn("No page parameter");
        }
        TrackSearchService service = new TrackSearchService();
        SearchResult<Track> result = service.searchTrack(searchQuery, genre, page);
        request.setAttribute(ConfigurationManager.getProperty("attr.results"), result.getResults());
        request.setAttribute(ConfigurationManager.getProperty("attr.page"), page);
        request.setAttribute(ConfigurationManager.getProperty("attr.numofpages"), result.getNumberOfPages());
        request.setAttribute(ConfigurationManager.getProperty("attr.search"), searchQuery);
        return new CommandResult(ConfigurationManager.getProperty("page.tracks"), CommandResult.Type.FORWARD);
    }
}

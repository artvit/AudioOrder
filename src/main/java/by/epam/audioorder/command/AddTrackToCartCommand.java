package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.action.IdParameterParser;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.service.TrackInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class AddTrackToCartCommand implements Command{
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Set<Track> cart = (Set<Track>) session.getAttribute(ConfigurationManager.getProperty("attr.cart"));
        if (cart == null) {
            cart = new HashSet<>();
            session.setAttribute(ConfigurationManager.getProperty("attr.cart"), cart);
        }
        long trackId = 0;
        String trackIdParameter = request.getParameter(ConfigurationManager.getProperty("param.id"));
        IdParameterParser parameterParser = new IdParameterParser();
        if (parameterParser.parse(trackIdParameter)) {
            trackId = parameterParser.getResult();
        } else {
            return new CommandResult(ConfigurationManager.getProperty("page.error"), CommandResult.Type.REDIRECT);
        }
        TrackInfoService trackInfoService = new TrackInfoService();
        Track track = trackInfoService.getTrackInfo(trackId);
        cart.add(track);
        String lastPage = (String) session.getAttribute(ConfigurationManager.getProperty("attr.lastpage"));
        return new CommandResult(lastPage, CommandResult.Type.REDIRECT);
    }
}

package by.epam.audioorder.command;

import by.epam.audioorder.action.IdParameterParser;
import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParameterName;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.service.TrackInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class DeleteFromCartCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Set<Track> cart = (Set<Track>) session.getAttribute(AttributeName.CART);
        long trackId = 0;
        String trackIdParameter = request.getParameter(ParameterName.ID);
        IdParameterParser parameterParser = new IdParameterParser();
        if (parameterParser.parse(trackIdParameter)) {
            trackId = parameterParser.getResult();
        } else {
            return new CommandResult(Page.ERROR, CommandResult.Type.REDIRECT);
        }
        TrackInfoService trackInfoService = new TrackInfoService();
        Track track = trackInfoService.getTrackInfo(trackId);
        if (track != null) {
            cart.remove(track);
            String lastPage = (String) session.getAttribute(AttributeName.LAST_PAGE);
            return new CommandResult(lastPage, CommandResult.Type.REDIRECT);
        } else {
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
    }
}

package by.bsu.audioorder.command;

import by.bsu.audioorder.action.IdParameterParser;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.config.ParameterName;
import by.bsu.audioorder.entity.Track;
import by.bsu.audioorder.service.TrackInfoService;
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

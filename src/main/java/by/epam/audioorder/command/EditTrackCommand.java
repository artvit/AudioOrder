package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.action.IdParameterParser;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.service.TrackInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditTrackCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String idParameter = request.getParameter(ConfigurationManager.getProperty("param.id"));
        IdParameterParser idParser = new IdParameterParser();
        if (!idParser.parse(idParameter)) {
            return new CommandResult(ConfigurationManager.getProperty("page.error"), CommandResult.Type.FORWARD);
        }
        long id = idParser.getResult();
        TrackInfoService trackInfoService = new TrackInfoService();
        Track track = trackInfoService.getTrackInfo(id);
        request.setAttribute(ConfigurationManager.getProperty("attr.track"), track);
        return new CommandResult(ConfigurationManager.getProperty("page.track.edit"), CommandResult.Type.FORWARD);
    }
}

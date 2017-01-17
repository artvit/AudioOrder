package by.bsu.audioorder.command;

import by.bsu.audioorder.action.IdParameterParser;
import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.service.TrackInfoService;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.config.ParameterName;
import by.bsu.audioorder.entity.Track;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditTrackCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String idParameter = request.getParameter(ParameterName.ID);
        IdParameterParser idParser = new IdParameterParser();
        if (!idParser.parse(idParameter)) {
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        long id = idParser.getResult();
        TrackInfoService trackInfoService = new TrackInfoService();
        Track track = trackInfoService.getTrackInfo(id);
        request.setAttribute(AttributeName.TRACK, track);
        return new CommandResult(Page.TRACK_EDIT, CommandResult.Type.FORWARD);
    }
}

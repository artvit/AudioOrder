package by.epam.audioorder.command;

import by.epam.audioorder.action.IdParameterParser;
import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParamenterName;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.service.TrackInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditTrackCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String idParameter = request.getParameter(ParamenterName.ID);
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

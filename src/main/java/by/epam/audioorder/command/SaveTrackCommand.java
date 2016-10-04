package by.epam.audioorder.command;

import by.epam.audioorder.action.IdParameterParser;
import by.epam.audioorder.action.InternationalizationManager;
import by.epam.audioorder.config.*;
import by.epam.audioorder.entity.Genre;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.exception.ServiceException;
import by.epam.audioorder.service.AudioFileService;
import by.epam.audioorder.service.SaveTrackService;
import by.epam.audioorder.service.TrackInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Locale;

public class SaveTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
        String idParameter = request.getParameter(ParamenterName.ID);
        IdParameterParser idParser = new IdParameterParser();
        if (!idParser.parse(idParameter)) {
            LOGGER.error("No id parameter");
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.param.id", locale));
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        long id = idParser.getResult();
        TrackInfoService trackInfoService = new TrackInfoService();
        Track track = trackInfoService.getTrackInfo(id);
        String artist = request.getParameter(ParamenterName.ARTIST);
        String title = request.getParameter(ParamenterName.TITLE);
        String yearParameter = request.getParameter(ParamenterName.YEAR);
        int year = 0;
        try {
            year = Integer.parseInt(yearParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        int duration = 0;
        String secondsParameter = request.getParameter(ParamenterName.SECONDS);
        String minutesParameter = request.getParameter(ParamenterName.MINUTES);
        try {
            duration = Integer.parseInt(secondsParameter);
            duration = 60 * Integer.parseInt(minutesParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        String costParameter = request.getParameter(ParamenterName.COST);
        double cost = 0;
        try {
            cost = Double.parseDouble(costParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        Genre genre = Genre.ANY;
        String genreParameter = request.getParameter(ParamenterName.GENRE);
        if (genreParameter != null && !genreParameter.isEmpty()) {
            genre = Genre.valueOf(genreParameter.toUpperCase());
        }
        String fileLink = null;
        try {
            Part filePart = request.getPart(ParamenterName.FILE);
            if (filePart != null) {
                InputStream fileContent = filePart.getInputStream();
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                AudioFileService audioFileService = new AudioFileService();
                if (!audioFileService.deleteTrack(track.getPath())) {
                    LOGGER.error("Cannot delete file: " + track.getPath());
                }
                fileLink = audioFileService.saveFile(fileName, fileContent);
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Cannot upload file", e);
        } catch (ServiceException e) {
            LOGGER.error("Cannot store file", e);
        }
        SaveTrackService saveTrackService = new SaveTrackService();
        boolean result = saveTrackService.saveTrack(track, artist, title, year, genre, duration, cost, fileLink);
        if (result) {
            String resultURL = ServletMappingValue.URL_TRACKS + "?" +
                    ParamenterName.COMMAND + "=" + CommandParameter.TRACK_INFO + "&" +
                    ParamenterName.ID + "=" + track.getTrackId();
            return new CommandResult(resultURL, CommandResult.Type.REDIRECT);
        } else {
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
    }
}

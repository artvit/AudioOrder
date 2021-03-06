package by.bsu.audioorder.command;

import by.bsu.audioorder.action.IdParameterParser;
import by.bsu.audioorder.action.InternationalizationManager;
import by.bsu.audioorder.config.*;
import by.bsu.audioorder.entity.Genre;
import by.bsu.audioorder.service.TrackInfoService;
import by.bsu.audioorder.entity.Track;
import by.bsu.audioorder.exception.ServiceException;
import by.bsu.audioorder.service.AudioFileService;
import by.bsu.audioorder.service.SaveTrackService;
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
        String idParameter = request.getParameter(ParameterName.ID);
        IdParameterParser idParser = new IdParameterParser();
        if (!idParser.parse(idParameter)) {
            LOGGER.error("No id parameter");
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.param.id", locale));
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        long id = idParser.getResult();
        TrackInfoService trackInfoService = new TrackInfoService();
        Track track = trackInfoService.getTrackInfo(id);
        String artist = request.getParameter(ParameterName.ARTIST);
        String title = request.getParameter(ParameterName.TITLE);
        String yearParameter = request.getParameter(ParameterName.YEAR);
        int year = 0;
        try {
            year = Integer.parseInt(yearParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        int duration = 0;
        String secondsParameter = request.getParameter(ParameterName.SECONDS);
        String minutesParameter = request.getParameter(ParameterName.MINUTES);
        try {
            duration = Integer.parseInt(secondsParameter);
            duration += 60 * Integer.parseInt(minutesParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        String costParameter = request.getParameter(ParameterName.COST);
        double cost = 0;
        try {
            cost = Double.parseDouble(costParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        Genre genre = Genre.ANY;
        String genreParameter = request.getParameter(ParameterName.GENRE);
        if (genreParameter != null && !genreParameter.isEmpty()) {
            genre = Genre.valueOf(genreParameter.toUpperCase());
        }
        String fileLink = null;
        try {
            Part filePart = request.getPart(ParameterName.FILE);
            if (filePart != null) {
                InputStream fileContent = filePart.getInputStream();
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                AudioFileService audioFileService = new AudioFileService();
                if (!audioFileService.deleteFile(track.getPath())) {
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
                    ParameterName.COMMAND + "=" + CommandParameter.TRACK_INFO + "&" +
                    ParameterName.ID + "=" + track.getTrackId();
            return new CommandResult(resultURL, CommandResult.Type.REDIRECT);
        } else {
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
    }
}

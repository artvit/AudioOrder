package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.action.IdParameterParser;
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

public class SaveTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

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
        String artist = request.getParameter(ConfigurationManager.getProperty("param.artist"));
        String title = request.getParameter(ConfigurationManager.getProperty("param.title"));
        String yearParameter = request.getParameter(ConfigurationManager.getProperty("param.year"));
        int year = 0;
        try {
            year = Integer.parseInt(yearParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        int duration = 0;
        String secondsParameter = request.getParameter(ConfigurationManager.getProperty("param.seconds"));
        String minutesParameter = request.getParameter(ConfigurationManager.getProperty("param.minutes"));
        try {
            duration = Integer.parseInt(secondsParameter);
            duration = 60 * Integer.parseInt(minutesParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        String costParameter = request.getParameter(ConfigurationManager.getProperty("param.cost"));
        double cost = 0;
        try {
            cost = Double.parseDouble(costParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        Genre genre = Genre.ANY;
        String genreParameter = request.getParameter(ConfigurationManager.getProperty("param.genre"));
        if (genreParameter != null && !genreParameter.isEmpty()) {
            genre = Genre.valueOf(genreParameter.toUpperCase());
        }
        String fileLink = null;
        try {
            Part filePart = request.getPart(ConfigurationManager.getProperty("param.file"));
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
        saveTrackService.saveTrack(track, artist, title, year, genre, duration, cost, fileLink);
        String resultURL = ConfigurationManager.getProperty("url.trackinfo") + "?" +
                ConfigurationManager.getProperty("param.command") + "=" + ConfigurationManager.getProperty("command.track.info") + "&" +
                ConfigurationManager.getProperty("param.id") + "=" + track.getTrackId();
        return new CommandResult(resultURL, CommandResult.Type.REDIRECT);
    }
}

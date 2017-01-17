package by.bsu.audioorder.command;

import by.bsu.audioorder.action.InternationalizationManager;
import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.config.ParameterName;
import by.bsu.audioorder.entity.Genre;
import by.bsu.audioorder.service.AudioFileService;
import by.bsu.audioorder.service.SaveTrackService;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.exception.ServiceException;
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

public class AddTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
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
            duration = 60 * Integer.parseInt(minutesParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        String costParameter = request.getParameter(ParameterName.COST);
        double cost = 0;
        boolean costError = false;
        if (costParameter != null) {
            try {
                cost = Double.parseDouble(costParameter);
                if (cost < 0) {
                    costError = true;
                }
            } catch (NumberFormatException e) {
                costError = true;
            }
        } else {
            costError = true;
        }
        if (costError) {
            LOGGER.error("Wrong cost parameter");
            Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.param.cost", locale));
            return new CommandResult(Page.TRACK_ADD, CommandResult.Type.FORWARD);
        }
        Genre genre = Genre.ANY;
        String genreParameter = request.getParameter(ParameterName.GENRE);
        if (genreParameter != null && !genreParameter.isEmpty()) {
            genre = Genre.valueOf(genreParameter.toUpperCase());
        }
        try {
            Part filePart = request.getPart(ParameterName.FILE);
            if (filePart != null) {
                InputStream fileContent = filePart.getInputStream();
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                AudioFileService audioFileService = new AudioFileService();
                String fileLink = audioFileService.saveFile(fileName, fileContent);
                SaveTrackService saveTrackService = new SaveTrackService();
                boolean result = saveTrackService.addTrack(artist, title, year, genre, duration, cost, fileLink);
                if (result) {
                    return new CommandResult(Page.TRACK_ADD_SUCCESS, CommandResult.Type.FORWARD);
                } else {
                    return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
                }
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Cannot upload file", e);
        } catch (ServiceException e) {
            LOGGER.error("Cannot store file", e);
        }
        request.setAttribute(ParameterName.ARTIST, artist);
        request.setAttribute(ParameterName.TITLE, title);
        request.setAttribute(ParameterName.YEAR, yearParameter);
        request.setAttribute(ParameterName.COST, costParameter);
        request.setAttribute(ParameterName.GENRE, genreParameter);
        request.setAttribute(ParameterName.MINUTES, minutesParameter);
        request.setAttribute(ParameterName.SECONDS, secondsParameter);
        Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
        String message = InternationalizationManager.getProperty("track.add.error.text", locale);
        request.setAttribute(AttributeName.MESSAGE, message);
        return new CommandResult(Page.TRACK_ADD, CommandResult.Type.FORWARD);
    }
}

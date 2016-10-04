package by.epam.audioorder.command;

import by.epam.audioorder.action.InternationalizationManager;
import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParamenterName;
import by.epam.audioorder.entity.Genre;
import by.epam.audioorder.exception.ServiceException;
import by.epam.audioorder.service.SaveTrackService;
import by.epam.audioorder.service.AudioFileService;
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
        boolean costError = false;
        try {
            cost = Double.parseDouble(costParameter);
            if (cost < 0) {
                costError = true;
            }
        } catch (NumberFormatException e) {
            costError = true;
        }
        if (costError) {
            LOGGER.error("Wrong cost parameter");
            Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.param.cost", locale));
            return new CommandResult(Page.TRACK_ADD, CommandResult.Type.FORWARD);
        }
        Genre genre = Genre.ANY;
        String genreParameter = request.getParameter(ParamenterName.GENRE);
        if (genreParameter != null && !genreParameter.isEmpty()) {
            genre = Genre.valueOf(genreParameter.toUpperCase());
        }
        try {
            Part filePart = request.getPart(ParamenterName.FILE);
            if (filePart != null) {
                InputStream fileContent = filePart.getInputStream();
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                AudioFileService audioFileService = new AudioFileService();
                String fileLink = audioFileService.saveFile(fileName, fileContent);
                SaveTrackService saveTrackService = new SaveTrackService();
                saveTrackService.addTrack(artist, title, year, genre, duration, cost, fileLink);
                return new CommandResult(Page.TRACK_ADD_SUCCESS, CommandResult.Type.FORWARD);
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Cannot upload file", e);
        } catch (ServiceException e) {
            LOGGER.error("Cannot store file", e);
        }
        request.setAttribute(ParamenterName.ARTIST, artist);
        request.setAttribute(ParamenterName.TITLE, title);
        request.setAttribute(ParamenterName.YEAR, yearParameter);
        request.setAttribute(ParamenterName.COST, costParameter);
        request.setAttribute(ParamenterName.GENRE, genreParameter);
        request.setAttribute(ParamenterName.MINUTES, minutesParameter);
        request.setAttribute(ParamenterName.SECONDS, secondsParameter);
        Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
        String message = InternationalizationManager.getProperty("track.add.error.text", locale);
        request.setAttribute(AttributeName.MESSAGE, message);
        return new CommandResult(Page.TRACK_ADD, CommandResult.Type.FORWARD);
    }
}

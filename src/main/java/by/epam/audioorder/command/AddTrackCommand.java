package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.action.InternationalizationManager;
import by.epam.audioorder.entity.Genre;
import by.epam.audioorder.exception.ServiceException;
import by.epam.audioorder.service.AddTrackService;
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
        try {
            Part filePart = request.getPart(ConfigurationManager.getProperty("param.file"));
            InputStream fileContent = filePart.getInputStream();
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            AudioFileService audioFileService = new AudioFileService();
            String fileLink = audioFileService.saveFile(fileName, fileContent);
            AddTrackService addTrackService = new AddTrackService();
            addTrackService.addTrack(artist, title, year, genre, duration, cost, fileLink);
            return new CommandResult(ConfigurationManager.getProperty("page.track.add.success"), CommandResult.Type.FORWARD);
        } catch (IOException | ServletException e) {
            LOGGER.error("Cannot upload file", e);
        } catch (ServiceException e) {
            LOGGER.error("Cannot store file", e);
        }
        request.setAttribute(ConfigurationManager.getProperty("param.artist"), artist);
        request.setAttribute(ConfigurationManager.getProperty("param.artist"), title);
        request.setAttribute(ConfigurationManager.getProperty("param.year"), yearParameter);
        request.setAttribute(ConfigurationManager.getProperty("param.cost"), costParameter);
        request.setAttribute(ConfigurationManager.getProperty("param.genre"), genreParameter);
        request.setAttribute(ConfigurationManager.getProperty("param.minutes"), minutesParameter);
        request.setAttribute(ConfigurationManager.getProperty("param.seconds"), secondsParameter);
        Locale locale = (Locale) request.getSession().getAttribute(ConfigurationManager.getProperty("attr.locale"));
        String message = InternationalizationManager.getProperty("track.add.error.text", locale);
        request.setAttribute(ConfigurationManager.getProperty("attr.message"), message);
        return new CommandResult(ConfigurationManager.getProperty("page.track.add"), CommandResult.Type.FORWARD);
    }
}

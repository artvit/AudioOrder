package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.entity.Genre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        Genre genre = Genre.ANY;
        String genreParameter = request.getParameter(ConfigurationManager.getProperty("param.genre"));
        if (genreParameter != null && !genreParameter.isEmpty()) {
            genre = Genre.valueOf(genreParameter.toUpperCase());
        }
        try {
            Part filePart = request.getPart(ConfigurationManager.getProperty("param.file"));
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            Files.copy(fileContent, Paths.get(ConfigurationManager.getProperty("path.storage") + fileName));

            return new CommandResult(ConfigurationManager.getProperty("page.track.add.success"), CommandResult.Type.FORWARD);
        } catch (IOException | ServletException e) {
            LOGGER.error("Cannot upload file", e);
        }
        return new CommandResult(ConfigurationManager.getProperty("page.track.add"), CommandResult.Type.FORWARD);
    }
}

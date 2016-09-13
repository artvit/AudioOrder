package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

public class AddTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String artist = request.getParameter(ConfigurationManager.getProperty("param.artist"));

        try {
            Part filePart = request.getPart(ConfigurationManager.getProperty("param.file"));
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();

        } catch (IOException | ServletException e) {
            LOGGER.error("Cannot upload file", e);
        }

        return null;
    }
}

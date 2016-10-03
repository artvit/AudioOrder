package by.epam.audioorder.command;

import by.epam.audioorder.action.IdParameterParser;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParamenterName;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.service.TrackInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
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
        Path filePath = Paths.get(track.getPath());
        try {
            InputStream inStream = Files.newInputStream(filePath);
            ServletContext context = request.getServletContext();
            String mimeType = context.getMimeType(track.getPath());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setContentLength((int) Files.size(filePath));
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", filePath.getFileName().toString());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
        } catch (IOException e) {
            LOGGER.error("Cannot send file", e);
        }
        return null;
    }
}

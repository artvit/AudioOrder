package by.bsu.audioorder.command;

import by.bsu.audioorder.action.InternationalizationManager;
import by.bsu.audioorder.action.IdParameterParser;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.config.ParameterName;
import by.bsu.audioorder.entity.Track;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.service.AudioFileService;
import by.bsu.audioorder.service.TrackInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Locale;

public class DownloadCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String idParameter = request.getParameter(ParameterName.ID);
        IdParameterParser idParser = new IdParameterParser();
        if (!idParser.parse(idParameter)) {
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        long id = idParser.getResult();
        TrackInfoService trackInfoService = new TrackInfoService();
        Track track = trackInfoService.getTrackInfo(id);
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
        if (!trackInfoService.checkUserHasTrack(user, track)) {
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.access", locale));
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        String name = track.getPath();
        try {
            AudioFileService audioFileService = new AudioFileService();
            InputStream inStream = audioFileService.downloadFile(name);
            if (inStream == null) {
                LOGGER.error("Cannot get file from dropbox");
                return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
            }
            ServletContext context = request.getServletContext();
            String mimeType = context.getMimeType(track.getPath());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            String headerKey = "Content-Disposition";
            String fileName = (track.getArtist().getName() + " - " + track.getTitle()).replaceAll("[^\\p{L}\\d.-]+", "_");
            String extension = track.getPath().substring(track.getPath().lastIndexOf("."));
            fileName += extension;
            fileName = URLEncoder.encode(fileName, "UTF-8");
            String headerValue = String.format("attachment; filename=\"%s\"", fileName);
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
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        return null;
    }
}

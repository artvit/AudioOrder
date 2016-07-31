package by.epam.audioorder.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class ParseUploadedFileCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();

    private static final String SAVE_DIR = "handlingFiles";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String savePath = request.getServletContext().getRealPath("") + SAVE_DIR;

        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            if (!fileSaveDir.mkdir()) {
                LOGGER.debug("cannot create " + SAVE_DIR + " dir");
//                throw new RuntimeException("Cannot create directory");
            }
        }

        Set<String> filePaths = new HashSet<>();
        try {
            for (Part part : request.getParts()) {
                String fileName = savePath + File.separator + extractFileName(part);
                part.write(fileName);
                filePaths.add(fileName);
                LOGGER.info("added file: " + fileName);
            }
        } catch (IOException e) {
            LOGGER.error("Cannot get files");
        } catch (ServletException e) {
            e.printStackTrace();
        }

        //TODO handle file

        for (String path : filePaths) {
            try {
                Files.delete(Paths.get(path));
            } catch (IOException e) {
                LOGGER.error("Cannot delete file: " + path);
            }
        }

        request.setAttribute("message", "Upload has been done successfully!");
        return "/pages/success.jsp";
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}

package by.epam.audioorder.service;

import by.epam.audioorder.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class AudioFileService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SAVE_FOLDER = "c:/Users/artvi/IdeaProjects/storage/";

    public String saveFile(String fileName, InputStream inputStream) throws ServiceException {
        try {
            String filePath = SAVE_FOLDER + fileName;
            String extension = "";
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i);
            }
            Files.copy(inputStream, Paths.get(filePath));
            String newFileName = UUID.randomUUID().toString() + extension;
            String newFilePath = SAVE_FOLDER + newFileName;
            Files.move(Paths.get(filePath), Paths.get(newFilePath));
            LOGGER.info("File " + newFilePath + "saved");
            return newFilePath;
        } catch (IOException e) {
            throw new ServiceException("Cannot store file");
        }
    }

    public boolean deleteTrack(String trackPath) {
        try {
            Files.delete(Paths.get(trackPath));
        } catch (IOException e) {
            LOGGER.error("Cannot delete file " + trackPath, e);
            return false;
        }
        LOGGER.info("Delete file " + trackPath +" successfully");
        return true;
    }
}

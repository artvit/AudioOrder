package by.epam.audioorder.service;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class AudioFileService {
    private static final Logger LOGGER = LogManager.getLogger();

    public String saveFile(String fileName, InputStream inputStream) throws ServiceException {
        try {
            String filePath = ConfigurationManager.getProperty("path.storage") + fileName;
            Files.copy(inputStream, Paths.get(filePath));
            String newFileName = UUID.randomUUID().toString();
            String newFilePath = ConfigurationManager.getProperty("path.storage") + newFileName;
            Files.move(Paths.get(filePath), Paths.get(newFilePath));
            return filePath;
        } catch (IOException e) {
            throw new ServiceException("Cannot store file");
        }
    }

    public boolean deleteTrack(String trackPath) {
        try {
            Files.delete(Paths.get(trackPath));
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}

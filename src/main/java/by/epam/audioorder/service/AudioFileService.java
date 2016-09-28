package by.epam.audioorder.service;

import by.epam.audioorder.action.ConfigurationManager;
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

public class AudioFileService {
    private static final Logger LOGGER = LogManager.getLogger();

    public String saveFile(String fileName, InputStream inputStream) throws ServiceException {
        try {
            String filePath = ConfigurationManager.getProperty("path.storage") + fileName;
            Files.copy(inputStream, Paths.get(filePath));
            return filePath;
        } catch (IOException e) {
            throw new ServiceException("Cannot store file");
        }
    }

    public int getDuration(String fileName) throws ServiceException {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Files.newInputStream(Paths.get(fileName)));
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            int durationInSeconds = (int) (frames / format.getFrameRate());
            return durationInSeconds;
        } catch (UnsupportedAudioFileException e) {
            throw new ServiceException("Unsupported file", e);
        } catch (IOException e) {
            throw new ServiceException("Cannot get audio track duration", e);
        }
    }
}

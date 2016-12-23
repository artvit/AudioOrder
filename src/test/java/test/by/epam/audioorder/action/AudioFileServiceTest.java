package test.by.epam.audioorder.action;

import by.epam.audioorder.exception.ServiceException;
import by.epam.audioorder.service.AudioFileService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class AudioFileServiceTest {
    @Test
    public void saveFileTest() throws IOException, ServiceException {
        List<String> filenames = Arrays.asList(
                "6e053af4-f98f-4f1b-a795-01cc0ecc001f.mp3",
                "3af6b10a-a1a2-45a6-9745-c52cb83ba9e0.mp3",
                "c7a0fd2f-8dc3-4b11-8fcc-2e9d063edc3b.mp3",
                "738e0dbd-f2d1-4a12-9d84-6bd7b78bc1e4.mp3",
                "4e022686-8bd0-417c-b919-a23744469861.mp3",
                "7524ff20-1540-48dd-948c-2fba49c064a2.mp3",
                "441bfbae-8fb0-42d0-b110-c9c1581a6eed.mp3",
                "7f0d32a2-ca04-4369-b72c-6d77e8e22946.mp3");
        for (String filename: filenames) {
            AudioFileService audioFileService = new AudioFileService();
            InputStream inputStream = Files.newInputStream(Paths.get("c:\\Users\\artvi\\IdeaProjects\\storage\\" + filename));
            audioFileService.saveFile(filename, inputStream);
        }
    }

    @Test
    public void downloadFileTest() {
        String filename =  "7524ff20-1540-48dd-948c-2fba49c064a2.mp3";
        AudioFileService audioFileService = new AudioFileService();
        InputStream inputStream = audioFileService.downloadFile(filename);
        Assert.assertNotNull(inputStream);
    }
}

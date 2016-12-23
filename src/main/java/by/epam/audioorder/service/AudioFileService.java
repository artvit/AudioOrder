package by.epam.audioorder.service;

import by.epam.audioorder.exception.ServiceException;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class AudioFileService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String ACCESS_TOKEN = "06D96Ewopg8AAAAAAABfykaEtBHN6Y7z1aLhgEISiKsvua6Pl7DPXTwQZQBolJm2";

    public String saveFile(String fileName, InputStream inputStream) throws ServiceException {
        try {
            String extension = "";
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i);
            }
            fileName = UUID.randomUUID().toString() + extension;
            DbxRequestConfig config = new DbxRequestConfig("AudioOrder/1.0");
            DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
            FileMetadata metadata = client.files().uploadBuilder("/" + fileName).uploadAndFinish(inputStream);
            LOGGER.info("File " + fileName + " saved");
            return fileName;
        } catch (IOException e) {
            throw new ServiceException("Cannot store file");
        } catch (UploadErrorException e) {
            throw new ServiceException("Cannot upload file");
        } catch (DbxException e) {
            throw new ServiceException("Dropbox exception");
        }
    }

    public boolean deleteFile(String trackPath) {
        DbxRequestConfig config = new DbxRequestConfig("AudioOrder/1.0");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        try {
            Metadata fileMetadata = client.files().getMetadata(trackPath);
            if (fileMetadata != null) {
                client.files().delete(trackPath);
                LOGGER.info("Delete file " + trackPath +" successfully");
                return true;
            }
        } catch (DbxException e) {
            LOGGER.error("Dropbox error");
        }
        return false;
    }

    public InputStream downloadFile(String file) {
        DbxRequestConfig config = new DbxRequestConfig("AudioOrder/1.0");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        try {
            DbxDownloader<FileMetadata> downloader = client.files().download("/" + file);
            return downloader.getInputStream();
        } catch (DbxException | IllegalArgumentException e) {
            LOGGER.error("Cannot get file", e);
            return null;
        }
    }

}

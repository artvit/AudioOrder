package by.epam.audioorder.action;

import by.epam.audioorder.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String HASH_ALGORITHM = "SHA-256";

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Hash algorithm does not found", e);
            return password;
        }
    }

    public static boolean validateUser(User user, String password) {
        if (password == null || user.getPasswordHash() == null) {
            return false;
        }
        return user.getPasswordHash().equals(hashPassword(password));
    }
}

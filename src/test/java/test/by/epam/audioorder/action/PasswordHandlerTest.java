package test.by.epam.audioorder.action;

import by.epam.audioorder.action.PasswordHandler;
import org.junit.Test;

public class PasswordHandlerTest {

    @Test
    public void hashPasswordTest() {
        String hash = PasswordHandler.hashPassword("hello");
        System.out.println(hash);
    }
}

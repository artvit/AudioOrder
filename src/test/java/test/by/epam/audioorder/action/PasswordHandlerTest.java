package test.by.epam.audioorder.action;

import by.epam.audioorder.action.PasswordHandler;
import org.junit.Assert;
import org.junit.Test;

public class PasswordHandlerTest {

    @Test
    public void hashPasswordTest() {
        String hash = PasswordHandler.hashPassword("hello");
        Assert.assertTrue(hash.equals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"));
    }
}

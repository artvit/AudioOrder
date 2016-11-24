package test.by.epam.audioorder.action;

import by.epam.audioorder.action.InternationalizationManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class InternationalizationManagerTest {
    @Test
    public void getPropertyTest() {
        String result = InternationalizationManager.getProperty("menu.tracks");
        Assert.assertTrue("Tracks".equals(result));
        result = InternationalizationManager.getProperty("menu.tracks", new Locale("en"));
        Assert.assertTrue("Tracks".equals(result));
    }
}

package test.by.epam.audioorder.action;

import by.bsu.audioorder.action.IdParameterParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IdParameterParserTest {
    private IdParameterParser parser;

    @Before
    public void init() {
        parser = new IdParameterParser();
    }

    @Test
    public void parseCorrectValueTest() {
        parser.parse("12367816273681");
        long result = parser.getResult();
        Assert.assertTrue(result == 12367816273681L);
    }

    @Test
    public void parseIncorrectValueTest() {
        Assert.assertFalse(parser.parse("123asda3"));

    }

    @Test
    public void parseEmptyValueTest() {
        Assert.assertFalse(parser.parse(""));
    }

    @Test
    public void parseNullValueTest() {
        Assert.assertFalse(parser.parse(null));
    }
}

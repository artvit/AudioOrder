package by.epam.audioorder.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class TrackDurationTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger();

    private int value;

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.println(String.format("%d:%02d", value / 60, value % 60));
        } catch (IOException e) {
            LOGGER.error("Cannot print pagination bar", e);
        }
        return SKIP_BODY;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

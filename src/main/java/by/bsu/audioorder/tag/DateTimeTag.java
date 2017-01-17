package by.bsu.audioorder.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger();

    private LocalDateTime value;

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.mm.yyyy");
            out.println(value.format(formatter));
        } catch (IOException e) {
            LOGGER.error("Cannot print pagination bar", e);
        }
        return SKIP_BODY;
    }

    public LocalDateTime getValue() {
        return value;
    }

    public void setValue(LocalDateTime value) {
        this.value = value;
    }
}

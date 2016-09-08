package by.epam.audioorder.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger();

    int page;
    int total;

    @Override
    public int doStartTag() throws JspException {
        try {
            if (total > 0) {
                JspWriter out = pageContext.getOut();
                out.println("<div class=\"btn-group\">");
                out.println("<input class=\"btn btn-default\" type=\"submit\" name=\"page\" value=\"1\">");
                for (int i = page - 3; i < page + 4; ++i) {
                    if (i > 0 && i < total) {
                        out.println("<input class=\"btn btn-default\" type=\"submit\" name=\"page\" value=\"" + i + "\">");
                    }
                }
                out.println("<input class=\"btn btn-default\" type=\"submit\" name=\"page\" value=\"" + total + "\">");
                out.println("</div>");
            }
        } catch (IOException e) {
            LOGGER.error("Cannot print pagination bar", e);
        }
        return SKIP_BODY;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

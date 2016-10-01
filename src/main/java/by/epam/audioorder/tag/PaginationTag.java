package by.epam.audioorder.tag;

import by.epam.audioorder.action.ConfigurationManager;
import com.sun.deploy.net.HttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
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
            if (total > 1) {
                JspWriter out = pageContext.getOut();
                String lastPage = (String) pageContext.getSession().getAttribute(ConfigurationManager.getProperty("attr.lastpage"));
                lastPage += (((HttpServletRequest) pageContext.getRequest()).getQueryString() != null) ? "&" : "?" + "page=";
                out.println("<div class=\"btn-group\">");
                out.println("<a class=\"btn btn-default\" type=\"submit\" name=\"page\" value=\"1\">" + 1 + "</a>");
                for (int i = page - 3; i < page + 4; ++i) {
                    if (i > 1 && i < total) {
                        out.println("<a class=\"btn btn-default\">" + i + "</a>");
                    }
                }
                out.println("<a class=\"btn btn-default\">" + total + "</a>");
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

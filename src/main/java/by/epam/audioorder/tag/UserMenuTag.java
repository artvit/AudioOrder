package by.epam.audioorder.tag;

import by.epam.audioorder.action.InternationalizationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UserMenuTag extends TagSupport{
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public int doStartTag() throws JspException {
        try {
            String contextPath = pageContext.getServletContext().getContextPath();
            pageContext.getOut().println("<ul class=\"nav navbar-nav navbar-right\">");
            if (pageContext.getSession().getAttribute("login") != null) {
                pageContext.getOut().println("<li>" + "<a href=\"" + contextPath + "/login\">" + "<span class=\"glyphicon glyphicon-list\"></span> " + pageContext.getSession().getAttribute("login") + "</a>" + "</li>");
                pageContext.getOut().println("<li>" + "<a href=\"" + contextPath + "/controller?command=logout\">" + InternationalizationManager.getProperty("menu.logout") + "</a>" + "</li>");
            } else {
                pageContext.getOut().println("<li>" + "<a href=\"" + contextPath + "/login\">" + InternationalizationManager.getProperty("menu.login") + "</a>" + "</li>");
                pageContext.getOut().println("<li>" + "<a href=\"" + contextPath + "/registration\">" + InternationalizationManager.getProperty("menu.registration") + "</a>" + "</li>");
            }
            pageContext.getOut().println("</ul>");
        } catch (IOException e) {
            LOGGER.error("Cannot print user menu", e);
        }
        return SKIP_BODY;
    }
}

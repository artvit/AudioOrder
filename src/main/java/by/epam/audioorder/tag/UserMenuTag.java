package by.epam.audioorder.tag;

import by.epam.audioorder.action.InternationalizationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UserMenuTag extends TagSupport{
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public int doStartTag() throws JspException {
        try {
            String contextPath = pageContext.getServletContext().getContextPath();
            JspWriter out = pageContext.getOut();
            out.println("<ul class=\"nav navbar-nav navbar-right\">");
            out.println("<li>" + "<a href=\"" + contextPath + "/cart\">" + "<span class=\"glyphicon glyphicon-shopping-cart\"></span> " + InternationalizationManager.getProperty("menu.cart") + "</a>" + "</li>");
            if (pageContext.getSession().getAttribute("login") != null) {
                out.println("<li>" + "<a href=\"" + contextPath + "/login\">" + "<span class=\"glyphicon glyphicon-list\"></span> " + pageContext.getSession().getAttribute("login") + "</a>" + "</li>");
                out.println("<li>" + "<a href=\"" + contextPath + "/controller?command=logout\">" + InternationalizationManager.getProperty("menu.logout") + "</a>" + "</li>");
            } else {
                out.println("<li>" + "<a href=\"" + contextPath + "/login\">" + InternationalizationManager.getProperty("menu.login") + "</a>" + "</li>");
                out.println("<li>" + "<a href=\"" + contextPath + "/registration\">" + InternationalizationManager.getProperty("menu.registration") + "</a>" + "</li>");
            }
            out.println("</ul>");
        } catch (IOException e) {
            LOGGER.error("Cannot print user menu", e);
        }
        return SKIP_BODY;
    }
}

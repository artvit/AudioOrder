package by.epam.audioorder.tag;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.action.InternationalizationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;

public class UserMenuTag extends TagSupport{
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public int doStartTag() throws JspException {
        try {
            String contextPath = pageContext.getServletContext().getContextPath();
            JspWriter out = pageContext.getOut();
            Locale locale = (Locale) pageContext.getSession().getAttribute(ConfigurationManager.getProperty("attr.locale"));
            out.println("<ul class=\"nav navbar-nav navbar-right\">");
            out.println("<li>" + "<a href=\"" + contextPath + "/cart\">" + "<span class=\"glyphicon glyphicon-shopping-cart\"></span> " + InternationalizationManager.getProperty("menu.cart", locale) + "</a>" + "</li>");
            if (pageContext.getSession().getAttribute("login") != null) {
                out.println("<li>" + "<a href=\"" + contextPath + "/login\">" + "<span class=\"glyphicon glyphicon-list\"></span> " + pageContext.getSession().getAttribute("login") + "</a>" + "</li>");
                out.println("<li>" + "<a href=\"" + contextPath + "/controller?command=logout\">" + InternationalizationManager.getProperty("menu.logout", locale) + "</a>" + "</li>");
            } else {
                out.println("<li>" + "<a href=\"" + contextPath + "/login\">" + InternationalizationManager.getProperty("menu.login", locale) + "</a>" + "</li>");
                out.println("<li>" + "<a href=\"" + contextPath + "/registration\">" + InternationalizationManager.getProperty("menu.registration", locale) + "</a>" + "</li>");
            }
            out.println("</ul>");
        } catch (IOException e) {
            LOGGER.error("Cannot print user menu", e);
        }
        return SKIP_BODY;
    }
}

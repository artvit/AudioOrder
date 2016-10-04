package by.epam.audioorder.tag;

import by.epam.audioorder.action.InternationalizationManager;
import by.epam.audioorder.config.AttributeName;
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
            Locale locale = (Locale) pageContext.getSession().getAttribute(AttributeName.LOCALE);
            out.println("<ul class=\"nav navbar-nav navbar-right\">");
            out.println("<li>" + "<a href=\"" + contextPath + "/cart\">" + "<span class=\"glyphicon glyphicon-shopping-cart\"></span> " + InternationalizationManager.getProperty("menu.cart", locale) + "</a>" + "</li>");
            if (pageContext.getSession().getAttribute("login") != null) {
                out.println("<li>" + "<a href=\"" + contextPath + "/account\">" + "<span class=\"glyphicon glyphicon-list\"></span> " + pageContext.getSession().getAttribute("login") + "</a>" + "</li>");
                out.println("<li>" + "<a href=\"" + contextPath + "/controller?command=logout\">" + InternationalizationManager.getProperty("menu.logout", locale) + "</a>" + "</li>");
            } else {
                out.println("<li>" + "<a href=\"" + contextPath + "/login\">" + InternationalizationManager.getProperty("menu.login", locale) + "</a>" + "</li>");
                out.println("<li>" + "<a href=\"" + contextPath + "/registration\">" + InternationalizationManager.getProperty("menu.registration", locale) + "</a>" + "</li>");
            }
            out.println("<li class=\"dropdown\">");
            out.println("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">" + InternationalizationManager.getProperty("menu.language", locale) + " <span class=\"caret\"></span></a>");
            out.println("<ul class=\"dropdown-menu\">");
            out.println("<li><a href=\"" + contextPath + "/controller?command=i18n&lang=en\">" + InternationalizationManager.getProperty("menu.language.english", locale) + "</a></li>");
            out.println("<li><a href=\"" + contextPath + "/controller?command=i18n&lang=ru\">" + InternationalizationManager.getProperty("menu.language.russian", locale) + "</a></li>");
            out.println("</ul>");
            out.println("</li>");
            out.println("</ul>");
        } catch (IOException e) {
            LOGGER.error("Cannot print user menu", e);
        }
        return SKIP_BODY;
    }
}

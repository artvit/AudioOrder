package by.epam.audioorder.tag;

import by.epam.audioorder.action.InternationalizationManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
//TODO rename class
public class UserMenuTag extends TagSupport{
    @Override
    public int doStartTag() throws JspException {
        try {
            String contextPath = pageContext.getServletContext().getContextPath();
            pageContext.getOut().println("<ul class=\"nav navbar-nav navbar-right\">");
            if (pageContext.getSession().getAttribute("login") != null) {
                pageContext.getOut().println("<li>" + "<a href=\"" + contextPath + "/login\">" + pageContext.getSession().getAttribute("login") + "</a>" + "</li>");
//                pageContext.getOut().println("<li>" + "<a href=\"" + contextPath + "/login\">" + InternationalizationManager.getProperty("menu.logout") + "</a>" + "</li>");
                pageContext.getOut().println("<li>");
                pageContext.getOut().println("<form class=\"navbar-form navbar-left\" role=\"search\">\n" +
                        "  <input type=\"hidden\" value=\"logout\">" +
                        "  <button type=\"submit\" class=\"btn btn-link\">" + InternationalizationManager.getProperty("menu.logout") +  "</button>\n" +
                        "</form>");
                pageContext.getOut().println("</li>");
            } else {
                pageContext.getOut().println("<li>" + "<a href=\"" + contextPath + "/login\">" + InternationalizationManager.getProperty("menu.login") + "</a>" + "</li>");
                pageContext.getOut().println("<li>" + "<a href=\"" + contextPath + "/registration\">" + InternationalizationManager.getProperty("menu.registration") + "</a>" + "</li>");
            }
            pageContext.getOut().println("</ul>");
        } catch (IOException e) {
//            TODO io exception handling
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}

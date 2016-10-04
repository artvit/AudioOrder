package by.epam.audioorder.filter;

import by.epam.audioorder.action.InternationalizationManager;
import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "PageAccessFilter")
public class PageAccessFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
        request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.access", locale));
        request.getRequestDispatcher(Page.ERROR).forward(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

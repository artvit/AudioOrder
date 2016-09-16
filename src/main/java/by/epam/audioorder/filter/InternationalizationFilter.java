package by.epam.audioorder.filter;

import by.epam.audioorder.action.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "InternationalizationFilter", urlPatterns = {"*"})
public class InternationalizationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getSession().getAttribute(ConfigurationManager.getProperty("attr.locale")) == null) {
            request.setAttribute(ConfigurationManager.getProperty("attr.locale"), request.getLocale());
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

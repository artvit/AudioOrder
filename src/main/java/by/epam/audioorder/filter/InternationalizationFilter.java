package by.epam.audioorder.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "InternationalizationFilter", urlPatterns = {"*"})
public class InternationalizationFilter implements Filter {
    public static final String LOCALE_ATTRIBUTE = "locale";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getSession().getAttribute(LOCALE_ATTRIBUTE) == null) {
            request.setAttribute(LOCALE_ATTRIBUTE, request.getLocale());
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

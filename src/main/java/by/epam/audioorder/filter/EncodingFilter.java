package by.epam.audioorder.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter", urlPatterns = {"*"})
public class EncodingFilter implements Filter {
    private static final String ENCODING = "UTF-8";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (!ENCODING.equals(req.getCharacterEncoding())) {
            req.setCharacterEncoding(ENCODING);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

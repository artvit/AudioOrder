package by.epam.audioorder.filter;

import by.epam.audioorder.config.*;
import by.epam.audioorder.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "UserAccessFilter")
public class UserAccessFilter implements Filter {
    private Set<String> forbiddenCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        forbiddenCommands = new HashSet<>();
        forbiddenCommands.add(CommandParameter.PAYMENT_COMPLETE);
        forbiddenCommands.add(CommandParameter.ACCOUNT_BONUSES);
        forbiddenCommands.add(CommandParameter.ACCOUNT_TRACKS);
        forbiddenCommands.add(CommandParameter.TRACK_COMMENT_ADD);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        if (user == null) {
            String servletPath = request.getServletPath();
            if (ServletMappingValue.URL_PAYMENT.equals(servletPath) ||
                    ServletMappingValue.URL_ACCOUNT.equals(servletPath)) {
                response.sendRedirect(ServletMappingValue.URL_LOGIN);
                return;
            }
            String command = request.getParameter(ParameterName.COMMAND);
            if (command != null && forbiddenCommands.contains(command)) {
                response.sendRedirect(ServletMappingValue.URL_LOGIN);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

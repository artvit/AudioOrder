package by.epam.audioorder.filter;

import by.epam.audioorder.action.InternationalizationManager;
import by.epam.audioorder.command.Command;
import by.epam.audioorder.config.*;
import by.epam.audioorder.entity.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@WebFilter(filterName = "AdminAccessFilter")
public class AdminAccessFilter implements Filter {
    private Set<String> forbiddenCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        forbiddenCommands = new HashSet<>();
        forbiddenCommands.add(CommandParameter.BONUS_ADD);
        forbiddenCommands.add(CommandParameter.SEARCH_USER);
        forbiddenCommands.add(CommandParameter.TRACK_ADD);
        forbiddenCommands.add(CommandParameter.TRACK_SAVE);
        forbiddenCommands.add(CommandParameter.TRACK_EDIT);
        forbiddenCommands.add(CommandParameter.TRACK_COMMENT_DELETE);
        forbiddenCommands.add(CommandParameter.USER_EDIT);
        forbiddenCommands.add(CommandParameter.BONUS_ADD);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        UserType userType = (UserType) request.getSession().getAttribute(AttributeName.ROLE);
        Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
        if (userType != UserType.ADMIN) {
            String servletPath = request.getServletPath();
            if (ServletMappingValue.URL_ADD_TRACK.equals(servletPath) ||
                    ServletMappingValue.URL_CLIENTS.equals(servletPath)) {
                request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.access", locale));
                request.getRequestDispatcher(Page.ERROR).forward(request, servletResponse);
                return;
            }
            String command = request.getParameter(ParamenterName.COMMAND);
            if (forbiddenCommands.contains(command)) {
                request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.access", locale));
                request.getRequestDispatcher(Page.ERROR).forward(request, servletResponse);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

package by.bsu.audioorder.command;

import by.bsu.audioorder.action.InternationalizationManager;
import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.config.ParameterName;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(ParameterName.LOGIN);
        String password = request.getParameter(ParameterName.PASSWORD);
        LoginService loginService = new LoginService();
        User user  = loginService.authenticate(login, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute(AttributeName.USER, user);
            session.setAttribute(AttributeName.LOGIN, user.getLogin());
            session.setAttribute(AttributeName.ROLE, user.getRole());
            LOGGER.info("User " + login + " signed in successfully");
            String lastPage = (String) session.getAttribute(AttributeName.LAST_PAGE);
            if (lastPage == null || lastPage.isEmpty()) {
                lastPage = Page.INDEX;
            }
            return new CommandResult(lastPage, CommandResult.Type.REDIRECT);
        } else {
            Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
            String message = InternationalizationManager.getProperty("login.error.invalid", locale);
            request.setAttribute(AttributeName.MESSAGE, message);
            return new CommandResult(Page.LOGIN, CommandResult.Type.FORWARD);
        }
    }
}

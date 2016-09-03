package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.action.InternationalizationManager;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String MESSAGE = "message";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        LoginService loginService = new LoginService();
        User user  = loginService.authenticate(login, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute(LOGIN, user.getLogin());
            session.setAttribute(ROLE, user.getRole());
            LOGGER.info("User " + login + " signed in successfully");
            return new CommandResult(ConfigurationManager.getProperty("page.index"), CommandResult.Type.REDIRECT);
        } else {
            request.setAttribute(MESSAGE, InternationalizationManager.getProperty("login.error.invalid"));
            return new CommandResult(ConfigurationManager.getProperty("page.login"), CommandResult.Type.FORWARD);
        }
    }
}

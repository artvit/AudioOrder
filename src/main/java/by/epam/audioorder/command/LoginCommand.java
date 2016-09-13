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

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(ConfigurationManager.getProperty("param.login"));
        String password = request.getParameter(ConfigurationManager.getProperty("param.password"));
        LoginService loginService = new LoginService();
        User user  = loginService.authenticate(login, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute(ConfigurationManager.getProperty("attr.login"), user.getLogin());
            session.setAttribute(ConfigurationManager.getProperty("attr.role"), user.getRole());
            LOGGER.info("User " + login + " signed in successfully");
            String lastPage = (String) request.getSession().getAttribute(ConfigurationManager.getProperty("attr.lastpage"));
            return new CommandResult(lastPage, CommandResult.Type.REDIRECT);
        } else {
            request.setAttribute(ConfigurationManager.getProperty("attr.message"), InternationalizationManager.getProperty("login.error.invalid"));
            return new CommandResult(ConfigurationManager.getProperty("page.login"), CommandResult.Type.FORWARD);
        }
    }
}

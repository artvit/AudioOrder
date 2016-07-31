package by.epam.audioorder.command;

import by.epam.audioorder.entity.User;
import by.epam.audioorder.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        LoginService loginService = new LoginService();
        User user  = loginService.authenticate(login, password);
        if (user != null) {
            request.getSession().setAttribute(LOGIN, user.getLogin());
            request.getSession().setAttribute(ROLE, user.getRole());
            LOGGER.info("User " + login + " signed in successfully");
            return "/index.jsp";
        } else {
            return "/pages/login.jsp";
        }
    }
}

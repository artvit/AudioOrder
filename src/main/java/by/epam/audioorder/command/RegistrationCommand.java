package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.exception.ServiceException;
import by.epam.audioorder.service.RegistrationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    private static final String LOGIN = "login";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_CONFIRM = "passwordConfirm";
    private static final String MESSAGE = "message";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(LOGIN);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String passwordConfirm = request.getParameter(PASSWORD_CONFIRM);
        RegistrationService service = new RegistrationService();
        try {
            service.signUp(login, email, password, passwordConfirm);
            return new CommandResult(ConfigurationManager.getProperty("page.registration-success"), CommandResult.Type.FORWARD);
        } catch (ServiceException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            return new CommandResult(ConfigurationManager.getProperty("page.registration"), CommandResult.Type.FORWARD);
        }
    }
}

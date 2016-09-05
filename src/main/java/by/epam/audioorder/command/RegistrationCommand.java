package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.exception.ServiceException;
import by.epam.audioorder.service.RegistrationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(ConfigurationManager.getProperty("param.login"));
        String email = request.getParameter(ConfigurationManager.getProperty("param.email"));
        String password = request.getParameter(ConfigurationManager.getProperty("param.password"));
        String passwordConfirm = request.getParameter(ConfigurationManager.getProperty("param.password.confirm"));
        RegistrationService service = new RegistrationService();
        try {
            service.signUp(login, email, password, passwordConfirm);
            return new CommandResult(ConfigurationManager.getProperty("page.registration.success"), CommandResult.Type.FORWARD);
        } catch (ServiceException e) {
            request.setAttribute(ConfigurationManager.getProperty("param.login"), login);
            request.setAttribute(ConfigurationManager.getProperty("param.email"), email);
            request.setAttribute(ConfigurationManager.getProperty("attr.message"), e.getMessage());
            return new CommandResult(ConfigurationManager.getProperty("page.registration"), CommandResult.Type.FORWARD);
        }
    }
}

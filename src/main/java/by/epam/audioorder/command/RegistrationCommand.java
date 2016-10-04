package by.epam.audioorder.command;

import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParameterName;
import by.epam.audioorder.exception.ServiceException;
import by.epam.audioorder.service.RegistrationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(ParameterName.LOGIN);
        String email = request.getParameter(ParameterName.EMAIL);
        String password = request.getParameter(ParameterName.PASSWORD);
        String passwordConfirm = request.getParameter(ParameterName.PASSWORD_CONFIRM);
        RegistrationService service = new RegistrationService();
        try {
            service.signUp(login, email, password, passwordConfirm);
            return new CommandResult(Page.REGISTRATION_SUCCESS, CommandResult.Type.FORWARD);
        } catch (ServiceException e) {
            request.setAttribute(ParameterName.LOGIN, login);
            request.setAttribute(ParameterName.EMAIL, email);
            request.setAttribute(AttributeName.MESSAGE, e.getMessage());
            return new CommandResult(Page.REGISTRATION, CommandResult.Type.FORWARD);
        }
    }
}

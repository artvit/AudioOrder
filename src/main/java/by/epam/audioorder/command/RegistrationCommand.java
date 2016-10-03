package by.epam.audioorder.command;

import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParamenterName;
import by.epam.audioorder.exception.ServiceException;
import by.epam.audioorder.service.RegistrationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(ParamenterName.LOGIN);
        String email = request.getParameter(ParamenterName.EMAIL);
        String password = request.getParameter(ParamenterName.PASSWORD);
        String passwordConfirm = request.getParameter(ParamenterName.PASSWORD_CONFIRM);
        RegistrationService service = new RegistrationService();
        try {
            service.signUp(login, email, password, passwordConfirm);
            return new CommandResult(Page.REGISTRATION_SUCCESS, CommandResult.Type.FORWARD);
        } catch (ServiceException e) {
            request.setAttribute(ParamenterName.LOGIN, login);
            request.setAttribute(ParamenterName.EMAIL, email);
            request.setAttribute(AttributeName.MESSAGE, e.getMessage());
            return new CommandResult(Page.REGISTRATION, CommandResult.Type.FORWARD);
        }
    }
}

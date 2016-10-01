package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.entity.UserType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(ConfigurationManager.getProperty("attr.login"));
        session.removeAttribute(ConfigurationManager.getProperty("attr.cart"));
        session.setAttribute(ConfigurationManager.getProperty("attr.role"), UserType.GUEST);
        String lastPage = (String) request.getSession().getAttribute(ConfigurationManager.getProperty("attr.lastpage"));
        return new CommandResult(lastPage, CommandResult.Type.REDIRECT);
    }
}
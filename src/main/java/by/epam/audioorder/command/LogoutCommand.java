package by.epam.audioorder.command;

import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.entity.UserType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(AttributeName.LOGIN);
        session.removeAttribute(AttributeName.USER);
        session.removeAttribute(AttributeName.CART);
        session.setAttribute(AttributeName.ROLE, UserType.GUEST);
        String lastPage = (String) request.getSession().getAttribute(AttributeName.LAST_PAGE);
        return new CommandResult(lastPage, CommandResult.Type.REDIRECT);
    }
}
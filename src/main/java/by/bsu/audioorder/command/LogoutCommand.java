package by.bsu.audioorder.command;

import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.entity.UserType;
import by.bsu.audioorder.config.AttributeName;

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
        return new CommandResult(Page.INDEX, CommandResult.Type.REDIRECT);
    }
}
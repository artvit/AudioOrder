package by.bsu.audioorder.command;

import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.config.ParameterName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLanguageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String lang = request.getParameter(ParameterName.LANG);
        Locale locale = new Locale(lang);
        HttpSession session = request.getSession();
        session.setAttribute(AttributeName.LOCALE, locale);
        String lastPage = (String) session.getAttribute(AttributeName.LAST_PAGE);
        return new CommandResult(lastPage, CommandResult.Type.REDIRECT);
    }
}

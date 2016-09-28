package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLanguageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String lang = request.getParameter(ConfigurationManager.getProperty("param.lang"));
        Locale locale = new Locale(lang);
        HttpSession session = request.getSession();
        session.setAttribute(ConfigurationManager.getProperty("attr.locale"), locale);
        String lastPage = (String) session.getAttribute(ConfigurationManager.getProperty("attr.lastpage"));
        return new CommandResult(lastPage, CommandResult.Type.REDIRECT);
    }
}

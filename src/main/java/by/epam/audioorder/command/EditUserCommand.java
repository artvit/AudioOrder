package by.epam.audioorder.command;

import by.epam.audioorder.action.IdParameterParser;
import by.epam.audioorder.action.InternationalizationManager;
import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParamenterName;
import by.epam.audioorder.entity.Bonus;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.service.BonusService;
import by.epam.audioorder.service.UserSearchService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

public class EditUserCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
        String idParameter = request.getParameter(ParamenterName.ID);
        IdParameterParser idParser = new IdParameterParser();
        if (!idParser.parse(idParameter)) {
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.param.id", locale));
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        long id = idParser.getResult();
        UserSearchService userSearchService = new UserSearchService();
        User user = userSearchService.findUserById(id);
        if (user == null) {
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.user.notfound", locale));
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        BonusService bonusService = new BonusService();
        List<Bonus> bonuses = bonusService.bonusesForUser(user);
        request.setAttribute(ParamenterName.ID, user.getUserId());
        request.setAttribute(AttributeName.USER, user);
        request.setAttribute(AttributeName.RESULTS, bonuses);
        return new CommandResult(Page.USER_EDIT, CommandResult.Type.FORWARD);
    }
}

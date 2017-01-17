package by.bsu.audioorder.command;

import by.bsu.audioorder.action.IdParameterParser;
import by.bsu.audioorder.action.InternationalizationManager;
import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.service.BonusService;
import by.bsu.audioorder.service.UserSearchService;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.config.ParameterName;
import by.bsu.audioorder.entity.Bonus;
import by.bsu.audioorder.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

public class EditUserCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
        String idParameter = request.getParameter(ParameterName.ID);
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
        request.setAttribute(ParameterName.ID, user.getUserId());
        request.setAttribute(AttributeName.USER, user);
        request.setAttribute(AttributeName.RESULTS, bonuses);
        return new CommandResult(Page.USER_EDIT, CommandResult.Type.FORWARD);
    }
}

package by.epam.audioorder.command;

import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.entity.Bonus;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.service.BonusService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AccountBonusesCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        BonusService bonusService = new BonusService();
        List<Bonus> bonuses = bonusService.bonusesForUser(user);
        request.setAttribute(AttributeName.RESULTS, bonuses);
        request.setAttribute(AttributeName.SECTION, AttributeName.SECTION_BONUSES);
        return new CommandResult(Page.ACCOUNT, CommandResult.Type.FORWARD);
    }
}

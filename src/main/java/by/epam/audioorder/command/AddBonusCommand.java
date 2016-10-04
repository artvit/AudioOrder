package by.epam.audioorder.command;

import by.epam.audioorder.action.IdParameterParser;
import by.epam.audioorder.action.InternationalizationManager;
import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParameterName;
import by.epam.audioorder.entity.Genre;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.service.SaveBonusService;
import by.epam.audioorder.service.UserSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class AddBonusCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
        String idParameter = request.getParameter(ParameterName.ID);
        IdParameterParser idParser = new IdParameterParser();
        if (!idParser.parse(idParameter)) {
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.param.id", locale));
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        long userId = idParser.getResult();
        UserSearchService userSearchService = new UserSearchService();
        User user = userSearchService.findUserById(userId);
        if (user == null) {
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.user.notfound", locale));
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        Genre genre = Genre.ANY;
        String genreParameter = request.getParameter(ParameterName.GENRE);
        if (genreParameter != null && !genreParameter.isEmpty()) {
            genre = Genre.valueOf(genreParameter.toUpperCase());
        } else {
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.", locale));
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        String afterParameter = request.getParameter(ParameterName.AFTER);
        int after = 0;
        try {
            after = Integer.parseInt(afterParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        String beforeParameter = request.getParameter(ParameterName.BEFORE);
        int before = 0;
        try {
            before = Integer.parseInt(beforeParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Wrong year parameter", e);
        }
        String bonusValueParameter = request.getParameter(ParameterName.BONUS_VALUE);
        String saleParameter = request.getParameter(ParameterName.SALE);
        if ((bonusValueParameter == null || bonusValueParameter.isEmpty()) && (saleParameter == null || saleParameter.isEmpty())) {
            LOGGER.error("Sale and bonus value was not passed both");
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.param.notall", locale));
        }
        if (bonusValueParameter == null) {
            bonusValueParameter = "";
        }
        if (saleParameter == null) {
            saleParameter = "";
        }
        double bonusValue = 0;
        try {
            bonusValue = Double.parseDouble(bonusValueParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Bonus value parameter was missed");
        }
        double sale = 0;
        try {
            sale = Double.parseDouble(saleParameter);
        } catch (NumberFormatException e) {
            LOGGER.warn("Sale parameter was missed");
        }
        SaveBonusService saveBonusService = new SaveBonusService();
        saveBonusService.saveBonus(userId, genre, after, before, bonusValue, sale);
        String lastPage = (String) request.getSession().getAttribute(AttributeName.LAST_PAGE);
        return new CommandResult(lastPage, CommandResult.Type.REDIRECT);
    }
}

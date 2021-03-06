package by.bsu.audioorder.command;

import by.bsu.audioorder.action.InternationalizationManager;
import by.bsu.audioorder.action.IdParameterParser;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.config.Page;
import by.bsu.audioorder.config.ParameterName;
import by.bsu.audioorder.config.ServletMappingValue;
import by.bsu.audioorder.entity.Bonus;
import by.bsu.audioorder.entity.Track;
import by.bsu.audioorder.entity.User;
import by.bsu.audioorder.service.BonusService;
import by.bsu.audioorder.service.CountTotalService;
import by.bsu.audioorder.service.TrackInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ShowPaymentCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String[] bonusParameter = request.getParameterValues(ParameterName.BONUS);
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        List<Bonus> bonuses = null;
        if (bonusParameter == null) {
            BonusService bonusService = new BonusService();
            bonuses = bonusService.bonusesForUser(user);
            if (bonuses != null && !bonuses.isEmpty()) {
                request.setAttribute(AttributeName.RESULTS, bonuses);
                return new CommandResult(Page.PAYMENT_BONUS, CommandResult.Type.FORWARD);
            }
        } else {
            BonusService bonusService = new BonusService();
            bonuses = new ArrayList<>();
            for (String bonusIdParameter : bonusParameter) {
                IdParameterParser parser = new IdParameterParser();
                if (parser.parse(bonusIdParameter)) {
                    Bonus bonus = bonusService.bonusForId(parser.getResult());
                    if (bonus != null) {
                        bonuses.add(bonus);
                    }
                }
            }
        }
        HashSet<Track> cart = (HashSet<Track>) request.getSession().getAttribute(AttributeName.CART);
        List<Track> tracks = new ArrayList<>(cart);
        if (cart.isEmpty()) {
            Locale locale = (Locale) request.getSession().getAttribute(AttributeName.LOCALE);
            request.setAttribute(AttributeName.MESSAGE, InternationalizationManager.getProperty("error.emptycart", locale));
            return new CommandResult(Page.ERROR, CommandResult.Type.FORWARD);
        }
        List<Track> boughtTrack = new ArrayList<>();
        TrackInfoService trackInfoService = new TrackInfoService();
        boughtTrack.addAll(tracks.stream().filter(track -> trackInfoService.checkUserHasTrack(user, track)).collect(Collectors.toList()));
        if (boughtTrack.size() > 0) {
            cart.removeAll(boughtTrack);
            request.getSession().setAttribute(AttributeName.CART, cart);
            return new CommandResult(ServletMappingValue.URL_CART, CommandResult.Type.REDIRECT);
        }
        CountTotalService countTotalService = new CountTotalService();
        double total = countTotalService.countTotal(tracks, bonuses);
        request.getSession().setAttribute(AttributeName.USING_BONUS, bonuses);
        request.setAttribute(AttributeName.TOTAL, total);
        return new CommandResult(Page.PAYMENT, CommandResult.Type.FORWARD);
    }
}

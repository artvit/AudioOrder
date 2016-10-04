package by.epam.audioorder.command;

import by.epam.audioorder.config.AttributeName;
import by.epam.audioorder.config.ServletMappingValue;
import by.epam.audioorder.entity.Bonus;
import by.epam.audioorder.entity.Track;
import by.epam.audioorder.entity.User;
import by.epam.audioorder.service.AddTrackToUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompletePaymentCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Set<Track> cart = (Set<Track>) request.getSession().getAttribute(AttributeName.CART);
        List<Track> tracks = new ArrayList<>(cart);
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        List<Bonus> usingBonuses = (List<Bonus>) request.getSession().getAttribute(AttributeName.USING_BONUS);
        AddTrackToUserService addTrackToUserService = new AddTrackToUserService();
        addTrackToUserService.addTracksToUser(tracks, user, usingBonuses);
        request.getSession().removeAttribute(AttributeName.CART);
        request.getSession().removeAttribute(AttributeName.USING_BONUS);
        return new CommandResult(ServletMappingValue.URL_ACCOUNT, CommandResult.Type.REDIRECT);
    }
}

package by.bsu.audioorder.command;

import by.bsu.audioorder.config.ServletMappingValue;
import by.bsu.audioorder.service.AddTrackToUserService;
import by.bsu.audioorder.config.AttributeName;
import by.bsu.audioorder.entity.Bonus;
import by.bsu.audioorder.entity.Track;
import by.bsu.audioorder.entity.User;

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

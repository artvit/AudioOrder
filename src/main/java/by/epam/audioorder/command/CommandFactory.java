package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.exception.UnsupportedCommandException;

public class CommandFactory {
    public Command createCommand(String command) throws UnsupportedCommandException {
        Command result;
        if (command == null || command.isEmpty()) {
            throw new UnsupportedCommandException("Empty command");
        }
        if (command.equals(ConfigurationManager.getProperty("command.login"))) {
            result = new LoginCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.registration"))) {
            result = new RegistrationCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.logout"))) {
            result = new LogoutCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.search.track"))) {
            result = new TrackSearchCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.search.user"))) {
            result = new UserSearchCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.track.info"))) {
            result = new TrackInfoCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.track.comment.send"))) {
            result = new AddCommentCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.track.comment.delete"))) {
            result = new DeleteCommentCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.track.cart.add"))) {
            result = new AddTrackToCartCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.track.cart.delete"))) {
            result = new DeleteFromCartCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.track.add"))) {
            result = new AddTrackCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.locale"))) {
            result = new ChangeLanguageCommand();
        } else {
            throw new UnsupportedCommandException("Unknown command type");
        }
        return result;
    }
}

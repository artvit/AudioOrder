package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.exception.UnsupportedCommandException;

public class CommandFactory {
    public Command createCommand(String command) throws UnsupportedCommandException {
        Command result;
        if (command == null || command.isEmpty()) {
            throw new UnsupportedCommandException("Empty command");
        }
        if (command.equals(ConfigurationManager.getProperty("command.postfile"))) {
            result = new ParseUploadedFileCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.login"))) {
            result = new LoginCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.registration"))) {
            result = new RegistrationCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.logout"))) {
            result = new LogoutCommand();
        } else if (command.equals(ConfigurationManager.getProperty("command.search.track"))) {
            result = new TrackSearchCommand();
        } else {
            throw new UnsupportedCommandException("Unknown command type");
        }
        return result;
    }
}

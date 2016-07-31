package by.epam.audioorder.command;

import by.epam.audioorder.exception.UnsupportedCommandException;

public class CommandFactory {
    public static final String POST_FILE = "post-file";
    public static final String POST_FILENAME = "post-filename";
    public static final String LOGIN = "login";

    public Command createCommand(String command) throws UnsupportedCommandException {
        Command result;
        switch (command) {
            case POST_FILE:
                result = new ParseUploadedFileCommand();
                break;
            case LOGIN:
                result = new LoginCommand();
                break;
            case POST_FILENAME:
            default:
                throw new UnsupportedCommandException("Unknown command type");
        }
        return result;
    }
}

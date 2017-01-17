package by.bsu.audioorder.command;

import by.bsu.audioorder.config.CommandParameter;
import by.bsu.audioorder.exception.UnsupportedCommandException;

public class CommandFactory {
    public static Command createCommand(String command) throws UnsupportedCommandException {
        Command result;
        if (command == null || command.isEmpty()) {
            throw new UnsupportedCommandException("Empty command");
        }
        switch (command) {
            case CommandParameter.LOGIN:
                result = new LoginCommand();
                break;
            case CommandParameter.REGISTRATION:
                result = new RegistrationCommand();
                break;
            case CommandParameter.LOGOUT:
                result = new LogoutCommand();
                break;
            case CommandParameter.SEARCH_TRACK:
                result = new TrackSearchCommand();
                break;
            case CommandParameter.SEARCH_USER:
                result = new UserSearchCommand();
                break;
            case CommandParameter.TRACK_INFO:
                result = new TrackInfoCommand();
                break;
            case CommandParameter.TRACK_COMMENT_ADD:
                result = new AddCommentCommand();
                break;
            case CommandParameter.TRACK_COMMENT_DELETE:
                result = new DeleteCommentCommand();
                break;
            case CommandParameter.TRACK_CART_ADD:
                result = new AddTrackToCartCommand();
                break;
            case CommandParameter.TRACK_CART_DELETE:
                result = new DeleteFromCartCommand();
                break;
            case CommandParameter.TRACK_ADD:
                result = new AddTrackCommand();
                break;
            case CommandParameter.LOCALE:
                result = new ChangeLanguageCommand();
                break;
            case CommandParameter.ACCOUNT_TRACKS:
                result = new AccountTracksCommand();
                break;
            case CommandParameter.ACCOUNT_BONUSES:
                result = new AccountBonusesCommand();
                break;
            case CommandParameter.TRACK_EDIT:
                result = new EditTrackCommand();
                break;
            case CommandParameter.TRACK_SAVE:
                result = new SaveTrackCommand();
                break;
            case CommandParameter.TRACK_DOWNLOAD:
                result = new DownloadCommand();
                break;
            case CommandParameter.PAYMENT:
                result = new ShowPaymentCommand();
                break;
            case CommandParameter.PAYMENT_COMPLETE:
                result = new CompletePaymentCommand();
                break;
            case CommandParameter.USER_EDIT:
                result = new EditUserCommand();
                break;
            case CommandParameter.BONUS_ADD:
                result = new AddBonusCommand();
                break;
            default:
                throw new UnsupportedCommandException("Unknown command type");
        }
        return result;
    }
}

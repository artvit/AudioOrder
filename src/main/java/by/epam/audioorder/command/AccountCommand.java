package by.epam.audioorder.command;

import by.epam.audioorder.action.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {

        return new CommandResult(ConfigurationManager.getProperty("page.account"), CommandResult.Type.FORWARD);
    }
}

package by.epam.audioorder.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Page.REGISTRATION;
    }
}

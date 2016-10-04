package by.epam.audioorder.controller;

import by.epam.audioorder.command.Command;
import by.epam.audioorder.command.CommandFactory;
import by.epam.audioorder.command.CommandResult;
import by.epam.audioorder.config.CommandParameter;
import by.epam.audioorder.config.Page;
import by.epam.audioorder.config.ParamenterName;
import by.epam.audioorder.config.ServletMappingValue;
import by.epam.audioorder.exception.UnsupportedCommandException;
import by.epam.audioorder.exception.UnsupportedPageException;
import by.epam.audioorder.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ControllerServlet",
        urlPatterns = {"/controller", "/pages/controller", "/login", "/registration", "/tracks", "/addtrack", "/clients", "/cart", "/account", "/payment"})
@MultipartConfig
public class ControllerServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();


    private CommandFactory commandFactory;

    @Override
    public void init() throws ServletException {
        super.init();
        commandFactory = new CommandFactory();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleCommandRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter(ParamenterName.COMMAND) != null) {
            handleCommandRequest(request, response);
        } else {
            handleNonCommandRequest(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().close();
    }

    private void handleCommandRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(ParamenterName.COMMAND);
        if (commandName != null) {
            handleCommand(commandName, request, response);
        } else {
            LOGGER.error("Null command in POST request");
            request.getRequestDispatcher(Page.ERROR).forward(request, response);
        }
    }

    private void handleNonCommandRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if (ServletMappingValue.URL_TRACKS.equals(servletPath)) {
            handleCommand(CommandParameter.SEARCH_TRACK, request, response);
        } else if (ServletMappingValue.URL_CLIENTS.equals(servletPath)) {
            handleCommand(CommandParameter.SEARCH_USER, request, response);
        } else if (ServletMappingValue.URL_ACCOUNT.equals(servletPath)) {
            handleCommand(CommandParameter.ACCOUNT_TRACKS, request, response);
        } else if (ServletMappingValue.URL_PAYMENT.equals(servletPath)) {
            handleCommand(CommandParameter.PAYMENT, request, response);
        } else {
            try {
                request.getRequestDispatcher(getForwardPage(servletPath)).forward(request, response);
            } catch (UnsupportedPageException e) {
                request.getRequestDispatcher(Page.ERROR).forward(request, response);
            }
        }
    }

    private void handleCommand(String commandName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Command command = commandFactory.createCommand(commandName);
            if (command != null) {
                CommandResult result = command.execute(request, response);
                if (result != null) {
                    if (result.getType() == CommandResult.Type.REDIRECT) {
                        response.sendRedirect(result.getAddress());
                    } else {
                        request.getRequestDispatcher(result.getAddress()).forward(request, response);
                    }
                }
            }
        } catch (UnsupportedCommandException e) {
            request.getRequestDispatcher(Page.ERROR).forward(request, response);
        }
    }


    private static String getForwardPage(String servletPath) throws UnsupportedPageException {
        switch (servletPath) {
            case ServletMappingValue.URL_LOGIN:
                return Page.LOGIN;
            case ServletMappingValue.URL_REGISTRATION:
                return Page.REGISTRATION;
            case ServletMappingValue.URL_ERROR:
                return Page.ERROR;
            case ServletMappingValue.URL_TRACKS:
                return Page.TRACKS;
            case ServletMappingValue.URL_CART:
                return Page.CART;
            case ServletMappingValue.URL_ADD_TRACK:
                return Page.TRACK_ADD;
            default:
                throw new UnsupportedPageException("Page " + servletPath + " is not supported");
        }
    }
}

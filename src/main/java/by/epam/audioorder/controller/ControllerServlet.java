package by.epam.audioorder.controller;

import by.epam.audioorder.action.ConfigurationManager;
import by.epam.audioorder.command.Command;
import by.epam.audioorder.command.CommandFactory;
import by.epam.audioorder.command.CommandResult;
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
        urlPatterns = {"/controller", "/pages/controller", "/login", "/registration", "/tracks", "/addtrack", "/clients", "/cart"})
@MultipartConfig(location = "C:\\apache-tomcat-8.0.36\\webapps\\parser\\tmp",
        fileSizeThreshold = 1024*1024*2,
        maxFileSize = 1024*1024*10,
        maxRequestSize = 1024*1024*50)
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
        if (request.getParameter(ConfigurationManager.getProperty("param.command")) != null) {
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
        String commandName = request.getParameter(ConfigurationManager.getProperty("param.command"));
        if (commandName == null) {
            request.getRequestDispatcher(ConfigurationManager.getProperty("page.error")).forward(request, response);
        } else {
            handleCommand(commandName, request, response);
        }


    }

    private void handleNonCommandRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if (servletPath.equals("/tracks")) {
            handleCommand(ConfigurationManager.getProperty("command.search.track"), request, response);
        } else if (servletPath.equals("/clients")) {
            handleCommand(ConfigurationManager.getProperty("command.search.user"), request, response);
        } else {
            try {
                request.getRequestDispatcher(getForwardPage(servletPath)).forward(request, response);
            } catch (UnsupportedPageException e) {
                request.getRequestDispatcher(ConfigurationManager.getProperty("page.error")).forward(request, response);
            }
        }
    }

    private void handleCommand(String commandName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Command command = commandFactory.createCommand(commandName);
            if (command != null) {
                CommandResult result = command.execute(request, response);
                if (result.getType() == CommandResult.Type.REDIRECT) {
                    response.sendRedirect(result.getAddress());
                } else {
                    request.getRequestDispatcher(result.getAddress()).forward(request, response);
                }
            }
        } catch (UnsupportedCommandException e) {
            request.getRequestDispatcher(ConfigurationManager.getProperty("page.error")).forward(request, response);
        }
    }

    private static String getForwardPage(String servletPath) throws UnsupportedPageException {
        switch (servletPath) {
            case "/login":
                return ConfigurationManager.getProperty("page.login");
            case "/registration":
                return ConfigurationManager.getProperty("page.registration");
            case "/error":
                return ConfigurationManager.getProperty("page.error");
            case "/tracks":
                return ConfigurationManager.getProperty("page.tracks");
            case "/cart":
                return ConfigurationManager.getProperty("page.cart");
            case "/addtrack":
                return ConfigurationManager.getProperty("page.track.add");
            default:
                throw new UnsupportedPageException("Page " + servletPath + " is not supported");
        }
    }
}

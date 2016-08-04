package by.epam.audioorder.controller;

import by.epam.audioorder.command.Command;
import by.epam.audioorder.command.CommandFactory;
import by.epam.audioorder.exception.UnsupportedCommandException;
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

@WebServlet(name = "ControllerServlet", urlPatterns = {"/controller", "/pages/controller"})
@MultipartConfig(location = "C:\\apache-tomcat-8.0.36\\webapps\\parser\\tmp",
        fileSizeThreshold = 1024*1024*2,
        maxFileSize = 1024*1024*10,
        maxRequestSize = 1024*1024*50)
public class ControllerServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String COMMAND = "command";
    private CommandFactory commandFactory;


    @Override
    public void init() throws ServletException {
        super.init();
        commandFactory = new CommandFactory();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        LOGGER.debug("Context Path: " + request.getContextPath());
//        LOGGER.debug("Servlet Path: " + request.getServletPath());
//        LOGGER.debug("Real Path: " + request.getServletContext().getRealPath(""));
        Command command;
        try {
            String commandName = request.getParameter(COMMAND);
            if (commandName == null) {
                request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
            }
            command = commandFactory.createCommand(commandName);
            if (command != null) {
                String result = command.execute(request, response);
                response.sendRedirect(result);
            }
        } catch (UnsupportedCommandException e) {
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().close();
    }
}
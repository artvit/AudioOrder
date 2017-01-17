package by.bsu.audioorder.listener;

import by.bsu.audioorder.entity.Genre;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ConstantsConfigListener implements ServletContextListener{
    public ConstantsConfigListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("genres", Genre.values());
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}

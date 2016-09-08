package by.epam.audioorder.listener; /**
 * Created by artvi on 06/09/2016.
 */

import by.epam.audioorder.entity.Genre;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

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

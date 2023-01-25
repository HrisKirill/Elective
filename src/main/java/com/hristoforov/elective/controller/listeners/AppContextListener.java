package com.hristoforov.elective.controller.listeners;

import com.hristoforov.elective.controller.context.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * AppContextListener  class.
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(AppContextListener.class);

    /**
     * Create AppContext to initialize all required classes
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppContext.createAppContext();
        LOGGER.info("Context is set");
    }
}

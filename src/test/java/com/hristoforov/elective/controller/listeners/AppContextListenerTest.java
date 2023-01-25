package com.hristoforov.elective.controller.listeners;

import com.hristoforov.elective.connection.DBCPDataSource;
import com.hristoforov.elective.controller.context.AppContext;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppContextListenerTest {

    private static final ServletContextEvent sce = mock(ServletContextEvent.class);
    private static final ServletContext servletContext = mock(ServletContext.class);

    @Test
    void testContextInitialized() {
        when(sce.getServletContext()).thenReturn(servletContext);
        new AppContextListener().contextInitialized(sce);
        assertNotNull(AppContext.getAppContext());
    }
}
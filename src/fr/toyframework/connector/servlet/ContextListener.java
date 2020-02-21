package fr.toyframework.connector.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import fr.toyframework.Log;
import fr.toyframework.task.Scheduler;

public final class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent e) {
        try {
        	Log.init();
        	Scheduler s = new Scheduler();
        	s.run();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void contextDestroyed(ServletContextEvent e) {

    }

}

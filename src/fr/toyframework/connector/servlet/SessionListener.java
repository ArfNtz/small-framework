package fr.toyframework.connector.servlet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import fr.toyframework.Log;

public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se) {
    	Log.logConnector.entering(getClass().getName(), "sessionCreated", se);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
    	Log.logConnector.entering(getClass().getName(), "sessionDestroyed", se);
    }

}

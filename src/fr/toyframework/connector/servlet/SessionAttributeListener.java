package fr.toyframework.connector.servlet;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

//import framework.Log;

public class SessionAttributeListener implements HttpSessionAttributeListener {

    public void attributeAdded(HttpSessionBindingEvent se) {
    	//Log.logConnector.entering(getClass().getName(), "attributeAdded", se);
    }

    public void attributeRemoved(HttpSessionBindingEvent se) {
    	//Log.logConnector.entering(getClass().getName(), "attributeRemoved", se);
    }

    public void attributeReplaced(HttpSessionBindingEvent se) {
    	//Log.logConnector.entering(getClass().getName(), "attributeReplaced", se);
    }


}

package fr.toyframework.connector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class JdoConnect {

	private static String jdoResourceName = JdoConnect.class.getName();
	private static PersistenceManager persistenceManager;
	
	static public PersistenceManager getPersistenceManager() throws FileNotFoundException, IOException {
		if (persistenceManager==null) {
			Properties p = new Properties();
			ResourceBundle rb = ResourceBundle.getBundle(jdoResourceName);
			Enumeration<String> e = rb.getKeys();
			String key;
			while (e.hasMoreElements()){
				key=(String)e.nextElement();
				p.setProperty(key,rb.getString(key));
			}
			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(p);
			persistenceManager= pmf.getPersistenceManager();
		}
		return persistenceManager;
	}

	public void setPersistenceManager(PersistenceManager manager) {
		persistenceManager = manager;
	}

}

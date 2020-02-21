package fr.toyframework.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;

import fr.toyframework.Log;
import fr.toyframework.connector.JdbcConnect;

public class DefaultTask extends A_Task {
	private String bundleFilename = DefaultTask.class.getName();

	private static String KEY_ENABLE = "enable";
	private static String KEY_DELAY = "delay";
	private static String KEY_PERIOD = "period";
	private static String KEY_FIRST_TIME = "firstTime";

	private String firstTime_format = "yyyy-MM-dd_HH-mm-ss_SSS";

	private Long delay;
	private Long period; 
	private Date firstTime;

	public void run() {
		Log.logProcess.finer(getClass().getName()+".run()");
		Connection c=null;
		try {
			c = JdbcConnect.getConnection();
			// todo

			//
			JdbcConnect.closeConnection(c);
		} catch (Throwable t) {
			try{if (c!=null)JdbcConnect.closeConnection(c);}catch(SQLException e){e.printStackTrace();}
			t.printStackTrace();
		}
	}
	// Pour exemple d'utilisation / dev / test / debug
	public static void main(String[] args){
		try {
			DefaultTask s = new DefaultTask();
			s.run();
		} catch (Throwable t) {
			t.printStackTrace();
		}		
	}
	public boolean isEnable() {
		return new Boolean(ResourceBundle.getBundle(bundleFilename).getString(KEY_ENABLE)).booleanValue();
	}
	public long getDelay() {
		if (delay==null) delay = new Long(ResourceBundle.getBundle(bundleFilename).getString(KEY_DELAY));
		return delay.longValue();
	}
	public Date getFirstTime() {
		if (firstTime==null) {
			try {
				firstTime = new SimpleDateFormat(firstTime_format).parse(ResourceBundle.getBundle(bundleFilename).getString(KEY_FIRST_TIME));
			} catch (ParseException e) {
				Log.logProcess.log(Level.FINER,"task_scrutation_firstTime",e);
			}
		}
		return firstTime;
	}
	public long getPeriod() {
		if (period==null) period = new Long(ResourceBundle.getBundle(bundleFilename).getString(KEY_PERIOD));
		return period.longValue();
	}
}

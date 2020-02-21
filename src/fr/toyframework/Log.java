package fr.toyframework;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Log {
	
	private static final ResourceBundle bundle = ResourceBundle.getBundle(Log.class.getName());
	public	static final char	separator			= ';';
	public	static final String	eol					= "\n";
	public	static final int 	max_exception_depth = 3;

	public static final String KEY_loggername			= "loggername";
	public static final String KEY_logLevel				= "logLevel";
	public static final String KEY_logFile				= "logFile";
	public static final String KEY_logConsole			= "logConsole";
	public static final String KEY_logTimeStampFormat 	= "logTimeStampFormat";//yyyy-MM-dd_HH-mm-ss_SSS
	public static final String KEY_logFilename			= "logFilename";

	public static final String KEY_logProcessLevel		= "logProcessLevel";
	public static final String KEY_logActionLevel		= "logActionLevel";
	public static final String KEY_logConnectorLevel	= "logConnectorLevel";
	public static final String KEY_logControlerLevel	= "logControlerLevel";
	public static final String KEY_logMapperLevel		= "logMapperLevel";
	public static final String KEY_logModelLevel		= "logModelLevel";
	public static final String KEY_logUtilLevel			= "logUtilLevel";
	public static final String KEY_logPerfLevel			= "logPerfLevel";
	
	public static final String KEY_logFilenameSuffixSystemPropery	= "framework.logFileNameSuffix";
	
	public static Logger logProcess;
	public static Logger logAction;
	public static Logger logConnector;
	public static Logger logControler;
	public static Logger logMapper;
	public static Logger logModel;
	public static Logger logUtil;
	public static Logger logPerf;
	
	public static final String logProcessName		="process";
	public static final String logActionName		="action";
	public static final String logConnectorName		="connector";
	public static final String logControlerName		="controler";
	public static final String logMapperName		="mapper";
	public static final String logModelName			="model";
	public static final String logUtilName			="util";
	public static final String logPerfName			="perf";
	
	private	static boolean			initDone = false;
	private	static FileHandler		fileHandler;
    private static StdoutConsoleHandler	sysoutHandler;
    private static ConsoleHandler	syserrHandler;
	private	static String			loggerName;
	private	static boolean			logFile;
	private	static boolean			logConsole;
	private	static String			logFileName;
    private	static SimpleDateFormat	simpleDateFormat;
    private	static Formatter		logFormatter;
    private static String			runtimeName;
    
	public static Logger getInstance(String name,Level level,boolean logFile, boolean logConsole) throws SecurityException, IOException{
	    Logger logger = Logger.getLogger(name);
	    logger.setUseParentHandlers(false);
	    logger.setLevel(level);
	    logger.setFilter(null);
	    for (Handler h : logger.getHandlers()) logger.removeHandler(h);
    	if (logFile) logger.addHandler(getFileHandler(level));
    	if (logConsole) {
    		logger.addHandler(getSysoutHandler(level));
    		logger.addHandler(getSyserrHandler(level));
    	}
		return logger;
	}
	
	public static void init() throws SecurityException, IOException{
		init (
			loggerName	=	bundle.getString(KEY_loggername),
			new Boolean(bundle.getString(KEY_logConsole)),
			new Boolean(bundle.getString(KEY_logFile)),
			replaceSystemVars(bundle.getString(KEY_logFilename)),
			Level.parse(bundle.getString(KEY_logProcessLevel)),
			Level.parse(bundle.getString(KEY_logActionLevel)),
			Level.parse(bundle.getString(KEY_logControlerLevel)),
			Level.parse(bundle.getString(KEY_logMapperLevel)),
			Level.parse(bundle.getString(KEY_logModelLevel)),
			Level.parse(bundle.getString(KEY_logUtilLevel)),
			Level.parse(bundle.getString(KEY_logPerfLevel)),
			Level.parse(bundle.getString(KEY_logConnectorLevel))
		);
	}
	
	public static void init(String loggerName,Level l) throws SecurityException, IllegalArgumentException, IOException {
		init (
			loggerName,
			true,
			false,
			null,
			l,l,l,l,l,l,l,l
		);
	}

	public static void init(
			String loggerName,
			Boolean logConsole,
			Boolean logFile,
			String logFilename,
			Level processLevel,
			Level actionLevel,
			Level controlerLevel,
			Level mapperLevel,
			Level modelLevel,
			Level utilLevel,
			Level perfLevel,
			Level connectorLevel
		) throws SecurityException, IllegalArgumentException, IOException {

		Log.loggerName = loggerName;
		Log.logConsole	=	logConsole.booleanValue();
		Log.logFile	=	logFile.booleanValue();
		Log.logFileName = logFilename; 
		
		logProcess = getInstance(loggerName+"."+logProcessName,processLevel,logFile,logConsole) ;
		logAction = getInstance(loggerName+"."+logActionName,actionLevel,logFile,logConsole) ;
		logControler = getInstance(loggerName+"."+logControlerName,controlerLevel,logFile,logConsole) ;
		logMapper = getInstance(loggerName+"."+logMapperName,mapperLevel,logFile,logConsole) ;
		logModel = getInstance(loggerName+"."+logModelName,modelLevel,logFile,logConsole) ;
		logUtil = getInstance(loggerName+"."+logUtilName,utilLevel,logFile,logConsole) ;
		logPerf = getInstance(loggerName+"."+logPerfName,perfLevel,logFile,logConsole);	
		logConnector = getInstance(loggerName+"."+logConnectorName,connectorLevel,logFile,logConsole);

		simpleDateFormat= 	new SimpleDateFormat(bundle.getString(KEY_logTimeStampFormat));
		initDone		=	true;
	}

	/**
	 * Remplace "${maVariable}" dans la chaine s par la propriete systeme "maVariable" donnee via l'option JVM -D
	 * @param s
	 * @return
	 */
	private static String replaceSystemVars(String s) {
		Matcher m = Pattern.compile("\\$\\{(.*?)\\}").matcher(s);
		StringBuffer b = new StringBuffer(s.length());
		String sysVarName,sysVarValue;
		while (m.find()) {
			sysVarName = m.group(1);
			sysVarValue = System.getProperty(sysVarName, "");
			m.appendReplacement(b, sysVarValue);
		}
		m.appendTail(b);
		return b.toString();
	}
	
	private static void initFileHandler(Level level) throws SecurityException, IOException{
		fileHandler = new FileHandler(logFileName);
		fileHandler.setLevel		(level);
		fileHandler.setFilter		(null);
		fileHandler.setFormatter	(getLogFormatter());
	}
	
	private static void initSysErrConsoleHandler(Level level) throws SecurityException{
		syserrHandler = new ConsoleHandler();
		syserrHandler.setLevel(level);
		syserrHandler.setFormatter(getLogFormatter());
		syserrHandler.setFilter(new Filter() {
			public boolean isLoggable(LogRecord record) {
				return record.getLevel().equals(Level.WARNING) || record.getLevel().equals(Level.SEVERE);
			}
		});
	}

	private static void initSysOutConsoleHandler(Level level) throws SecurityException{
		sysoutHandler = new StdoutConsoleHandler();
		sysoutHandler.setLevel(level);
		sysoutHandler.setFilter(null);
		sysoutHandler.setFormatter(getLogFormatter());
	}
	
	private static StdoutConsoleHandler getSysoutHandler(Level level) { if (sysoutHandler==null) initSysOutConsoleHandler(level); return  sysoutHandler; }
	private static ConsoleHandler getSyserrHandler(Level level) { if (syserrHandler==null) initSysErrConsoleHandler(level); return syserrHandler; }
	private static FileHandler getFileHandler(Level level) throws SecurityException, IOException { if (fileHandler==null) initFileHandler(level); return fileHandler; }
	private static Formatter getLogFormatter() { if (logFormatter==null) logFormatter = new LogFormatter() ; return logFormatter; }
	
	public static void setLogProcess	(Logger l)	{	logProcess = l;		}
	public static void setLogAction		(Logger l) 	{	logAction = l;		}
	public static void setLogConnector	(Logger l)	{	logConnector = l;	}
	public static void setLogControler	(Logger l)	{	logControler = l;	}
	public static void setLogMapper		(Logger l)	{	logMapper = l;		}
	public static void setLogModel		(Logger l)	{	logModel = l;		}
	public static void setLogUtil		(Logger l)	{	logUtil = l;		}
	public static void setLogPerf		(Logger l)	{	logPerf = l;		}
	
	public static String getLoggerName() {	return loggerName;	}
	public static void setLoggerName	(String l)	{	loggerName = l;		}
	
	public static boolean isLogFile() {		return logFile;		}
	public static void setLogFile(boolean logFile) {		Log.logFile = logFile;			}
	
	public static boolean isLogConsole() {	return logConsole;	}
	public static void setLogConsole(boolean logConsole) {	Log.logConsole = logConsole;	}

	public static void setLogFileName(String logFileName) {	Log.logFileName = logFileName;	}
	
	public static SimpleDateFormat getSimpleDateFormat() {	
		if (simpleDateFormat==null)	simpleDateFormat = new SimpleDateFormat(bundle.getString(KEY_logTimeStampFormat));
		return simpleDateFormat;
	}
	public static void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		Log.simpleDateFormat = simpleDateFormat;
	}

	public static boolean isInitDone() {	return initDone;	}

	private static class StdoutConsoleHandler extends StreamHandler {
	    public StdoutConsoleHandler() {
	    	setOutputStream(System.out);
	    }
	    public void publish(LogRecord record) {
			super.publish(record);	
			flush();
	    }
	}
	
	/**
	 *  Formatte les chaines de charact�res pour supporter la variabilisation.
	 *  Exemple: Log.log(Level.INFO, "Le fichier {0} est maintenant � l'�tat {1}.", new Object[]{ file, state });
	 */
	private static class LogFormatter extends Formatter {
		
		public String format(LogRecord logRecord) {
			final StringBuilder log = new StringBuilder();
			final String timestamp = getSimpleDateFormat().format(new Date(logRecord.getMillis()));
			final String formattedMessage = MessageFormat.format(logRecord.getMessage(), logRecord.getParameters());
			log
				.append(timestamp)						.append(separator)	// Date timestamp
				.append(runtimeName)					.append(separator)	// JVM identification
				.append(logRecord.getLoggerName())		.append(separator)	// Nom du logger
				.append(logRecord.getLevel())			.append(separator)	// Niveau de log
				.append(logRecord.getSourceClassName()) .append(separator)	// Classe source
				.append(logRecord.getSourceMethodName()).append(separator)	// M�thode source
				.append(formattedMessage)				.append(eol);		// Message formatt�
			
	    	Throwable throwable = logRecord.getThrown();
	    	if(throwable != null) {
	    		for (int depth=0; depth<max_exception_depth; depth++) {
	    			if(throwable != null) {
	    				log.append(toStackTrace(throwable));
	    				throwable = throwable.getCause();
	    			}
		    	}
	    	}
	    	return log.toString();
		}

		private String toStackTrace(Throwable t) {
    		StringWriter sw = new StringWriter();
    		PrintWriter p = new PrintWriter(sw);
    		t.printStackTrace(p);
    		p.flush();
    		return sw.getBuffer().toString();
		}

	}
	
}

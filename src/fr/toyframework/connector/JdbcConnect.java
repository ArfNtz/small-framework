package fr.toyframework.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import fr.toyframework.Log;


public class JdbcConnect {

	static private String bundleFilename 			= JdbcConnect.class.getName();
//	static private String KEY_useDataSource 		= "useDataSource";
	static private String KEY_createDataSource 		= "createDataSource";
	static private String KEY_dataSourceName 		= "dataSourceName";
	static private String KEY_initialContextFactory = "initialContextFactory";
	static private String KEY_providerUrl 			= "providerUrl";
	static private String KEY_dbUrl 				= "dbUrl";
	static private String KEY_driverClass 			= "driverClass";
	static private String KEY_id 					= "id";
	static private String KEY_pwd 					= "pwd";
	static private String KEY_driverProperties 		= "driverProperties";
	static private String KEY_catalogue 			= "catalogue"; 	// SH - 03/10/2014 - 802
	static private String KEY_schema 				= "schema"; 	// SH - 03/10/2014 - 802

	static private boolean createDataSource = Boolean.parseBoolean(ResourceBundle.getBundle(bundleFilename).getString(KEY_createDataSource));
//	static private boolean useDataSource = Boolean.parseBoolean(ResourceBundle.getBundle(bundleFilename).getString(KEY_useDataSource));
	
	static private DataSource dataSource;

	static private String initialContextFactory;
	static private String providerUrl;
	static private String dataSourceName;

	static private String dbUrl;
	static private String id;
	static private String pwd;
	static private String driverClass;
	static private String driverProperties;
	static private String catalogue ;
	static private String schema ;

	static public Connection getConnection() throws SQLException, NamingException, ClassNotFoundException  {
		Log.logConnector.finer("");
		return getDataSource().getConnection();
	}
	static public void closeConnection(Connection c) throws SQLException  {
		Log.logConnector.finer("");
		c.close();
	}
	static public DataSource getDataSource() throws NamingException {
		if (dataSource==null) {
			String _s = "";
			try {
				_s = "namingUrl:"+getProviderUrl()+",namingContext:"+getInitialContextFactory()+",dataSourceName:"+getDataSourceName();
				Log.logConnector.finer(_s);
				//java.util.Hashtable parms = new java.util.Hashtable();
				//parms.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,getInitialContextFactory());
				//parms.put(javax.naming.Context.PROVIDER_URL, getProviderUrl());
				InitialContext context = new InitialContext();
				dataSource = (javax.sql.DataSource) context.lookup(getDataSourceName());
			} catch (NamingException e) {
				Log.logConnector.log(Level.FINER,_s,e);
				if (createDataSource) dataSource = createDataSource();
				else throw e;
			}
		}
		return dataSource;
	}
	static public DataSource createDataSource() {
		Log.logConnector.finer("driverClass:"+getDriverClass());
		Log.logConnector.finer("dbUrl:"+getDbUrl()+getDriverProperties()+",id:"+getId()+",pwd:"+getPwd()+"')");
		BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(getDriverClass());
        ds.setUsername(getId());
        ds.setPassword(getPwd());
        ds.setUrl(getDbUrl()+getDriverProperties());
		Log.logConnector.finer("Active:"+ds.getNumActive()+"/"+ds.getMaxActive()+";Idle:"+ds.getMaxIdle()+"/"+ds.getNumIdle());
        return ds;
	}
	static public boolean isAuthorized(String id, String pwd,String driverClass,String dbUrl) throws SQLException, ClassNotFoundException {
		Class.forName(driverClass);
		Connection c = DriverManager.getConnection(dbUrl,id,pwd);
		if (c==null) return false;
		else { c.close(); c=null; return true; }
	}
	static public String getDataSourceName() {
		if (dataSourceName==null) dataSourceName  = ResourceBundle.getBundle(bundleFilename).getString(KEY_dataSourceName);
		return dataSourceName;
	}
	static public void setDataSourceName(String newDataSourceName) {
		dataSourceName = newDataSourceName;
	}
	static public String getInitialContextFactory() {
		if (initialContextFactory==null) initialContextFactory = ResourceBundle.getBundle(bundleFilename).getString(KEY_initialContextFactory);
		return initialContextFactory;
	}
	static public void setInitialContextFactory(String newInitialContextFactory) {
		initialContextFactory = newInitialContextFactory;
	}
	static public String getProviderUrl() {
		if (providerUrl==null) providerUrl  = ResourceBundle.getBundle(bundleFilename).getString(KEY_providerUrl);
		return providerUrl;
	}
	static public void setProviderUrl(String newProviderUrl) {
		providerUrl = newProviderUrl;
	}
	static public String getDbUrl() {
		if (dbUrl==null) dbUrl = ResourceBundle.getBundle(bundleFilename).getString(KEY_dbUrl);
		return dbUrl;
	}
	static private String getDriverClass() {
		if (driverClass==null) driverClass = ResourceBundle.getBundle(bundleFilename).getString(KEY_driverClass);
		return driverClass;
	}
	static private String getId() {
		if (id==null) id = ResourceBundle.getBundle(bundleFilename).getString(KEY_id);
		return id;
	}
	static private String getPwd() {
		if (pwd==null) pwd = ResourceBundle.getBundle(bundleFilename).getString(KEY_pwd);
		return pwd;
	}
	public static String getDriverProperties() {
		if (driverProperties==null) driverProperties  = ResourceBundle.getBundle(bundleFilename).getString(KEY_driverProperties);
		return driverProperties;
	}
	static public void setDbUrl(String dbUrl) {
		JdbcConnect.dbUrl = dbUrl;
	}
	static public void setDriverClass(String driverClass) {
		JdbcConnect.driverClass = driverClass;
	}
	static public void setDriverProperties(String driverProperties) {
		JdbcConnect.driverProperties = driverProperties;
	}
	static public String getCatalogue() {
		if (catalogue==null) catalogue  = ResourceBundle.getBundle(bundleFilename).getString(KEY_catalogue);
		return catalogue;
	}
	public static void setCatalogue(String catalogue) {
		JdbcConnect.catalogue = catalogue;
	}
	public static String getSchema() {
		if (schema==null) schema  = ResourceBundle.getBundle(bundleFilename).getString(KEY_schema);
		return schema;
	}
	public static void setSchema(String schema) {
		JdbcConnect.schema = schema;
	}
	public static void setDataSource(DataSource dataSource) {
		JdbcConnect.dataSource = dataSource;
	}

}

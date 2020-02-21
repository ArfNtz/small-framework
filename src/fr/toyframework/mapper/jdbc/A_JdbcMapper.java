package fr.toyframework.mapper.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import fr.toyframework.connector.JdbcConnect;
import fr.toyframework.mapper.A_Mapper;
import fr.toyframework.mapper.MapperException;

public abstract class A_JdbcMapper extends A_Mapper {
	
	private Connection c;
	private DatabaseMetaData metaData;
	private String catalogName;
	private String schemaName;
	protected JdbcCounter jdbcCounter;
	protected Map<String,String> counterMap;

	public abstract boolean existStorage(Object m) throws MapperException;
	public abstract void call(Object pm) throws MapperException;
	public abstract void create(Object pm) throws MapperException;
	public abstract void initCounters(Object pm) throws MapperException;
	public abstract void drop(Object pm) throws MapperException;
	public abstract Object insert(Object pm) throws MapperException;
	public abstract Object select(Object pm) throws MapperException;
	public abstract void selectAll(List<?> l, Object o) throws MapperException;
	public abstract void update(Object pm) throws MapperException;
	public abstract void delete(Object pm) throws MapperException;
	public abstract Object insertOrUpdate(Object m) throws MapperException;
	public abstract Object insertIfNotExist(Object m) throws MapperException;

	protected abstract JdbcCounter createCounter() throws MapperException;

	public void open() throws MapperException {
		try {
			open(JdbcConnect.getConnection(),JdbcConnect.getCatalogue(),JdbcConnect.getSchema());
		} catch (SQLException e) {
			throw new MapperException(e,null,null);
		} catch (NamingException e) {
			throw new MapperException(e,null,null);
		} catch (ClassNotFoundException e) {
			throw new MapperException(e,null,null);
		}
	}
	public void open(Connection c, String catalogue, String schema) throws MapperException {
		this.c = c; this.catalogName = catalogue; this.schemaName = schema;
		jdbcCounter = createCounter();
	}

	public void close() throws MapperException {
		try {
			if (c!=null) JdbcConnect.closeConnection(c);
		} catch (SQLException e) {
			throw new MapperException(e,null,null);
		}
	}
	public void commit() throws MapperException {
		try {
			if (c!=null&&!c.getAutoCommit()) c.commit();
		} catch (SQLException e) {
			throw new MapperException(e,null,null);
		}
	}
	public void rollback() throws MapperException {
		try {
			if (c!=null&&!c.getAutoCommit()) c.rollback();
		} catch (SQLException e) {
			throw new MapperException(e,null,null);
		}
	}

	public JdbcCounter getJdbcCounter() {
		return jdbcCounter;
	}
	public Map<String, String> getCounterMap() {
		if (counterMap==null) counterMap = new HashMap<String,String>();
		return counterMap;
	}
	protected final void addCounterMap(String tableName, String fieldName, String counterName) {
		getCounterMap().put(tableName+"."+fieldName, counterName);
	}
	protected final String getCounterMap(String tableName, String fieldName){
		String counterName = getCounterMap().get(tableName+"."+fieldName); 
		return counterName==null?tableName+"."+fieldName:counterName;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public DatabaseMetaData getMetaData() throws SQLException {
		return getC().getMetaData();
	}
	public final void setC(Connection c) {this.c = c;}
	public final Connection getC() {return c;}


}

package fr.toyframework.mapper.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import fr.toyframework.mapper.MapperException;

public class JdbcCounter {

	private String bundleFilename = getClass().getName();

	private static String TABLE = "table";
	private static String FIELD = "field";
	private static String OBJECT_FIELD = "objectField";
	private static String ERROR_COUNTER_NOT_FOUND = "error.counterNotFound";
	
	private String table = ResourceBundle.getBundle(bundleFilename).getString(TABLE);
	private String field = ResourceBundle.getBundle(bundleFilename).getString(FIELD);
	private String objectField = ResourceBundle.getBundle(bundleFilename).getString(OBJECT_FIELD);

	private String getSelectStatement() {
		return "SELECT "+field+ " FROM "+table+" WHERE "+objectField+"=?";
	}
	private String getUpdateStatement() {
		return "UPDATE "+table+" SET "+field+" = ? WHERE "+objectField+"=?";
	}
	private String getInsertStatement() {
		return "INSERT INTO "+table+"("+objectField+","+field+") VALUES (?,?)";
	}

	public void init(Connection c,String name) throws MapperException {
		try {
			PreparedStatement ps;
			ps = JdbcUtils.execute(c, getInsertStatement(), new Object[]{name,0});
			ps.close();
		} catch (SQLException e) {
			throw new MapperException(e,null,null);
		}
	}
	public Long increment(Connection c,String name) throws MapperException {
		Long l = null;
		try {
			PreparedStatement ps = JdbcUtils.execute(c, getSelectStatement(), new Object[]{name});
			ResultSet rs = ps.getResultSet();
			if (rs.next()) l = rs.getLong(1);
			else throw new SQLException(MessageFormat.format(ResourceBundle.getBundle(bundleFilename).getString(ERROR_COUNTER_NOT_FOUND),new Object[]{name}));
			rs.close();ps.close();
			l++;
			ps = JdbcUtils.execute(c,getUpdateStatement(),new Object[]{l,name});
			ps.close();
			} catch (SQLException e) {
				throw new MapperException(e,null,null);
			}
		return l;
	}
	public String getTable() {
		return table;
	}

}

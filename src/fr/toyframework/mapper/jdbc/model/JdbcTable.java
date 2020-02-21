package fr.toyframework.mapper.jdbc.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import fr.toyframework.Log;
import fr.toyframework.mapper.jdbc.JdbcUtils;

public class JdbcTable implements Serializable {

	private static final long serialVersionUID = -6470344513521573435L;

	String tableName;
	String schemaName;
	String catalogName;
	String D = "."; //separator, delimiter

	String dropStatement;
	String createStatement;
	String deleteStatement;
	String insertStatement;
	String selectStatement;
	String updateStatement;
	String primaryKeyStatement;
	List<Object> insertData;
	
	// donn�es renseign�es via les m�tadata
	boolean hasReadMetadata=false;
	boolean hasReadColumnDefinitions=false;
	boolean hasReadPrimaryKeys=false;
	boolean hasReadIndexes=false;
	List<JdbcColumnDefinition> columnDefinitions;
	Map<String,String> primaryKeys; 
	List<JdbcIndex> indexes;
	
//	public JdbcTable(){}
	public JdbcTable(String tableName,String schemaName,String catalogName){this.tableName=tableName;this.schemaName=schemaName;this.catalogName=catalogName;}

	public String getInsertStatement(List<String> cols) {
		insertStatement = "INSERT INTO "+getFullTableName()+" (";
		if (cols==null) { 
			cols = new ArrayList<String>();
			for (JdbcColumnDefinition jcd : getColumnDefinitions())	cols.add(jcd.getColumnName());
		}
		Iterator<String> i_cols = cols.iterator();
		if (i_cols.hasNext()) {
			insertStatement += "\""+i_cols.next()+"\"";
			while (i_cols.hasNext()) {
				insertStatement += ",\""+i_cols.next()+"\"";
			}
		} 
		insertStatement += ") VALUES (";
		for (int i=0;i<cols.size()-1;i++) insertStatement+="?,";
		insertStatement+="?)";			
		return insertStatement;
	}
	public String getCreateStatement(List<String> fields,List<String> types,List<String> keys,List<String> notNulls) {
		createStatement = "CREATE TABLE "+getFullTableName()+" (";
		if (fields==null) {
			Iterator<JdbcColumnDefinition> i = getColumnDefinitions().iterator();
			if (i.hasNext()) {
				createStatement+=((JdbcColumnDefinition)i.next()).toSqlString();
				while (i.hasNext()) {
					createStatement+=","+((JdbcColumnDefinition)i.next()).toSqlString();
				}
			}
			String primaryKeys = getPrimaryKeyStatement();
			if (primaryKeys!=null&&!primaryKeys.trim().equals("")) createStatement += ", PRIMARY KEY("+primaryKeys+")"; 
		} else {
			Iterator<String> i_fields = fields.iterator();
			int i=0;
			String field;
			while (i_fields.hasNext()) {
				field = i_fields.next();
				createStatement += field+" "+types.get(i);
				if (notNulls!=null&&notNulls.contains(field)) createStatement += " NOT NULL ";
				i++;
				if (i_fields.hasNext()) createStatement+=",";
			}
			Iterator<String> i_keys = keys.iterator();
			if (i_keys.hasNext()) {
				createStatement+=",PRIMARY KEY ("+i_keys.next();
				while (i_keys.hasNext()) createStatement+=","+i_keys.next();
				createStatement+=")";
			}
		}
		createStatement+=")";
		return createStatement;
	}
	
	public String getSelectStatement(List<String> selectedColumns,Map<String,String> aliases, List<JdbcFilter> filters) {
		selectStatement = "SELECT "+JdbcFilter.toStringGlobalOperators(filters);
		if(selectedColumns == null)	{
			selectedColumns = new ArrayList<String>();
			for (JdbcColumnDefinition jcd : getColumnDefinitions()) selectedColumns.add(jcd.getColumnName());
		} else {
			Iterator<String> i_cols = selectedColumns.iterator();
			String fieldName;
			if (i_cols.hasNext()) {
				fieldName = i_cols.next();
				selectStatement += JdbcFilter.toString(fieldName,filters);
				if (aliases.containsKey(fieldName)) this.selectStatement+=" "+aliases.get(fieldName);
				while (i_cols.hasNext()) {
					fieldName = i_cols.next();
					selectStatement += ","+JdbcFilter.toString(fieldName,filters);
					if (aliases.containsKey(fieldName)) this.selectStatement+=" "+aliases.get(fieldName);
				}
			} else selectStatement+=JdbcFilter.toString("*",filters);
		}
		selectStatement += " FROM "+getFullTableName();
		selectStatement += JdbcFilter.toString(filters);
		return selectStatement;
	}
	public String getUpdateStatement(List<String> fields,List<JdbcFilter> filters) {
		updateStatement = "UPDATE "+getFullTableName()+" SET ";
		Iterator<String> i_ = fields.iterator();
		if (i_.hasNext()) {
			updateStatement += i_.next()+"=?";
			while (i_.hasNext()) updateStatement += ","+i_.next()+"=?";
		}
		updateStatement+=JdbcFilter.toString(filters);
		return updateStatement;
	}
	public String getPrimaryKeyStatement(){
		Map<String,String> keys = getPrimaryKeys();
		String primaryKeys = "";
		if (keys.size()>0) primaryKeys = ""+keys.get(""+1);
		for (int i=2;i<=keys.size();i++) primaryKeys += ","+keys.get(""+i);
		return primaryKeys;
	}
	public void setPrimaryKeyStatement(String primaryKeyStatement) {
		this.primaryKeyStatement = primaryKeyStatement;
	}
	public void setInsertData(List<Object> l) {
		insertData = l;
	}
	public List<JdbcColumnDefinition> getColumnDefinitions() {
		if (columnDefinitions==null) columnDefinitions = new ArrayList<JdbcColumnDefinition>();
		return columnDefinitions;
	}
	public void setColumnDefinitions(List<JdbcColumnDefinition> l) {
		columnDefinitions = l;
	}
	public String getDropStatement() {
		dropStatement = "DROP TABLE "+getFullTableName();
		return dropStatement;
	}
	public void setDropStatement(String string) {
		dropStatement = string;
	}
	public String getDeleteStatement(List<JdbcFilter> listFilters) {
		deleteStatement = "DELETE FROM "+getFullTableName();
		deleteStatement += JdbcFilter.toString(listFilters);
		return deleteStatement;
	}
	public void setDeleteStatement(String string) {
		deleteStatement = string;
	}
	public Map<String, String> getPrimaryKeys() {
		return primaryKeys;
	}
	public void setPrimaryKeys(Map<String, String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}
	public List<JdbcIndex> getIndexes() {
		if (indexes==null) indexes = new ArrayList<JdbcIndex>();
		return indexes;
	}
	public void setIndexes(List<JdbcIndex> l) {
		indexes=l;
	}
	public void setValue(int columnNumber,String oldValue, String newValue) {
		Iterator<Object> i = getInsertData().iterator();
		Object[] args;
		String value;
		while (i.hasNext()){
			args = (Object[])i.next();
			value = (String)args[columnNumber-1];
			if (value.trim().equals(oldValue.trim())) args[columnNumber-1] = newValue.trim();
		}
	}
	public void setValue(int columnNumber,Map<String,Object> oldKeysNewValues) {
		Iterator<Object> i = getInsertData().iterator();
		Object[] args;
		String oldValue,newValue;
		while (i.hasNext()){
			args = (Object[])i.next();
			oldValue = (String)args[columnNumber-1];
			if (oldValue!=null)oldValue=oldValue.trim();
			newValue = (String)oldKeysNewValues.get(oldValue);
			if (newValue!=null) args[columnNumber-1] = newValue.trim();
		}
	}
	public void setColumnDefinition(int columnNumber,JdbcColumnDefinition columnDefinition){
		getColumnDefinitions().set(columnNumber-1,columnDefinition);
	}
	private JdbcColumnDefinition getColumnDefinition(ResultSet rs) throws SQLException{
		JdbcColumnDefinition cd = new JdbcColumnDefinition();
		cd.setColumnName(rs.getString("COLUMN_NAME"));
		cd.setColumnRemarks(rs.getString("REMARKS"));
		cd.setColumnType(rs.getInt("DATA_TYPE"));
		cd.setColumnSize(rs.getInt("COLUMN_SIZE"));
		cd.setColumnDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
		cd.setNullable(rs.getString("IS_NULLABLE").trim().equals("YES"));
		return cd;
	}
	public void readMetadata(Connection c) throws SQLException, NamingException, ClassNotFoundException{
		DatabaseMetaData dmd = c.getMetaData();
		readColumnDefinitions(dmd);
		readPrimaryKeys(dmd);
		readIndexes(dmd);
		hasReadMetadata=true;
	}
	public void readColumnDefinitions(DatabaseMetaData dmd) throws SQLException, NamingException, ClassNotFoundException{
		columnDefinitions = null;
		ResultSet rs = dmd.getColumns(getCatalogName(),getSchemaName(),getTableName(),"%");
		int i=0;
		while (rs.next()) { 
			getColumnDefinitions().add(getColumnDefinition(rs));
			i++;
		}
		Log.logConnector.finest("num cols:"+i);
		rs.close();
		hasReadColumnDefinitions=true;
	}
	public void readPrimaryKeys(DatabaseMetaData dmd) throws SQLException, NamingException, ClassNotFoundException{
		primaryKeys = null;
		ResultSet rs = dmd.getPrimaryKeys(getCatalogName(),getSchemaName(),getTableName());
		setPrimaryKeys(getPrimaryKeys(rs));
		rs.close();
		hasReadPrimaryKeys=true;
	}
	public void readIndexes(DatabaseMetaData dmd) throws SQLException, NamingException, ClassNotFoundException{
		indexes = null;
		ResultSet rs = dmd.getIndexInfo(getCatalogName(),getSchemaName(),getTableName(),false,false);
		JdbcIndex index;
		String primaryKeys = getPrimaryKeyStatement();
		while (rs.next()) {
			index = getIndex(rs);
			if (
				index.getQualifierName()!=null&&index.getIndexName()!=null&&
				index.getQualifierName().trim().equals(getSchemaName())&&
				index.getIndexName().trim().equals(getTableName())
			) {
				if (primaryKeys==null||primaryKeys.trim().equals("")) primaryKeys += index.getColumnName();
				else if (primaryKeys.indexOf(index.getColumnName())==-1)primaryKeys += ","+index.getColumnName();
			} else {
				getIndexes().add(getIndex(rs));
			}
		}
		rs.close();
		setPrimaryKeyStatement(primaryKeys);
		hasReadIndexes=true;
	}
	public void readData(Connection c,int nbMaxRows) throws SQLException {
		String sql = "SELECT * FROM "+getFullTableName();
		PreparedStatement ps = JdbcUtils.execute(c,sql,null);
		ResultSet rs = ps.getResultSet();
		insertData = null;
		int r=0;
		while (rs.next()&&r<nbMaxRows) {
			Object[] args = new Object[rs.getMetaData().getColumnCount()];
			for (int i=0;i<rs.getMetaData().getColumnCount();i++) args[i]=rs.getObject(i+1);
			getInsertData().add(args);
			r++;
		}
		rs.close();
		ps.close();
	}
	public void readFlat(String row,Connection c) throws SQLException, NamingException, ClassNotFoundException {
		if (row.length()>=10) {
			setTableName(row.substring(0,10));
			readMetadata(c);
			int i=0, p1=0, l=0,p2=0;
			String[] args = new String[getColumnDefinitions().size()];
			for (JdbcColumnDefinition cd : getColumnDefinitions()) {
				l = cd.getColumnSize();
				p2 = p1 + l;
				if (p1<row.length()) {
					if (p2<row.length()) {
						args[i]=row.substring(p1,p2);
					}
					else args[i]=row.substring(p1);
				}
				p1 = p2;
				i++;
			}
			getInsertData().add(args);
		}
	}
	private Map<String,String> getPrimaryKeys(ResultSet rs) throws SQLException{
		/*
		TABLE_CAT;TABLE_SCHEM;TABLE_NAME;COLUMN_NAME;KEY_SEQ;PK_NAME;
		*/
		Map<String,String> keys = new HashMap<String, String>();
		while (rs.next()){
			keys.put(
				rs.getString("KEY_SEQ"),
				rs.getString("COLUMN_NAME")
			);
		}
		return keys;
	}
	private JdbcIndex getIndex(ResultSet rs) throws SQLException{
		JdbcIndex index = new JdbcIndex();
		//String TABLE_CAT = rs.getString("TABLE_CAT");
		//String TABLE_SCHEM = rs.getString("TABLE_SCHEM");
		//String TABLE_NAME = rs.getString("TABLE_NAME");
		index.setQualifierName(rs.getString("INDEX_QUALIFIER"));
		index.setIndexName(rs.getString("INDEX_NAME"));
		index.setType(rs.getInt("TYPE"));
		index.setNonUnique(rs.getBoolean("NON_UNIQUE"));
		index.setColumnName(rs.getString("COLUMN_NAME")); 
		return index;
	}
	public String getCreateIndexStatement(JdbcIndex index){
		String sql = "";
		if (index.getType()==Types.DECIMAL) {
			if (index.isNonUnique()) {
				sql = "CREATE UNIQUE INDEX ";
			} else {
				sql = "CREATE INDEX ";
			}
			sql+=getSchemaName()+D+getTableName()+" ON "+getCatalogName()+D+getSchemaName()+D+getTableName()+"("+index.getColumnName()+")";
		}
		return sql;
	}
	public String getFullTableName(){
		String tableName = "";
		if (getCatalogName()!=null&&!getCatalogName().trim().equals("")) tableName += getCatalogName()+D;
		if (getSchemaName()!=null&&!getSchemaName().trim().equals("")) tableName += getSchemaName()+D;
		if (getTableName()!=null&&!getTableName().trim().equals("")) tableName += getTableName();
		return tableName;
	}
	public String toFlatXmlString(boolean withData) {
		Iterator<Object> i_row = getInsertData().iterator();
		Object[] row;
		String xml = "<"+getTableName()+">\n";
		while (i_row.hasNext()) {
			row = (Object[])i_row.next();
			for (int i=0;i<row.length;i++) {
				xml+="\t"+((JdbcColumnDefinition)getColumnDefinitions().get(i)).toFlatXmlString(withData,row[i]);
			}
		}
		xml+="</"+getTableName()+">\n";
		return xml;
	}
	public String getTableName() {
		if (tableName!=null&&tableName.trim().equals("")) tableName=null;
		return tableName;
	}
	public void setTableName(String string) {
		tableName = string;
	}
	public String getSchemaName() {
		if (schemaName!=null&&schemaName.trim().equals("")) schemaName=null;
		return schemaName;
	}
	public void setSchemaName(String string) {
		schemaName = string;
	}
	public String getCatalogName() {
		if (catalogName!=null&&catalogName.trim().equals("")) catalogName=null;
		return catalogName;
	}
	public void setCatalogName(String string) {
		catalogName = string;
	}
	public List<Object> getInsertData() {
		if (insertData==null)insertData=new ArrayList<Object>();
		return insertData;
	}
	public boolean hasReadMetadata() {return hasReadMetadata;}
	public boolean hasReadColumnDefinitions() {return hasReadColumnDefinitions;}
	public boolean hasReadPrimaryKeys() {return hasReadPrimaryKeys;}
	public boolean hasReadIndexes() {return hasReadIndexes;}


}
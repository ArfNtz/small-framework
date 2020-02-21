package fr.toyframework.mapper.jdbc;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.naming.NamingException;

import fr.toyframework.mapper.BeanUtils;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.mapper.converter.Converters;
import fr.toyframework.mapper.jdbc.model.JdbcColumnDefinition;
import fr.toyframework.mapper.jdbc.model.JdbcFilter;
import fr.toyframework.mapper.jdbc.model.JdbcTable;
import fr.toyframework.model.A_Model;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.Counter;
import fr.toyframework.type.Operator;

public class DefaultJdbcMapper extends A_JdbcMapper {

	private Map<String,JdbcTable> tables;
	private Map<String,List<String>> fields;
	private Map<String,List<String>> keys;
	private Map<String,List<String>> notNulls;
	private Map<String,List<String>> counters;
	private List<JdbcFilter> filters;

	protected int nbMaxResult = DEFAULT_NB_MAX_RESULT;
	public static int DEFAULT_NB_MAX_RESULT = 500;

	protected JdbcCounter createCounter() throws MapperException {
		return new JdbcCounter();
	}
	private final Map<String,JdbcTable> getTables() {
		if (tables==null) tables = new LinkedHashMap<String,JdbcTable>();
		return tables;
	}
	private final Map<String, List<String>> getFields() {
		if (fields==null) fields = new LinkedHashMap<String, List<String>>();
		return fields;
	}
	private final Map<String, List<String>> getKeys() {
		if (keys==null) keys = new LinkedHashMap<String, List<String>>();
		return keys;
	}
	private final Map<String, List<String>> getNotNulls() {
		if (notNulls==null) notNulls = new LinkedHashMap<String, List<String>>();
		return notNulls;
	}
	public final Map<String, List<String>> getCounters() {
		if (counters==null) counters = new LinkedHashMap<String, List<String>>();
		return counters;
	}
	private final List<JdbcFilter> getFilters() {
		if (filters==null) filters = new ArrayList<JdbcFilter>();
		return filters;
	}

	public final void addTable(String tableName){
		getTables().put(tableName,new JdbcTable(tableName,getSchemaName(),getCatalogName()));
	}
	public final void renameTable(String oldTableName,String newTableName){
		JdbcTable table = getTables().get(oldTableName);
		table.setTableName(newTableName);
		getTables().remove(oldTableName);
		getTables().put(newTableName, table);
		List<String> fields = getFields().get(oldTableName);
		List<String> keys = getKeys().get(oldTableName);
		List<String> notNulls = getNotNulls().get(oldTableName);
		List<String> counters = getCounters().get(oldTableName);
		fields.remove(oldTableName);
		keys.remove(oldTableName);
		notNulls.remove(oldTableName);
		counters.remove(oldTableName);
		getFields().put(newTableName, fields);
		getKeys().put(newTableName, keys);
		getNotNulls().put(newTableName, notNulls);
		getCounters().put(newTableName, counters);
	}
	public final void setSchema(String schemaName) {
		for (JdbcTable table:getTables().values()) table.setSchemaName(schemaName);
	}
	public final void setFields(String tableName,List<String> modelFields){
		if (tableName==null) tableName = (String)getTables().keySet().toArray()[0];
		getFields().put(tableName,modelFields);
	}	
	public final void setKeys(String tableName,List<String> modelKeys){
		if (tableName==null) tableName = (String)getTables().keySet().toArray()[0];
		getKeys().put(tableName,modelKeys);
	}	
	public final void setNotNulls(String tableName,List<String> notNullModelFields){
		if (tableName==null) tableName = (String)getTables().keySet().toArray()[0];
		getNotNulls().put(tableName,notNullModelFields);
	}
	public final void setCounters(String tableName,List<String> counters){
		if (tableName==null) tableName = (String)getTables().keySet().toArray()[0];
		getCounters().put(tableName,counters);
	}
	public void addFilter(Operator logic_operator,Operator field_operator, Operator operator,String fieldName, String fieldName2, List<A_Type> values) {
		if (operator!=null&&fieldName!=null) {
			if (logic_operator!=null) filters.add(new JdbcFilter(logic_operator));
			if (fieldName2!=null) {
				filters.add(new JdbcFilter(operator,getMap(fieldName), getMap(fieldName2)));
			} else if (values!=null) {
				if (field_operator!=null) {
					filters.add(new JdbcFilter(operator,field_operator+"("+getMap(fieldName)+")", values));
				} else {
					filters.add(new JdbcFilter(operator,getMap(fieldName), values));
				}
			} else {
				filters.add(new JdbcFilter(operator,getMap(fieldName)));
			}
		}
	}
	public void addFilter(Operator logic_operator,Operator field_operator, Operator operator,String fieldName, A_Type oneValue) {
		addFilter(logic_operator,field_operator,operator,fieldName,null,Arrays.asList(oneValue));
	}
	public void addFilter(Operator logic_operator,Operator operator, String fieldName,String fieldName2) {
		addFilter(logic_operator,null,operator,fieldName,fieldName2,null);
	}
	public void addFilter(Operator logic_operator,Operator operator,String fieldName, A_Type oneValue) {
		addFilter(logic_operator,null,operator,fieldName,null,Arrays.asList(oneValue));
	}
	public void addFilter(Operator logic_operator,Operator operator, String fieldName) {
		addFilter(logic_operator,null,operator,fieldName,null,null);
	}
	public void addFilter(Operator operator, String fieldName, A_Type value) {
		addFilter(null,null,operator,fieldName,null,Arrays.asList(value));		
	}
	public void addFilter(Operator operator, String fieldName) {
		addFilter(null,null,operator,fieldName,null,null);
	}
	public void addFilter(List<Operator> operators) {
		for (Operator operator : operators) filters.add(new JdbcFilter(operator));
	}
	public void addAlias(String fieldName, String alias) {
		getReverseMap().put(fieldName, alias);
	}
	public final void reset() {
		getMap().clear();getReverseMap().clear();getTables().clear();getFields().clear();getKeys().clear();getFilters().clear();getNotNulls().clear();getCounters().clear();setMaxNbResult(DEFAULT_NB_MAX_RESULT);
	}
	public final void resetFilters() {
		getFilters().clear();
	}
	public final void setMaxNbResult(int i){
		nbMaxResult = i;
	}	

	public void init(A_Model m) throws MapperException {
		reset();
		Class<?> modelClass = m.getClass();
		String tableName = modelClass.getSimpleName().toUpperCase();
		addTable(tableName);
		setFields(tableName,BeanUtils.getProperties(modelClass,A_Model.class,A_Type.class));
		setKeys(tableName, BeanUtils.getProperties(modelClass,A_Model.class,A_Type.class,"isDbKey",m));
		setNotNulls(tableName, BeanUtils.getProperties(modelClass,A_Model.class,A_Type.class,"isDbNotNull",m));
		setCounters(tableName, BeanUtils.getProperties(modelClass,A_Model.class,Counter.class));
	}
	public A_Model map(A_Model m) throws MapperException {
		return m;
	}
	public void call(A_Model m) throws MapperException {
		/* TODO call procedure */
	}
	public boolean existStorage(A_Model m) throws MapperException {
		boolean r = false;
		try {
			init(m);
			for (String tableName : getTables().keySet()) {
				ResultSet rs = getMetaData().getTables(null,null,tableName,null);
				r = rs.next(); 
				rs.close();
				if (!r) return r;
			}
		} catch (SQLException e) {
			throw new MapperException(e,null,null);
		}
		return r;
	}
	public void create(A_Model m) throws MapperException {
		try {
			JdbcTable table;
			List<String> modelFields = null;
			List<String> dbFields = null;
			List<String> dbKeys = null;
			List<String> sqlTypes = null;
			List<String> notNulls = null;
			for (String tableName : getTables().keySet()) {
				table = getTables().get(tableName);
				readColumnDefinitions(table);
				modelFields = getFields().get(tableName);
				sqlTypes = getSqlTypes(m,modelFields);
				dbFields = getMap(modelFields);
				dbKeys = getMap(getKeys().get(tableName));
				notNulls = getNotNulls().get(tableName);
				JdbcUtils.exec(getC(),table.getCreateStatement(dbFields,sqlTypes,dbKeys,notNulls),null);
			}
		} catch (SQLException e) {
			throw new MapperException(e,null,null);
		} catch (IllegalArgumentException e) {
			throw new MapperException(e,null,null);
		} catch (SecurityException e) {
			throw new MapperException(e,null,null);
		} catch (IntrospectionException e) {
			throw new MapperException(e,null,null);
		} catch (IllegalAccessException e) {
			throw new MapperException(e,null,null);
		} catch (NoSuchFieldException e) {
			throw new MapperException(e,null,null);
		}
	}
	public void initCounters(A_Model m) throws MapperException {
		for (String tableName : getTables().keySet()) {
			List<String> tableCounters = getCounters().get(tableName);
			for (String name : tableCounters) {
				jdbcCounter.init(getC(),getCounterMap(tableName, name));
			}
		}
	}
	public void drop(A_Model m) throws MapperException {
		try {
			for (JdbcTable table : getTables().values()) {
				JdbcUtils.exec(getC(),table.getDropStatement(),null);
			}
		} catch (SQLException e) {
			throw new MapperException(e,null,null);
		}
	}
	public A_Model insert(A_Model m) throws MapperException {
		try {
			String tableName;
			List<String> dbFields;
			List<String> modelFields;
			Long count;
			for (JdbcTable table : getTables().values()) {
				tableName = table.getTableName();
				readColumnDefinitions(table);
				// compteurs
				List<String> tableCounters = getCounters().get(tableName); 
				if (tableCounters!=null){
					for (String name : tableCounters) {
						count = jdbcCounter.increment(getC(),getCounterMap(tableName,name));					
						BeanUtils.map(name,count,m,Converters.getAtypeConverters(),false,BeanUtils.CONNECTOR_DB,null);
					}
				}
				
				// insert des donnees
				modelFields = getFields().get(tableName);
				dbFields = getMap(modelFields);
				keepOnlyExistingColumns(table.getColumnDefinitions(), dbFields, null, modelFields);
				JdbcUtils.exec(getC(),table.getInsertStatement(dbFields),BeanUtils.getValues(m,modelFields).toArray());
			}
			return m;
		} catch (IntrospectionException e) {
			throw new MapperException(e,e.getMessage(),null);
		} catch (IllegalArgumentException e) {
			throw new MapperException(e,e.getMessage(),null);
		} catch (SQLException e) {
			throw new MapperException(e,e.getMessage(),null); // FB2012542 - 25/05/2012
		} catch (IllegalAccessException e) {
			throw new MapperException(e,e.getMessage(),null);
		} catch (InvocationTargetException e) {
			throw new MapperException(e,e.getMessage(),null);
		}
	}
	
	public void delete(A_Model m) throws MapperException {
		try 
		{
			List<String> modelKeys; 
			List<String> dbKeys;
			for (JdbcTable table : getTables().values()) {
				readColumnDefinitions(table);
				List<Object> args;
				String sql;
				if(getFilters().size()==0) {
					modelKeys = getKeys().get(table.getTableName());
					dbKeys = getMap(modelKeys);
					keepOnlyExistingColumns(table.getColumnDefinitions(),dbKeys, null, modelKeys);
					List<JdbcFilter> filters = new ArrayList<JdbcFilter>();
					boolean first = true;
					for (String modelKey : modelKeys) {
						if (!first) filters.add(new JdbcFilter(Operator.AND));
						filters.add(new JdbcFilter(Operator.EQ,modelKey));
						first=false;
					}
					sql = table.getDeleteStatement(filters);
					args = BeanUtils.getValues(m,modelKeys);
				} else {
					sql = table.getDeleteStatement(getFilters());
					args = getFiltersValues();
				}
				JdbcUtils.exec(getC(),sql,args.toArray());
			}
		} catch (SQLException e) {
			throw new MapperException(e,null,null);
		} catch (IllegalArgumentException e) {
			throw new MapperException(e,null,null);
		} catch (IntrospectionException e) {
			throw new MapperException(e,null,null);
		} catch (IllegalAccessException e) {
			throw new MapperException(e,null,null);
		} catch (InvocationTargetException e) {
			throw new MapperException(e,null,null);
		}
	}
	
	public A_Model select(A_Model m) throws MapperException 
	{
		A_Model theObject = null;
		List<String> dbFields;
		List<String> modelFields;
		boolean overide = false;
		for (JdbcTable table : getTables().values()) {
			readColumnDefinitions(table);
			modelFields = getFields().get(table.getTableName());
			dbFields = getMap(modelFields);
			keepOnlyExistingColumns(table.getColumnDefinitions(), dbFields, null, modelFields);
			List<String> dbKeys = null;
			List<String> modelKeys = null;
			if (overide) {
				modelKeys = getKeys().get(table.getTableName());
				dbKeys = getMap(modelKeys);
				keepOnlyExistingColumns(table.getColumnDefinitions(),dbKeys,null,modelKeys);
			}
			try {
				theObject = JdbcUtils.toObject(
					theObject,
					m.getClass(),
					getC(),
					table.getSelectStatement(dbFields,getReverseMap(),getFilters()),
					this.getFiltersValues().toArray(),
					overide,
					modelKeys,
					true
				);
			// FB2016100 - 11/02/2016
			} catch (Exception e) {
				throw new MapperException(e,null,null);
			}
			overide=true;
		};		
		return theObject;
	}

	public List<? extends A_Model> selectAll(A_Model m) throws MapperException {
		List<? extends A_Model> list = new ArrayList<A_Model>();
		try {
			List<String> dbFields;
			List<String> modelFields;
			boolean overide = false;
			for (JdbcTable table : getTables().values()) {
				readColumnDefinitions(table);
				modelFields = getFields().get(table.getTableName());
				dbFields = getMap(modelFields);
				keepOnlyExistingColumns(table.getColumnDefinitions(), dbFields, null, modelFields);
				List<String> dbKeys = null;
				List<String> modelKeys = null;
				if (overide) {
					modelKeys = getKeys().get(table.getTableName());
					dbKeys = getMap(modelKeys);
					keepOnlyExistingColumns(table.getColumnDefinitions(),dbKeys,null,modelKeys);
				}
				JdbcUtils.toList(
						list,
						m.getClass(),
						getC(),
						table.getSelectStatement(dbFields,getReverseMap(),getFilters()),
						this.getFiltersValues().toArray(),
						overide,
						modelKeys,
						nbMaxResult,
						true
					);
				overide=true;
			}
		} catch (SQLException e) {
			throw new MapperException(e,null,null);
		} catch (IllegalArgumentException e) {
			throw new MapperException(e,null,null);
		} catch (IntrospectionException e) {
			throw new MapperException(e,null,null);
		} catch (IllegalAccessException e) {
			throw new MapperException(e,null,null);
		} catch (InvocationTargetException e) {
			throw new MapperException(e,null,null);
		} catch (InstantiationException e) {
			throw new MapperException(e,null,null);
		}
		return list;
	}

	public void update(A_Model m) throws MapperException {
		try {
			List<String> dbFields,modelFields; 
			List<String> dbKeys,modelKeys; 
			for (JdbcTable table : getTables().values()) {
				readColumnDefinitions(table);
				modelFields = getFields().get(table.getTableName());
				dbFields = getMap(modelFields);
				keepOnlyExistingColumns(table.getColumnDefinitions(),dbFields, null, modelFields);
				String sql;
				List<Object> args;
				if(getFilters().size()==0) {
					modelKeys = getKeys().get(table.getTableName());
					dbKeys = getMap(modelKeys);
					keepOnlyExistingColumns(table.getColumnDefinitions(),dbKeys, null, modelKeys);
					List<JdbcFilter> filters = new ArrayList<JdbcFilter>();
					boolean first = true;
					for (String modelKey : modelKeys) {
						if (!first) filters.add(new JdbcFilter(Operator.AND));
						filters.add(new JdbcFilter(Operator.EQ,modelKey));
						first=false;
					}
					sql = table.getUpdateStatement(dbFields,filters);
					modelFields.addAll(modelKeys);
					args = BeanUtils.getValues(m,modelFields);
				} else {
					sql = table.getUpdateStatement(dbFields,getFilters());
					args = BeanUtils.getValues(m,modelFields);
					args.addAll(getFiltersValues());
				}
				JdbcUtils.exec(getC(),sql,args.toArray());
			}		
		} catch (SQLException e) { 
			throw new MapperException(e,null,null);
		} catch (IllegalArgumentException e) {
			throw new MapperException(e,null,null);
		} catch (IntrospectionException e) {
			throw new MapperException(e,null,null);
		} catch (IllegalAccessException e) {
			throw new MapperException(e,null,null);
		} catch (InvocationTargetException e) {
			throw new MapperException(e,null,null);
		}
	}
	
	public A_Model insertOrUpdate(A_Model m) throws MapperException {
		if (select(m)==null)
			m = insert(m);
		else
			update(m);
		return m;
	}
	public A_Model insertIfNotExist(A_Model m) throws MapperException {
		if (select(m)==null) m = insert(m);
		return m;
	}
	

	private final void keepOnlyExistingColumns(List<JdbcColumnDefinition> columnDefinitions,List<String> dbFields,List<String> operators,List<String> modelFields) {
		ListIterator<String> li_dbf = dbFields.listIterator(dbFields.size());
		String dbf;
		boolean remove;
		int i;
		while(li_dbf.hasPrevious()){
			i = li_dbf.previousIndex();
			dbf = li_dbf.previous();
			Iterator<JdbcColumnDefinition> i_cd = columnDefinitions.iterator();
			JdbcColumnDefinition cd;
			remove = true;
			while (i_cd.hasNext()) {
				cd = i_cd.next();
				if (cd.equalsIgnoreCase(dbf)) remove = false;
			}
			if (remove) {
				li_dbf.remove();
				if (operators!=null) operators.remove(i);
				if (modelFields!=null) modelFields.remove(i);
			}
		}
	}
	private final List<String> getSqlTypes(A_Model m,List<String> modelFields) throws IntrospectionException, IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException, MapperException {
		List<String> sqlTypes = new ArrayList<String>();
		Iterator<String> i_fields = modelFields.iterator();
		String fieldName;
		String sqlType;
		PropertyDescriptor[] pds = Introspector.getBeanInfo(m.getClass()).getPropertyDescriptors();
		PropertyDescriptor pd;
		while (i_fields.hasNext()) {
			fieldName = i_fields.next();
			pd = BeanUtils.getPropertyDescriptor(pds,fieldName);
			if (pd==null) throw new MapperException(new Exception("No Such Field in Model"),m.getClass().getName(),fieldName);
			if (A_Type.class.isAssignableFrom(pd.getPropertyType())) {
				sqlType = (String)pd.getPropertyType().getField(A_Type.FIELDNAME_SQLTYPE).get(null);
				sqlTypes.add(sqlType);
			}
		}
		return sqlTypes;
	}
	
	private List<Object> getFiltersValues()	{
		List<Object> listValues = new ArrayList<Object>();
		List<A_Type> oneListValues = new ArrayList<A_Type>();
		for(JdbcFilter oneFilter : getFilters()) {
			oneListValues = oneFilter.getListValues();
			for(A_Type oneValue : oneListValues) {
				listValues.add(oneValue);
			}
		}		
		return listValues;
	}
	
	private void readColumnDefinitions(JdbcTable table) throws MapperException {
		try {
			if (!table.hasReadColumnDefinitions()) table.readColumnDefinitions(getMetaData());
		} catch (SQLException e) {
			throw new MapperException(e,null,null);			
		} catch (NamingException e) {
			throw new MapperException(e,null,null);
		} catch (ClassNotFoundException e) {
			throw new MapperException(e,null,null);
		}
	}

}

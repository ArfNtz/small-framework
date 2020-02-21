package fr.toyframework.mapper.jdbc;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;

import fr.toyframework.Log;
import fr.toyframework.mapper.BeanUtils;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.model.A_Model;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.Identifier;
import fr.toyframework.type.TypeException;
import fr.toyframework.type.format.TextFormat;

public class JdbcUtils {
	
	private static String bundleFilename = JdbcUtils.class.getName();

	private static int queryTimeout = Integer.parseInt(ResourceBundle.getBundle(bundleFilename).getString("queryTimeout"));

	// TODO private static JdbcCache cache = new JdbcCache();

	public static Identifier[] exec(Connection c,String sql,Object[] args) throws SQLException  {
		PreparedStatement ps=null;
		try{
			ps = execute(c, sql, args);
			Identifier[] keys = null;
			/* @TODO : hsqldb not supported / auto increment
			ResultSet rs = ps.getGeneratedKeys();
			if (rs!=null&&rs.next()) {
				int nbCols = rs.getMetaData().getColumnCount();
				keys = new Identifier[nbCols];
				for (int i=0;i<nbCols;i++) keys[i] = new Identifier(rs.getInt(i));
				rs.close();
			}
			*/
			close(ps);
			return keys;
		} catch (SQLException t) {
			close(ps);
			throw t;
		}
	}

	public static String toXml(List<Map<Object,Object>> list) {
		String xml="";
		Iterator<Map<Object,Object>> i_list = list.iterator();
		Map<Object,Object> line;
		while (i_list.hasNext()) {
			line = i_list.next();
			xml+=TextFormat.toXml(line);
		}
		return xml;
	}
	@SuppressWarnings("unchecked")
	public static List<?> toList(List<?> list,Class<?> type,Connection c,String sql,Object[] args,boolean override,List<String> keyAttributeNames,int maxNbResults,boolean useCache) throws SQLException, IllegalArgumentException, MapperException, IntrospectionException, IllegalAccessException, InvocationTargetException, InstantiationException {
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ps = execute(c, sql, args/*,useCache */);
			rs = ps.getResultSet();
			if (rs!=null) { 
				ResultSetMetaData rsmd = ps.getMetaData();
				int i=0;
				while (rs.next()&&i<maxNbResults) {
					if (list==null) list = new ArrayList<Object>();
					i++;
					if (override) map(rs,rsmd,list,keyAttributeNames);
					else {
						Object o = type.newInstance();
						map(rs,rsmd,o);
						((List<Object>)list).add(o);
					}
				}
				close(rs);
			}
			close(ps);
		} catch (SQLException t) {
			close(rs);
			close(ps);
			throw t;
		}
		return list;
	}
	
	public static Object toObject(Object theObject,Class<?> type,Connection c,String sql,Object[] args,boolean override,List<String> keyAttributeNames,boolean useCache) throws Exception{
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps = execute(c, sql, args);
			rs = ps.getResultSet();
			if (rs!=null) {
				ResultSetMetaData rsmd = ps.getMetaData();
				if (rs.next()) {
					if (!override) theObject = (A_Model)type.newInstance(); 
					map(rs,rsmd,theObject);
				}
				close(rs); 
			}
			close(ps);
			return theObject;
		// FB2016100 - 11/02/2016
		} catch(Throwable t) { 
			close(rs);
			close(ps);
			throw new Exception(t);
		}
	}
	
	public static String toXml(Connection c,String sql,Object[] args) throws Exception{
		String xml="";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ps = execute(c, sql, args);
			rs = ps.getResultSet();
			if (rs!=null) {
				ResultSetMetaData rsmd = ps.getMetaData();
				while (rs.next()) {
					xml+=toXml(rs,rsmd);
				}
				close(rs);
			}
			close(ps);
		} catch (Throwable t) {
			close(rs);
			close(ps);
			throw new Exception(t);
		}
		return xml;
	}
	/**
	 * ne pas utiliser dans les process 
	 * for internal framework use only
	 * @throws SQLException 
	 */
	public static PreparedStatement execute(Connection c,String sql,Object[] args) throws SQLException {
		PreparedStatement ps=null;
		ParameterMetaData md=null;
		int parameter_count=0;
		String _sql = merge(sql,args);
		try{
			ps = c.prepareStatement(sql);
			ps.setQueryTimeout(queryTimeout);
			md = ps.getParameterMetaData();
			parameter_count = md.getParameterCount();
			Object value;
			int _type;
			for (int i=0;args!=null&&i<args.length;i++) {
				if (args[i] instanceof A_Type) value = ((A_Type)args[i]).getDbString();
				else value = args[i];
				if (value==null) value="";
				if (i < parameter_count) {
					_type = md.getParameterType(i+1);
					if (value==null) value="";//ps.setNull(i+1,_type);
					else {
						if (_type == Types.VARCHAR) value = value.toString().trim();
						if (value.equals("")) value = " ";
						ps.setObject(i+1,value,_type);
					}
				}
			}
			long t1 = System.currentTimeMillis();
			ps.execute();
			long d = System.currentTimeMillis()-t1;
//			Log.logPerf.log(Level.FINEST,"SQL"+Log.separator+_sql+Log.separator+d);
		} catch (SQLException e) {
			close(ps);
			throw e;
		} catch (TypeException e) {
			close(ps);
			Log.logConnector.log(Level.WARNING,_sql,e);
		} finally {
			Log.logConnector.finest(_sql);
		}
		return ps;
	}

	private static void map(ResultSet rsline,ResultSetMetaData rsmd,List<?> list,List<String> keyAttributeNames) throws SQLException, MapperException, IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		Iterator<?> i_list = list.iterator();
		Object o;
		while (i_list.hasNext()) {
			o = i_list.next();
			if (equals(rsline,rsmd,o,keyAttributeNames)) {
				map(rsline,rsmd,o); 
			}
		}
	}
	private static void map(ResultSet rsline,ResultSetMetaData rsmd,Object o) throws SQLException, MapperException {
		for (int i=1;i<=rsmd.getColumnCount();i++) {
			BeanUtils.getInstance().map(o,rsmd.getColumnName(i),rsline.getString(i));
		}
	}
	private static boolean equals(ResultSet rsline,ResultSetMetaData rsmd,Object bean,List<String> keyAttributeNames) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SQLException  {
		boolean r = false;
		PropertyDescriptor pd;
		Object value;
		Iterator<String> i_ = keyAttributeNames.iterator();
		String s;
		while (i_.hasNext()) {
			s = i_.next();
			pd = BeanUtils.getPropertyDescriptor(Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors(),s);
			if (pd!=null) {
				value = pd.getReadMethod().invoke(bean, new Object[]{});
				if (rsline.getString(s).equals(value.toString())) r = true;
				else return false;
			} else return false;
		}
		return r;
	}
	private static void close(PreparedStatement ps){
		try {
			if (ps!=null) ps.close();
		} catch (SQLException e) {
			Log.logConnector.log(Level.FINER,"",e);
		}
	}
	private static void close(ResultSet rs){
		try {
			if (rs!=null) rs.close();
		} catch (SQLException e) {
			Log.logConnector.log(Level.FINER,"",e);
		}
	}
	private static String toXml(ResultSet rsline,ResultSetMetaData rsmd) throws SQLException, Exception{
		String xml="";
		for (int i=1;i<=rsmd.getColumnCount();i++) {
			xml+=
			"<"+rsmd.getColumnName(i)+
			" type=\""+rsmd.getColumnType(i)+"\""+
			" size=\""+rsmd.getPrecision(i)+"\""+
			" decimal=\""+rsmd.getScale(i)+"\""+
			">"+
			rsline.getString(i)+
			"</"+rsmd.getColumnName(i)+">"+
			"\n";
		}
		return xml;
	}
	//debug use only
	static public String dump(ResultSet rs) throws SQLException {
		String dump = "\n";
		ResultSetMetaData meta = rs.getMetaData();
		for (int i=1;i<=meta.getColumnCount();i++) dump+= meta.getColumnName(i)+";";
		while (rs.next()){
			dump+="\n";
			for (int i=1;i<=meta.getColumnCount();i++) dump+= rs.getString(i)+";";
		}
		//rs.close();
		return dump;
	}
	public static String merge(
		String sql,
		Object[] args
	) {
		//Arrays.toString(args)
		StringTokenizer st = new StringTokenizer(sql,"?");
		String _sql = "";
		int i;
		for (i=0;st.hasMoreTokens();i++) {
			_sql += st.nextToken();
			if (args!=null&&i<args.length)
				_sql += "["+args[i]+"]";
		}
		if (args!=null&&i<args.length) _sql += args[i];
		return _sql;
	}
}

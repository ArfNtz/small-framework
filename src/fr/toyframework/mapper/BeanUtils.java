package fr.toyframework.mapper;
import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import fr.toyframework.Log;
import fr.toyframework.mapper.converter.A_Converter;
import fr.toyframework.mapper.converter.ConverterException;
import fr.toyframework.mapper.converter.Converters;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.TypeException;

public class BeanUtils {
	
	private Map<Class<?>, A_Converter> converters;
	private Locale locale;
	private boolean caseSensitive;

	public static BeanUtils getInstance() {
		BeanUtils beanUtils = new BeanUtils();
		beanUtils.setLocale(Locale.getDefault());
		beanUtils.setConverters(Converters.getAtypeConverters(Locale.getDefault()));
		beanUtils.setCaseSensitive(true);
		return beanUtils;
	}

	public void map (
		Object object,
		String propertyName,
		Object propertyValue
	) throws MapperException {
		String _propertyName=null;
		Method mw=null;
		Method mr=null;
		String propertyNameToken=null;
		try {
			if (object==null) return;
			PropertyDescriptor[] propertyDescriptors;
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			propertyDescriptors = beanInfo.getPropertyDescriptors();
			PropertyDescriptor propertyDescriptor;
			IndexedPropertyDescriptor iprop = null;
			StringTokenizer stk = new StringTokenizer(propertyName,".");
			boolean indexed = false;
			int index = 0;
			boolean found = true;

			while (stk.hasMoreElements()&&found) {

				propertyNameToken = stk.nextToken();
				indexed = false;
				if ((propertyNameToken.indexOf('[') != -1) || (propertyNameToken.indexOf('(') != -1))
				{
					indexed = true;
					StringTokenizer stk2 = new StringTokenizer(propertyNameToken, "[]()");
					propertyNameToken = stk2.nextToken();
					String propertyIndex = stk2.nextToken();
					index = Integer.parseInt(propertyIndex);
				}

				beanInfo = Introspector.getBeanInfo(object.getClass());
				propertyDescriptors = beanInfo.getPropertyDescriptors();
				found = false;

				for (int i = propertyDescriptors.length - 1; i>=0; --i) {

					propertyDescriptor = propertyDescriptors[i];
					if (propertyDescriptor.isExpert() || propertyDescriptor.isHidden()) continue;
					_propertyName = propertyDescriptor.getName();

					if (!stk.hasMoreElements())
					{
						if (caseSensitive&&_propertyName.equals(propertyNameToken)||_propertyName.equalsIgnoreCase(propertyNameToken))
						{
							found = true;
							if (indexed) {
								iprop = (IndexedPropertyDescriptor) propertyDescriptor;
								mw = iprop.getIndexedWriteMethod();
								mr = iprop.getIndexedReadMethod();
								if (mw!=null) {
									Object into = mr.invoke(object,new Object[]{new Integer(index)});
									propertyValue = convert(propertyValue,into,mw.getParameterTypes()[1],mw.getGenericParameterTypes()[1]);
									mw.invoke(object,new Object[]{new Integer(index),propertyValue});
								}
							} else	{
								mw = propertyDescriptor.getWriteMethod();
								mr = propertyDescriptor.getReadMethod();
								if (mw!=null) {
									Object into = mr.invoke(object,new Object[]{});
									propertyValue = convert(propertyValue,into,mw.getParameterTypes()[0],mw.getGenericParameterTypes()[0]);
									mw.invoke(object,new Object[]{propertyValue});
								}
							}
						}
					}
					else
					{
						if (caseSensitive&&_propertyName.equals(propertyNameToken)||_propertyName.equalsIgnoreCase(propertyNameToken))
						{
							found = true;
							if (indexed) {
								if (!(propertyDescriptor instanceof IndexedPropertyDescriptor)) continue;
								iprop = (IndexedPropertyDescriptor) propertyDescriptor;
								mr = iprop.getIndexedReadMethod();
								if (mr!=null) object = mr.invoke(object, new Object[] {new Integer(index)});
							} else {
								mr = propertyDescriptor.getReadMethod();
								if (mr!=null) object = mr.invoke(object, new Object[] {});
							}
							break;
						}
					}
				}
			}
		}
		catch (ConverterException e) { throw new MapperException(e,propertyName,propertyValue); }
		catch (IntrospectionException e) { throw new MapperException(e,propertyName,propertyValue); }
		catch (IllegalArgumentException e) {throw new MapperException(e,propertyName,propertyValue);}
		catch (IllegalAccessException e) {throw new MapperException(e,propertyName,propertyValue);}
		catch (InvocationTargetException e) {throw new MapperException(e,propertyName,propertyValue);}
		catch (NullPointerException e) {throw new MapperException(e,propertyName,propertyValue);}
	}
	public Object map(Object from,Object to,Map<Class<?>, A_Converter> converters,boolean caseSensitive) throws MapperException {
		try {
			PropertyDescriptor[] from_pds = Introspector.getBeanInfo(from.getClass()).getPropertyDescriptors();
			PropertyDescriptor[] to_pds = Introspector.getBeanInfo(to.getClass()).getPropertyDescriptors();
			PropertyDescriptor from_pd,to_pd;
			String from_name;
			Method mr,mw;
			Object from_value=null,to_value=null;
			for (int i=0;i<from_pds.length;i++) {
				from_pd = from_pds[i];
				if (from_pd instanceof IndexedPropertyDescriptor) continue;
				from_name = from_pd.getName();
				for (int j=0;j<to_pds.length;j++) {
					to_pd = to_pds[j];
					if (caseSensitive?from_name.equals(to_pd.getName()):from_name.equalsIgnoreCase(to_pd.getName())) {
						mr = from_pd.getReadMethod();
						mw = to_pd.getWriteMethod();
						if (mr!=null&&mw!=null) {
							from_value = mr.invoke(from,new Object[]{});
							if (from_value!=null) {
								to_value = convert(from_value,null,mw.getParameterTypes()[0],mw.getGenericParameterTypes()[0]);
								mw.invoke(to,new Object[]{to_value});
							}
						}
					}
				}
			}
			return to;
		}
		catch (IntrospectionException e) { throw new MapperException(e,from.getClass().getName(),to.getClass().getName()); }
		catch (InvocationTargetException e) {throw new MapperException(e,from.getClass().getName(),to.getClass().getName());}
		catch (IllegalAccessException e) {throw new MapperException(e,from.getClass().getName(),to.getClass().getName());}
		catch (ConverterException e) { throw new MapperException(e,from.getClass().getName(),to.getClass().getName()); }
	}
	public Map<?,?> toHash(List<?> list,String keyAttributeName) throws MapperException {
		HashMap<Object,Object> hm = new HashMap<Object,Object>();
		try {
			Object keyValue;
			for (Object o : list) {
				keyValue = BeanUtils.getValue(o, keyAttributeName);
				hm.put(keyValue,o);
			}
		}
		catch (SecurityException e) { throw new MapperException(e,keyAttributeName,""); }
		catch (InvocationTargetException e) { throw new MapperException(e,keyAttributeName,""); }
		catch (IllegalArgumentException e) { throw new MapperException(e,keyAttributeName,""); }
		catch (IllegalAccessException e) { throw new MapperException(e,keyAttributeName,""); }
		catch (IntrospectionException e) { throw new MapperException(e,keyAttributeName,""); }
		return hm;
	}
	public static PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] pds, String propertyName) {
		for (int j=pds.length-1; j>=0; --j) {
			if (pds[j].getName().equals(propertyName)) return pds[j];
		}
		return null;
	}
	public List<String> getProperties(Class<?> modelClass, Class<?> stopClass, Class<?> assignable) throws MapperException {
		return getProperties(modelClass,stopClass,assignable,null,null);
	}
	public List<String> getProperties(Class<?> modelClass, Class<?> stopClass, Class<?> propertyType, String propertyMethodReturningTrue, Object o) throws MapperException {
		List<String> properties = new ArrayList<String>();
		Class<?> _propertyType = null;
		String propertyName = null;
		try {
			PropertyDescriptor[] pds = Introspector.getBeanInfo(modelClass,stopClass).getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				_propertyType = pd.getPropertyType();
				propertyName = pd.getName();
				Object property;
				if (propertyType.isAssignableFrom(_propertyType)) {
					if (propertyMethodReturningTrue!=null && o!=null) {
						property = pd.getReadMethod().invoke(o,(Object[])null);
						if (property!=null) {
							Method mr = propertyType.getMethod(propertyMethodReturningTrue,new Class[]{});
							if (mr!=null&&((Boolean)mr.invoke(property,new Object[]{})).booleanValue()) {
								properties.add(propertyName);
							}
						}
					} else {
						properties.add(propertyName);
					}
				}
			}
		}
		catch (IntrospectionException e) { throw new MapperException(e,propertyName,propertyType); }
		catch (IllegalArgumentException e) { throw new MapperException(e,propertyName,propertyType); }
		catch (IllegalAccessException e) { throw new MapperException(e,propertyName,propertyType); }
		catch (SecurityException e)	{ throw new MapperException(e,propertyName,propertyType);}
		catch (NoSuchMethodException e) {throw new MapperException(e,propertyName,propertyType);}
		catch (InvocationTargetException e) {throw new MapperException(e,propertyName,propertyType);}
		return properties;
	}
	public static List<Object> getValues(Object o,List<String> propertyNames) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<Object> values = new ArrayList<Object>();
		for (String s : propertyNames) values.add(getValue(o,s));
		return values;
	}
	public static Object getValue(Object o,String propertyName) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(Introspector.getBeanInfo(o.getClass()).getPropertyDescriptors(),propertyName);
		Object value = null;
		if (pd!=null) {
			value = pd.getReadMethod().invoke(o, new Object[]{});
		}
		return value;		
	}
	public static void dumpPropertyDescriptors(PropertyDescriptor[] pds) {
		for (int j=pds.length-1; j>=0; --j) Log.logMapper.finer(pds[j].getName());
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object convert(Object data, Object into, Class<?> intoClass,Type genericIntoType) throws ConverterException {
		try {
			//if (data == null) return null;
			A_Converter converter = null;
	
			if (A_Type.class.isAssignableFrom(intoClass)) {
				if (data != null && data.getClass().isArray() && !intoClass.isArray()) data = ((Object[]) data)[0];
				converter = converters.get(intoClass);
				if (converter!=null) return converter.convert(data, into, intoClass, null); 
				else return Converters.getAtypeConverter(getLocale()).convert(data,into,intoClass,null);
			}
	
			else if (List.class.isAssignableFrom(intoClass)) {
				into = ArrayList.class.newInstance();
				ParameterizedType pt = (ParameterizedType) genericIntoType;
				Type t = pt.getActualTypeArguments()[0];
				Class<?> c = (Class<?>) t;
				if (data != null && !data.getClass().isArray()) data = new Object[] { data };
				int j = Array.getLength(data);
				for (int i = 0; i < j; i++) {
					if (A_Type.class.isAssignableFrom(c)) {
						A_Type o = (A_Type) c.newInstance();
						o.setUiString(Array.get(data, i).toString());
						((List) into).add(o);
					} else if (String.class.isAssignableFrom(c)) {//PEG - PEGA-8WYCST - 29/08/2012
						String s = Array.get(data, i).toString();
						((List) into).add(s);
					}
				}
				return into;
			}
	
			else if (intoClass.isArray()) {
				if (data != null && !data.getClass().isArray()) data = new Object[] { data };
				intoClass = intoClass.getComponentType();
				int j = Array.getLength(data);
				Object _data = Array.newInstance(intoClass, j);
				for (int i = 0; i < j; i++) {
					converter =  converters.get(intoClass);
					if (converter != null) {
						Object to = converter.convert(Array.get(data, i),into,intoClass,null);
						//if (to == null) throw new ConverterException(MessageFormat.format(ResourceBundle.getBundle(bundleFilename).getString(ERROR_NOT_FOUND), new Object[] { data.getClass(), intoClass }));
						Array.set(_data, i, to);
					}
				}
				return _data;
			}
	
			else {
				if (data != null && data.getClass().isArray()) data = ((Object[]) data)[0];
				converter = converters.get(intoClass);
				if (converter != null) {
					Object to = converter.convert(data,into,intoClass,null);
					//if (to == null) throw new ConverterException(MessageFormat.format(ResourceBundle.getBundle(bundleFilename).getString(ERROR_NOT_FOUND), new Object[] { data.getClass(), intoClass }));
					return to;
				} else return data;
			}
		}
		catch (TypeException e) { throw new ConverterException(e.getMessage(),e); }
		catch (InstantiationException e) { throw new ConverterException(e.getMessage(),e); }
		catch (IllegalAccessException e) { throw new ConverterException(e.getMessage(),e); }
	}

	//WV - 19092016 - pour ne pas faire remonter les IllegalAccessException et InvocationTargetException
	public static void copyProperties(Object dest, Object orig){
		try{
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Map<Class<?>, A_Converter> getConverters() { return converters; }
	public void setConverters(Map<Class<?>, A_Converter> converters) { this.converters = converters; }
	public Locale getLocale() { return locale; }
	public void setLocale(Locale locale) { this.locale = locale; }
	public boolean isCaseSensitive() { return caseSensitive; }
	public void setCaseSensitive(boolean caseSensitive) { this.caseSensitive = caseSensitive; }

}

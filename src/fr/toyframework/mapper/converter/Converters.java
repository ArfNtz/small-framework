package fr.toyframework.mapper.converter;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import fr.toyframework.type.A_Type;
import fr.toyframework.type.Time;
import fr.toyframework.type.TypeException;
import fr.toyframework.type.format.TextFormat;

public class Converters {

	// suppr NAU - NAUD-8SKHDT - 28/03/2012
	
	private static Map<Class<?>, A_Converter> allConverters;
	private static Map<Class<?>, A_Converter> javaConverters;
	private static Map<Class<?>, A_Converter> atypeConverters;
	private static A_Converter atypeConverter;

	public static Map<Class<?>, A_Converter> getAllConverters(Locale l) {
		if (allConverters==null || allConverters.isEmpty()) {
			allConverters = new HashMap<Class<?>, A_Converter>();
			allConverters.putAll(getJavaConverters(l));
			allConverters.putAll(getAtypeConverters(l));
		}
		return allConverters;
	}
	
	public static Map<Class<?>, A_Converter> getJavaConverters(Locale l) {
		if (javaConverters==null || javaConverters.isEmpty()) {
			javaConverters = new HashMap<Class<?>, A_Converter>();
			
			javaConverters.put(boolean.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						if (_data.equalsIgnoreCase("on") || _data.equalsIgnoreCase("y") || _data.equalsIgnoreCase("yes")
								|| _data.equalsIgnoreCase("o") || _data.equalsIgnoreCase("oui") || _data.equalsIgnoreCase("1")
								|| _data.equalsIgnoreCase("true")
						) return new Boolean(true);
						else return new Boolean(false);
					} else if (fr.toyframework.type.Boolean.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Boolean) data).booleanValue();
					} else return null;
				}
			});
	
			javaConverters.put(Boolean.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						if (_data.equalsIgnoreCase("on") || _data.equalsIgnoreCase("y") || _data.equalsIgnoreCase("yes")
								|| _data.equalsIgnoreCase("o") || _data.equalsIgnoreCase("oui") || _data.equalsIgnoreCase("1")
								|| _data.equalsIgnoreCase("true")
						) return new Boolean(true);
						else return new Boolean(false);
					} else if (fr.toyframework.type.Boolean.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Boolean) data).booleanValue();
					} else return null;
				}
			});
			javaConverters.put(byte.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						return new Byte(TextFormat.trimSpace(_data));
					} else return data;
				}
			});
			javaConverters.put(Byte.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						return new Byte(TextFormat.trimSpace(_data));
					} else return data;
				}
			});
			javaConverters.put(char.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						return new Character(_data.charAt(0));
					} else if (fr.toyframework.type.Char.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Char) data).toString().charAt(0);
					} else return null;
				}
			});
			javaConverters.put(Character.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						return new Character(_data.charAt(0));
					} else if (fr.toyframework.type.Char.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Char) data).toString().charAt(0);
					} else return null;
				}
			});
			javaConverters.put(short.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						if (_data.trim().equals("")) _data = "0";
						return new Short(TextFormat.trimSpace(_data.replace(',', '.')));
					} else if (fr.toyframework.type.Numeric.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Numeric) data).getNumber().shortValue();
					} else return null;
				}
			});
			javaConverters.put(Short.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						if (_data.trim().equals("")) _data = "0";
						return new Short(TextFormat.trimSpace(_data.replace(',', '.')));
					} else if (fr.toyframework.type.Numeric.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Numeric) data).getNumber().shortValue();
					} else return null;
				}
			});
			javaConverters.put(int.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						if (_data.trim().equals("")) _data = "0";
						return new Integer(TextFormat.trimSpace(_data.replace(',', '.')));
					} else if (fr.toyframework.type.Numeric.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Numeric) data).getNumber().intValue();
					} else return null;
				}
			});
			javaConverters.put(Integer.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						if (_data.trim().equals("")) _data = "0";
						return new Integer(TextFormat.trimSpace(_data.replace(',', '.')));
					} else if (fr.toyframework.type.Numeric.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Numeric) data).getNumber().intValue();
					} else return null;
				}
			});
			javaConverters.put(long.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						if (_data.trim().equals("")) _data = "0";
						return new Long(TextFormat.trimSpace(_data.replace(',', '.')));
					} else if (fr.toyframework.type.Numeric.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Numeric) data).getNumber().longValue();
					} else return null;
				}
			});
			javaConverters.put(Long.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						if (_data.trim().equals("")) _data = "0";
						return new Long(TextFormat.trimSpace(_data.replace(',', '.')));
					} else if (fr.toyframework.type.Numeric.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Numeric) data).getNumber().longValue();
					} else return null;
				}
			});
			javaConverters.put(float.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						return new Float(TextFormat.trimSpace(_data.replace(',', '.')));
					} else if (fr.toyframework.type.Numeric.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Numeric) data).getNumber().floatValue();
					} else return null;
				}
			});
			javaConverters.put(Float.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						if (_data.trim().equals("")) _data = "0";
						return new Float(TextFormat.trimSpace(_data.replace(',', '.')));
					} else if (fr.toyframework.type.Numeric.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Numeric) data).getNumber().floatValue();
					} else return null;
				}
			});
			javaConverters.put(double.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						if (_data.trim().equals("")) _data = "0";
						return new Double(TextFormat.trimSpace(_data.replace(',', '.')));
					} else if (fr.toyframework.type.Numeric.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Numeric) data).getNumber().doubleValue();
					} else return null;
				}
			});
			javaConverters.put(Double.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						if (_data.trim().equals("")) _data = "0";
						return new Double(TextFormat.trimSpace(_data.replace(',', '.')));
					} else if (fr.toyframework.type.Numeric.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Numeric) data).getNumber().doubleValue();
					} else return null;
				}
			});
			javaConverters.put(String.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						return TextFormat.trimSpace(_data);
					} else if (A_Type.class.isAssignableFrom(data.getClass())) {
						try {
							return ((A_Type) data).getUiString();
						} catch (TypeException e) {
							throw new ConverterException(e.getMessage(),e);
						}
					} else return null;
				}
			});
			javaConverters.put(Date.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						String _data = (String) data;
						_data = TextFormat.trimSpace(_data);
						try {
							return new SimpleDateFormat().parse(_data);
						} catch (ParseException e) {
							throw new ConverterException(e.getMessage(),e);
						}
					} else if (fr.toyframework.type.Date.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Date) data).getDate();					
					} else if (fr.toyframework.type.Datetime.class.isAssignableFrom(data.getClass())) {
						return ((fr.toyframework.type.Datetime) data).getDate();
					} else return null;
				}
			});
		}
		
		return javaConverters;
	}


	public static Map<Class<?>, A_Converter> getAtypeConverters(Locale l) {
		if (atypeConverters==null || atypeConverters.isEmpty()) {
			atypeConverters = new HashMap<Class<?>, A_Converter>();	
			atypeConverters.put(fr.toyframework.type.Date.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						if (((String) data).trim().equals("")) return null;
						else return getAtypeConverter(l).convert(data,into,intoClass,null);
					} else if (data instanceof Date) {
						return new fr.toyframework.type.Date((Date)data);
					} else return null;
				}
			});
			atypeConverters.put(fr.toyframework.type.Datetime.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						if (((String) data).trim().equals("")) return null;
						else return getAtypeConverter(l).convert(data,into,intoClass,null);
					} else if (data instanceof Date) {
						return new fr.toyframework.type.Datetime((Date)data);
					} else return null;
				}
			});		
			// FB201339 - 17/04/2013
			atypeConverters.put(fr.toyframework.type.Time.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						if (((String) data).trim().equals("")) return null;
						else return getAtypeConverter(l).convert(data,into,intoClass,null);
					} else if (data instanceof Time) {
						return new fr.toyframework.type.Time((Date)data);
					} else return null;
				}
			});
			/*atypeConverters.put(fr.toyframework.type.Amount.class, new A_Converter() {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType, int connector, Locale l) throws ConverterException {
					if (data instanceof String) {
						return getAtypeConverter().convert(data,into,intoClass,null,connector, l);
					} else if (data instanceof Double) {
						return new fr.toyframework.type.Amount((Double)data);
					} else return null;
				}
			});*/
			atypeConverters.put(fr.toyframework.type.Decimal.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						return getAtypeConverter(l).convert(data,into,intoClass,null);
					} else if (data instanceof Double) {
						return new fr.toyframework.type.Decimal((Double)data);
					} else return null;
				}
			});
			atypeConverters.put(fr.toyframework.type.Rate.class, new A_Converter(l) {
				public Object convert(Object data, Object into, Class<?> intoClass, Type genericIntoType) throws ConverterException {
					if (data instanceof String) {
						return getAtypeConverter(l).convert(data,into,intoClass,null);
					} else if (data instanceof Double) {
						return new fr.toyframework.type.Rate((Double)data);
					} else return null;
				}
			});
		}
		
		return atypeConverters;
	}

	public static A_Converter getAtypeConverter(Locale l) {
		if (atypeConverter==null) {
			atypeConverter = new A_Converter(l) {
				// convertisseur g�n�rique de String vers A_Type
				public Object convert(Object data,Object into,Class<?> intoClass,Type genericIntoType) throws ConverterException {
					try {
						if (into==null) into = (A_Type)intoClass.newInstance();
						String _data = null;
						if (data!=null) _data = data.toString();
						((A_Type) into).setString(_data);
					}
					catch (TypeException e) { throw new ConverterException(e.getMessage(),e); }
					catch (InstantiationException e) { throw new ConverterException(e.getMessage(),e); }
					catch (IllegalAccessException e) { throw new ConverterException(e.getMessage(),e); }
					return into;
				}
			};
		}
		return atypeConverter;
	}
}
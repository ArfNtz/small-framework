package fr.toyframework.mapper.jdbc.model;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class JdbcColumnDefinition {

	public static Map<Integer,String> jdbcTypeNames;

	String columnName; //COLUMN_NAME
	String columnRemarks; //REMARKS
	int columnType; //DATA_TYPE //java.sql.Type static fields
	int columnSize; //COLUMN_SIZE
	int columnDecimalDigits; //DECIMAL_DIGITS
	boolean nullable; //IS_NULLABLE (YES/NO)
	boolean doNotNull;
	public JdbcColumnDefinition(){}
	public JdbcColumnDefinition(String columnName){	this.columnName = columnName; }
	public JdbcColumnDefinition(String columnName,int columnType,int columnSize,int columnDecimalDigits){
		this.columnName = columnName;
		this.columnType = columnType;
		this.columnSize = columnSize;
		this.columnDecimalDigits = columnDecimalDigits;
	}
	public boolean equals(Object s) {
		return this.getColumnName().equals(s);
	}
	public boolean equalsIgnoreCase(String s) {
		return this.getColumnName().equalsIgnoreCase(s);
	}
	public String toSqlString() {
		String _sql = "\""+getColumnName()+"\" "+getJdbcTypeName(getColumnType())+"("+getColumnSize()+(getColumnDecimalDigits()==0?"":","+getColumnDecimalDigits())+")";
// HORS PORTAGE
//		if (!isNullable()&&isDoNotNull()) _sql+= " NOT NULL";
// PORTAGE
		if (getColumnType()==Types.CHAR) _sql+= " NOT NULL";
		return _sql;
	}
	public String toDdsTypeString() {
		return getDdsTypeName(getColumnType())+"("+getColumnSize()+","+getColumnDecimalDigits()+")";
	}
	public String toDdsString() {
		//A            COLUMNAME- ---10A--       COLHDG('--------------------')
		return "A            "
		+format(getColumnName()," ",10,false,true)
		+format(""+getColumnSize()," ",5,false,false)
		+format(getDdsTypeName(getColumnType())," ",1,false,false)
		+format(""+getColumnDecimalDigits()," ",2,false,false)
		+"       COLHDG('"
		+format(""+getColumnRemarks()," ",20,false,false)
		+"')";
	}
	public String toFlatXmlString(boolean withData, Object data) {
		return "<"+getColumnName()
		+" rem=\""+getColumnRemarks()+"\""
		+" type=\""+getColumnType()+"\""
		+" size=\""+getColumnSize()+"\""
		+" decimal=\""+getColumnDecimalDigits()+"\""
		+">"+(withData?data:"")+"</"+getColumnName()+">\n";
	}
	public String toJavaAttributeCode(boolean toLowerCase,boolean javaTypeOnly) {
		String columnName = toLowerCase?getColumnName().toLowerCase():getColumnName();
		String type = javaTypeOnly?getJavaTypeName():getFrameworkTypeName();
		return "private\t"+type+"\t"+columnName+";";
	}
	public String toJavaGetterCode(boolean toLowerCase,boolean javaTypeOnly) {
		String type = javaTypeOnly?getJavaTypeName():getFrameworkTypeName();
		String columnName = toLowerCase?getColumnName().toLowerCase():getColumnName();
		String accessorName = columnName.replaceFirst(".", (columnName.charAt(0)+"").toUpperCase());
		String arg = getConstructorArg(type);
		return "public "+type+" get"+accessorName+"() { if ("+columnName+"==null) "+columnName+"= new "+type+"("+arg+"); return "+columnName+"; }";
	}
	public String toJavaSetterCode(boolean toLowerCase,boolean javaTypeOnly) {
		String type = javaTypeOnly?getJavaTypeName():getFrameworkTypeName();
		String columnName = toLowerCase?getColumnName().toLowerCase():getColumnName();
		String accessorName = columnName.replaceFirst(".", (columnName.charAt(0)+"").toUpperCase());
		return "public void set"+accessorName+"("+type+" "+columnName+") { this."+columnName+"="+columnName+"; }";
	}
	
	public static String getJdbcTypeName(int type) {
		if (jdbcTypeNames==null) {
			jdbcTypeNames = new HashMap<Integer,String>();
			Field[] fields = java.sql.Types.class.getFields();
			for (int i=0; i<fields.length; i++) {
				try {
					String name = fields[i].getName();
					Integer value = (Integer)fields[i].get(null);
					jdbcTypeNames.put(value, name);
				} catch (IllegalAccessException e) {}
			}
		}
		if (type==Types.CHAR) type=Types.VARCHAR;
		return (String)jdbcTypeNames.get(new Integer(type));
	}
	
	public static String getDdsTypeName(int type) {
		switch (type) {
			case 1: return "A";
			case 12: return "A";
			case -1: return "A";
			default: return " ";		
		}
	}
	
	public String getJavaTypeName() {
		// refer to "java.sql.Types.CHAR = 1" ...
		int type			= getColumnType();
		switch (type) {
			case -1: return "String";
			case 1: return "String";
			case 2: return "Number";
			case 3: return "Number";
			case 4: return "Number";
			case 6: return "Double";
			case 7: return "Double";
			case 8: return "Double";
			case 12: return "String";
			case 16: return "Boolean";
			case 91: return "Date";
			default: return "Unknown";		
		}
	}
	
	public String getFrameworkTypeName() {
		// refer to "java.sql.Types.CHAR = 1" ...
		String name			= getColumnName();
		int type			= getColumnType();
		int length			= getColumnSize();
		int nbDecimalDigits = getColumnDecimalDigits();
		
		switch (type) {
			case -1:return "Text";
			case 1:	return getJavaTypeFromCHAR(name,length);
			case 2: return getJavaTypeFromNUMERIC(name, length, nbDecimalDigits, false);
			case 3: return getJavaTypeFromNUMERIC(name, length, nbDecimalDigits, false);
			case 4: return getJavaTypeFromNUMERIC(name, length, nbDecimalDigits, false);
			
			case 6: return getJavaTypeFromNUMERIC(name, length, nbDecimalDigits, true);
			case 7: return getJavaTypeFromNUMERIC(name, length, nbDecimalDigits, true);
			case 8: return getJavaTypeFromNUMERIC(name, length, nbDecimalDigits, true);
			
			case 12: return "Chars";
			case 16: return "Boolean";
			case 91: return "Date";
			default: return "Unknow";
		}
	}
	
	
	public static String getJavaTypeFromCHAR(String name, int length){
		String frameworkTypeName = "Text";
		if (name.equalsIgnoreCase("CODSOC")){
			frameworkTypeName = "Codsoc";
		}else if (name.equalsIgnoreCase("USRMAJ")){
			frameworkTypeName = "Usrmaj";
		}else if (name.equalsIgnoreCase("UTILIS")){
			frameworkTypeName = "Char";
		}else if (length==1){
			frameworkTypeName = "Char";
		}else if (length==10){
			frameworkTypeName = "Lib10";
		}else if (length==20){
			frameworkTypeName = "Lib20";
		}else if (length==24){
			frameworkTypeName = "Lib24";
		}else if (length==25){
			frameworkTypeName = "Lib25";
		}else if (length==35){
			frameworkTypeName = "Lib35";
		}else if (length==50){
			frameworkTypeName = "Lib50";
		}else{
			frameworkTypeName = "Chars";
		}
		return frameworkTypeName;
	}
	
	public static String getJavaTypeFromNUMERIC(String name, int length, int nbDecimalDigits, boolean forceDecimal){
		String frameworkTypeName = "Decimal";
		if(nbDecimalDigits==0  && !forceDecimal){
			frameworkTypeName = "fr.toyframework.type.Integer";
			if (length==8 && name.startsWith("D")){
				frameworkTypeName = "fr.toyframework.type.Date";
			}else if (length==6 && name.equalsIgnoreCase("DATMAJ")){
				frameworkTypeName = "Datmaj";
			}
		}else if (length==18 && nbDecimalDigits==3){
			frameworkTypeName = "Amount";
		}else if (length==13 && nbDecimalDigits==8){
			frameworkTypeName = "Rate";
		}else{
			frameworkTypeName = "Chars";
		}
		
		return frameworkTypeName;
	}
	
	
	public static String getConstructorArg(String type){
		 return (	type.equals("fr.toyframework.type.Integer")
				 || type.equals("Decimal")
				 || type.equals("Amount")
				 || type.equals("Rate") )
				 ? "0"
				 : (	type.equals("Datmaj") )
					? "new java.util.Date()"
					: (		type.equals("Codsoc")
						||	type.equals("Usrmaj")
						||	type.equals("Chars"))
						? "\"\""
						: type.equals("Char")
							? "' '"
							:"";
	}
	
	
	public static String format(
			String donnee,
			String filler,
			int length,
			boolean cutFromRightToLeft,
			boolean fillAfter
		) {
			String _filler = "";
			int l = donnee.length();
			if (l<length) {
				for(int i=l; i < length; i++) {
					_filler += filler;
				}
				if (fillAfter) {
					// fill after
					donnee += _filler;
				} else {
					// fill before
					donnee = _filler.concat(donnee);
				}
			} else if (l>length) {
				if (cutFromRightToLeft) {
					//cut last 'length' chars
					donnee = donnee.substring(l-length);
				} else {
					//cut first 'length' chars
					donnee = donnee.substring(0,length);
				}
			}
			return donnee;
		}
	
	
	public int getColumnDecimalDigits() {
		return columnDecimalDigits;
	}
	public String getColumnName() {
		return columnName;
	}
	public String getColumnRemarks() {
		return columnRemarks;
	}
	public int getColumnSize() {
		return columnSize;
	}
	public int getColumnType() {
		return columnType;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setColumnDecimalDigits(int columnDecimalDigits) {
		this.columnDecimalDigits = columnDecimalDigits;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public void setColumnRemarks(String columnRemarks) {
		this.columnRemarks = columnRemarks;
	}
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public boolean isDoNotNull() {
		return doNotNull;
	}
	public void setDoNotNull(boolean doNotNull) {
		this.doNotNull = doNotNull;
	}
}
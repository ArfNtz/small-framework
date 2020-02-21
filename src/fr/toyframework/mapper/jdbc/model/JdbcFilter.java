package fr.toyframework.mapper.jdbc.model;

import java.util.ArrayList;
import java.util.List;

import fr.toyframework.type.Operator;
import fr.toyframework.type.A_Type;

public class JdbcFilter {
	
	private Operator operator=null;
	private String fieldName=null;
	private String fieldName2=null;
	private List<A_Type> listValues=null;
	
	public JdbcFilter(Operator operator, String fieldName, String fieldName2)
	{
		this.operator = operator;
		this.fieldName = fieldName;
		this.fieldName2 = fieldName2;
	}
	
	public JdbcFilter(Operator operator, String fieldName,List<A_Type> values)
	{
		this.operator = operator;
		this.fieldName = fieldName;
		getListValues().addAll(values);
	}

	public JdbcFilter(Operator operator, String fieldName)
	{
		this.operator = operator;
		this.fieldName = fieldName;
	}
	public JdbcFilter(Operator operator)
	{
		this.operator = operator;
	}

	public String toString() {
		String stringFilter = "";
		if(operator != null) {
			if(fieldName != null) {
				if (operator.getType()==Operator.TYPE_ARITHMETIC) {
					if (operator==Operator.IN || operator==Operator.NOT_IN) {
						stringFilter = fieldName+" "+operator+"(";
						for(int index=0; index<getListValues().size(); index++) {
							stringFilter += "?";
							if(index+1 < getListValues().size()) {
								stringFilter += ",";
							}
						}
						stringFilter += ")";							
					} else if (operator==Operator.IS_NULL || operator==Operator.IS_NOT_NULL) {
						stringFilter += fieldName+operator;
					} else if (fieldName2!=null) {
						stringFilter += fieldName+operator+fieldName2;
					} else if (fieldName==null) {
						stringFilter += operator;
					} else {
						stringFilter += fieldName+operator+"?";
					}
					return stringFilter;
				} else if (operator.equals(Operator.ORDERBY)) {
					stringFilter = operator+" "+fieldName+" "+getListValues().get(0);
					getListValues().remove(0);
					return stringFilter;
				}
			}
			if(operator.equals(Operator.GROUPBY)) {
				stringFilter = operator+" "+fieldName+" "; // SH - 19/01/2015 - Suppression des ( )
				return stringFilter;
			}
			if (operator.getType()!=Operator.TYPE_SQL_FUNCTION&&operator.getType()!=Operator.TYPE_GLOBAL_FUNCTION) stringFilter += operator;
		}
		return stringFilter;
	}

	public static String toString(List<JdbcFilter> filters) {
		String s = "";
		if (filters!=null) {
			for(JdbcFilter f : filters) {
				if (
					f.getOperator().getType() == Operator.TYPE_LOGIC || 
					f.getOperator().getType() == Operator.TYPE_ARITHMETIC
				) { s+=" WHERE "; break; }
			}
			for(JdbcFilter f : filters)	s += f.toString();
		}
		return s;
	}

	public static String toString(String fieldName,List<JdbcFilter> filters) {
		if (filters!=null) {
			for (JdbcFilter f : filters) {
				if (
					f.getOperator().getType()==Operator.TYPE_SQL_FUNCTION &&
					f.getFieldName().equals(fieldName)
				) {
					// FB2012936 - 28/08/2012
					return f.getOperator()+"("+fieldName+")";
				}
			}
		}
		return fieldName;
	}

	public static String toStringGlobalOperators(List<JdbcFilter> filters){
		String s = "";
		if (filters!=null)
			for (JdbcFilter f : filters)
				if (f.getOperator().getType()==Operator.TYPE_GLOBAL_FUNCTION)
					s+=f.getOperator(); 
		return s;
	}
	public Operator getOperator() {
		return operator;
	}
	public List<A_Type> getListValues()	{
		if(this.listValues == null)	{ this.listValues = new ArrayList<A_Type>(); }
		return listValues;
	}
	public String getFieldName()
	{
		return fieldName;
	}
}

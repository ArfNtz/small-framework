package fr.toyframework.mapper;
public class MapperException extends Exception {

	private static final long serialVersionUID = -8874542674150956651L;
	private String propertyName;
	private Object propertyValue;
	private String propertyValueToString;
	
	public MapperException(Exception e,String propertyName, Object propertyValue){
		super("[propertyName:"+propertyName+"][propertyValue:"+propertyValue+"]",e);
		this.propertyValueToString = "";
		if (propertyValue!=null&&propertyValue.getClass().isArray()) for (Object o : (Object[])propertyValue) this.propertyValueToString+=o.toString();
		this.propertyName=propertyName;
		this.propertyValue=propertyValue;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public Object getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getPropertyValueToString() {
		return propertyValueToString;
	}
	public void setPropertyValueToString(String propertyValueToString) {
		this.propertyValueToString = propertyValueToString;
	}
}
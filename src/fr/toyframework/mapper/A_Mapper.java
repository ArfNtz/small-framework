package fr.toyframework.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class A_Mapper {

	protected Map<String,String> map;
	protected Map<String,String> reverseMap;	
	
	public abstract void open() throws MapperException;
	public abstract void close() throws MapperException;
	public abstract void commit() throws MapperException;
	public abstract void rollback() throws MapperException;

	public abstract void init(Object pm) throws MapperException;
	public abstract Object map(Object pm) throws MapperException;
	
	protected final void addMap(String modelAttribute, String toField) {
		getMap().put(modelAttribute, toField);
		getReverseMap().put(toField, modelAttribute);
	}
	protected final String getMap(String modelAttribute){
		String toField = getMap().get(modelAttribute); 
		return toField==null?modelAttribute:toField;
	}
	protected final List<String> getMap(List<String> modelAttributes){
		List<String> l = new ArrayList<String>();
		Iterator<String> i_ = modelAttributes.iterator();
		while (i_.hasNext()) l.add(getMap(i_.next()));
		return l;
	}
	protected final String getReverseMap(String toField){
		String modelAttribute = getReverseMap().get(toField); 
		return modelAttribute==null?toField:modelAttribute;
	}
	protected final List<String> getReverseMap(List<String> toFields){
		List<String> l = new ArrayList<String>();
		Iterator<String> i_ = toFields.iterator();
		while (i_.hasNext()) l.add(getReverseMap(i_.next()));
		return l;
	}
	protected final Map<String, String> getMap() {
		if (map==null) map = new HashMap<String,String>();
		return map;
	}
	protected final Map<String, String> getReverseMap() {
		if (reverseMap==null) reverseMap = new HashMap<String,String>();
		return reverseMap;
	}
}

package fr.toyframework.connector.api;

import java.io.Serializable;

import fr.toyframework.type.format.MessageFormat;


public class ServiceMessage implements Serializable {

	private static final long serialVersionUID = 5961137906861763652L;

	public static int TYPE_ERROR = 0;
	public static int TYPE_INFO = 1;
	public static int TYPE_USER_INPUT = 2;
	
	private static String[] types = new String[]{"TYPE_ERROR","TYPE_INFO","TYPE_USER_INPUT"};

	private String id;
	private int type;
	private String text;

	public ServiceMessage() { }

	public ServiceMessage(String id,int type,String pattern,Object[] args) {
		this.id = id;
		this.type = type;
		this.text = pattern==null?null:MessageFormat.format(pattern,args);
	}

	public String getId() {	return id;	}
	public int getType() { return type; }
	public String getText() {	return text;	}

	public String toString() { return types[type]+";"+id+";"+text; }

}

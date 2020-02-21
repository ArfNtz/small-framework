package fr.toyframework.connector.api;
public class OutilException extends Exception {
	private static final long serialVersionUID = 8633253281113646012L;
	public OutilException(){super();}
	public OutilException(String message){super(message);}
	public OutilException(Throwable t,String message){super(message,t);}
	public OutilException(Throwable t){super(t);}
}
package fr.toyframework.connector.api;
public class ServiceException extends Exception {
	private static final long serialVersionUID = -5179273274779932843L;
	public ServiceException(){super();}
	public ServiceException(String message){super(message);}
	public ServiceException(Throwable t,String message){super(message,t);}
	public ServiceException(Throwable t){super(t);}
}
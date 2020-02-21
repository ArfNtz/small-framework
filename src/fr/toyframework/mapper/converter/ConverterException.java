package fr.toyframework.mapper.converter;
public class ConverterException extends Exception {
	private static final long serialVersionUID = 5421171496618439417L;
	public ConverterException(String message){super(message);}
	public ConverterException(String message,Throwable t){super(message,t);}
}
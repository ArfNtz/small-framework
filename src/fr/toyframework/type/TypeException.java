package fr.toyframework.type;
public class TypeException extends Exception {
	private static final long serialVersionUID = -8874542674150956651L;
	public TypeException(String message){super(message);}
	public TypeException(String message,Throwable t){super(message,t);}
}
package fr.toyframework.controler;
public class ControlerException extends Exception {
private static final long serialVersionUID = -5179273274779932843L;
public ControlerException(){super();}
public ControlerException(String message){super(message);}
public ControlerException(Throwable t,String message){super(message,t);}
public ControlerException(Throwable t){super(t);}
}
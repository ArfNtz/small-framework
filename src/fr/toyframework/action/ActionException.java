package fr.toyframework.action;
public class ActionException extends Exception {
private static final long serialVersionUID = -5179273274779932843L;
public ActionException(){super();}
public ActionException(String message){super(message);}
public ActionException(Throwable t,String message){super(message,t);}
public ActionException(Throwable t){super(t);}
}
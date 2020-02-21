package fr.toyframework.view;
public class ViewException extends Exception {
	private static final long serialVersionUID = -6938326994800473795L;
	public ViewException(String message){super(message);}
	public ViewException(Throwable t,String message){super(message,t);}
	public ViewException(Throwable t){super(t);}
}

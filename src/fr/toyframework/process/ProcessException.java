package fr.toyframework.process;
public class ProcessException extends Exception {
	private static final long serialVersionUID = -6938326994800473795L;
	public ProcessException(String message){super(message);}
	public ProcessException(Throwable t,String message){super(message,t);}
	public ProcessException(Throwable t){super(t);}
}

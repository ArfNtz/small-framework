package fr.toyframework.model;

import fr.toyframework.connector.api.ServiceMessage;

public class Message extends ServiceMessage {
	private static final long serialVersionUID = 6256476109527425760L;

	public static String MESSAGE_ID_EXCEPTION = "exception";
	public static String MESSAGE_ID_UNAUTHORIZED = "unauthorized";
	public static String MESSAGE_ID_UNINSTALLED = "uninstalled";
	
	private String pattern;
	private String className;
	private Object[] arguments;
	private A_SessionBean sessionBean;
	private Throwable t;
	private String idAction_target;
	private String targetActionParametersQueryString;

	/**
	 * @param type
	 * @param pattern 
	 * @param className : facultatif (null)
	 * @param args : facultatif, utilis�s dans pattern (null)
	 * @param sb : facultatif, session utilisateur utilis�e par exemple pour recomposer des liens suivant le contexte du message, ne pas oublier de faire un sessionBean.clone() si l'on veut que l'objet stock� dans le message n'�volue pas avec la session. (null)
	 */
	public Message(String id,int type, String pattern, String className, Object[] args,A_SessionBean sb,Throwable t) {
		super(id,type,pattern,args);
		this.pattern = pattern;
		this.className = className;
		this.arguments = args;
		this.sessionBean = sb;
		this.t = t;
	}
	public Message(String id,int type,String pattern,Object[] args) {
		this(id,type,pattern,null,args,null,null);
	}
	/** 
	 * @param idAction_target : permet par exemple de mettre un lien vers une action cible
	 * @param targetActionParametersQueryString : param�tre � passer � l'action cible
	 */
	public Message(String id,int type, String pattern, Object[] args,A_SessionBean sb,Throwable t,String idAction_target, String targetActionParametersQueryString) {
		this(id,type,pattern,null,args,sb,t);
		this.idAction_target=idAction_target;
		this.targetActionParametersQueryString=targetActionParametersQueryString;
	}

	public A_SessionBean getSessionBean() { return sessionBean; }
	public Object[] getArguments() { return arguments; }
	public String getClassName() { return className; }
	public String getPattern(){ return pattern; }
	public Throwable getThrowable(){ return t; }
	public String getIdAction_target() { return idAction_target; }
	public String getTargetActionParametersQueryString() { return targetActionParametersQueryString; }

}

package fr.toyframework.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import fr.toyframework.connector.api.ServiceSessionBean;
import fr.toyframework.controler.Thread;

public abstract class A_SessionBean extends ServiceSessionBean implements Cloneable, Serializable {

	private static final long serialVersionUID = -5320781791164165157L;

	private static String bundleFilename = A_SessionBean.class.getName();

	private static String KEY_language = "language";
	private static String KEY_country = "country";

	public static Locale defaultLocale; 

	private transient Map<String,Thread> threads;

	private Set<String> states;
	private Locale locale;
	private String nextThreadId = null;
	private String nextActionId = null;
	private Map<String,Object> nextMap = null;
	private A_ActionModel nextActionModel = null;
	private boolean next = false;

	private String previousThreadId = null;
	private String previousActionId = null;
	private Map<String,Object> previousMap = null;
	private A_ActionModel previousActionModel = null;
	private boolean previous = false;

	public final void setNextProcess(String idThread,/*String idProcess,*/String idAction,Map<String,Object> map,A_ActionModel m){
		this.nextThreadId	=idThread;
		this.nextActionId	=idAction;
		this.nextMap		=map;
		this.nextActionModel=m;
		this.next			=true;
	}
	public final void setPreviousProcess(String idThread,/*String idProcess,*/String idAction,Map<String,Object> map,A_ActionModel m){
		this.previousThreadId	=idThread;
		this.previousActionId	=idAction;
		this.previousMap		=map;
		this.previousActionModel=m;
		this.previous=true;
	}

	public final String getNextThreadId() { return nextThreadId; }
	public String getNextActionId() { return nextActionId; }
	public final Map<String,Object> getNextMap() { return nextMap; }
	public A_ActionModel getNextActionModel() {	return nextActionModel; }
	public String getPreviousThreadId() {return previousThreadId;}
	public String getPreviousActionId() { return previousActionId; }
	public Map<String, Object> getPreviousMap() {return previousMap;}
	public A_ActionModel getPreviousActionModel() {	return previousActionModel;	}

	public boolean isNext() { return next; }
	public void setNext(boolean next) {	this.next = next; }
	public boolean isPrevious() { return previous; }
	public void setPrevious(boolean previous) {	this.previous = previous; }

	public void logout() {
		setIdUser(null);
		setUserName(null);
		setIdGroup(null);
		setGroupName(null);
	}
	public static Locale getDefaultLocale(){
		if (defaultLocale==null) { 
			String language = ResourceBundle.getBundle(bundleFilename,Locale.ROOT).getString(KEY_language);
			String country = ResourceBundle.getBundle(bundleFilename,Locale.ROOT).getString(KEY_country);
			defaultLocale = new Locale(language,country);
		}
		return defaultLocale;
	}
	
	public static void setDefaultLocale(String language, String country){
		defaultLocale = new Locale(language,country);
	}

	public Locale getLocale() {
		if (locale==null) locale = getDefaultLocale();
		return locale;
	}
	public void setLocale(java.util.Locale newLocale) {
		locale = newLocale;
	}
	public Map<String, Thread> getThreads() {
		if (threads==null) threads = new HashMap<String, Thread>();
		return threads;
	}
	public void setThreads(Map<String, Thread> threads) {
		this.threads = threads;
	}
	public Set<String> getStates() {
		if (states==null) states = new HashSet<String>();
		return states;
	}
	public void setStates(HashSet<String> set) {
		states = set;
	}
}

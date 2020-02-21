package fr.toyframework.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;

import fr.toyframework.Log;
import fr.toyframework.connector.api.ServiceException;
import fr.toyframework.controler.NotInstalledException;
import fr.toyframework.controler.Thread;
import fr.toyframework.controler.UnauthorizedAccessException;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.model.A_ActionModel;
import fr.toyframework.model.A_SessionBean;
import fr.toyframework.model.BlankActionModel;
import fr.toyframework.model.Message;
import fr.toyframework.process.A_Process;
import fr.toyframework.process.BlankProcess;
import fr.toyframework.process.ProcessException;
import fr.toyframework.type.TypeException;
import fr.toyframework.type.format.MessageFormat;
import fr.toyframework.view.A_View;
import fr.toyframework.view.ViewException;

public abstract class A_Action {

	//statics
	public static String ACTION_NONE = BlankAction.class.getName();

	//attributes
	private A_Process process;
	private A_ActionModel model;
	private A_View view;
	private boolean newAction = false;
	private A_SessionBean sessionBean;
	private Map<String,Message> messages;

	//abstract methods
	public abstract A_View createView();
	public abstract A_ActionModel createModel();
	public abstract String getProcessClassName();
	public abstract String getProcessMethodName();
	public abstract void checkSystem(A_ActionModel am) throws MapperException, ProcessException;
	public abstract void contextSystem(A_ActionModel am) throws MapperException, ProcessException;
	public abstract void context(A_ActionModel am) throws MapperException, ProcessException;
	public abstract void checkAccess(A_ActionModel am) throws MapperException, ProcessException, UnauthorizedAccessException;
	public abstract void init(A_ActionModel am) throws MapperException, ProcessException;
	public abstract void check(A_ActionModel am) throws MapperException, ProcessException;
	public abstract void reset(A_ActionModel am) throws MapperException, ProcessException;
	public abstract boolean isNext();

	public String getBundleFilename() { return getClass().getName(); }

	public final synchronized void invoke(Thread t,A_SessionBean sessionBean,String logId) throws ActionException, MapperException, ProcessException, TypeException, ServiceException {
		process = getProcess(t,logId);
		setSessionBean(sessionBean);
		if (getMessages().isEmpty()) {
			try {
				process.getMapper().open();
				checkSystem(getActionModel());
				contextSystem(getActionModel());
				checkAccess(getActionModel());
				context(getActionModel());
				if (isNewAction()) { setNewAction(false);init(getActionModel()); }
				check(getActionModel());
				if (getMessages().isEmpty()) {
					process.reset(this);
					process.contextSystem(this);
					process.context(this);
					if (process.isNewProcess()) { process.setNewProcess(false);process.init(this); }
					if (!(this instanceof BlankAction)) {
						String method = getProcessMethodName();
						if (method==null) method = A_Process.METHOD_PROCESS; 
						Log.logAction.finer(logId+method);
						process.getClass().getMethod(method,new Class[]{A_Action.class}).invoke(process,new Object[]{this});
					}
					process.getMapper().commit();
				}
				process.getMapper().close();
			} catch (UnauthorizedAccessException e){
				handleInvokeError(e,logId);
			} catch (ProcessException e){
				handleInvokeError(e,logId);
			} catch (IllegalArgumentException e) {
				handleInvokeError(e,logId);
			} catch (SecurityException e) {
				handleInvokeError(e,logId);
			} catch (IllegalAccessException e) {
				handleInvokeError(e,logId);
			} catch (InvocationTargetException e) {
				handleInvokeError(e.getCause(),logId);
			} catch (NoSuchMethodException e) {
				handleInvokeError(e,logId);
			}
		}
	}
	private void handleInvokeError(Throwable t,String logId) throws MapperException, ProcessException {
		Log.logControler.log(Level.FINER, logId+t.getMessage(), t);
		String idMessage = Message.MESSAGE_ID_EXCEPTION;
		if (t instanceof UnauthorizedAccessException) idMessage = Message.MESSAGE_ID_UNAUTHORIZED;
		else if (t instanceof NotInstalledException) idMessage = Message.MESSAGE_ID_UNINSTALLED;
		getMessages().put(idMessage,new Message(idMessage,Message.TYPE_ERROR,t.getMessage(),process.getClass().getName(),null,getSessionBean(),t));
		process.getMapper().rollback();
		process.getMapper().close();
	}
	protected A_Process getProcess(Thread t,String logId) throws ActionException {
		try {
			String idProcess = getProcessClassName();
			if (idProcess==null) idProcess = BlankProcess.class.getName();
			process = t.getProcess();
			if (process==null||!process.getClass().getName().trim().equals(idProcess)) {
				process = (A_Process)Class.forName(idProcess).newInstance();
				process.setNewProcess(true);
				t.setProcess(process);
				Log.logControler.finer(logId+process.getClass().getName());
			}
		} catch (InstantiationException e) {
			throw new ActionException(e);
		} catch (IllegalAccessException e) {
			throw new ActionException(e);
		} catch (ClassNotFoundException e) {
			throw new ActionException(e);
		}
		return process;
	}
	
	// confort
	public final void addMessage(Message m) {
		getMessages().put(m.getId(),m);
	}
	
	// FB2013561 - 01/07/2013
	public final void addMessages(List<Message> ms) {
		for (Iterator<Message> iterator = ms.iterator(); iterator.hasNext();) {
			addMessage(iterator.next());			
		}
	}
	
	// getters and setters
	public A_Process getProcess() {
		return process;
	}
	public void setActionModel(A_ActionModel am) {
		this.model = am;
	}
	public A_ActionModel getActionModel() {
		if (model==null) {
			model = createModel();
			if (model==null) model = new BlankActionModel();
			Log.logControler.finer(model.getClass().getName());
		}
		return model;
	}
	public A_View getView(String logId) throws ViewException {
		view = createView();
		Log.logControler.finer(logId+view.getClass().getName());
		view.init(this);
		return view;
	}
	public A_SessionBean getSessionBean() {
		return sessionBean;
	}
	public final void setSessionBean(A_SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	public final Map<String,Message> getMessages() {
		if (messages==null) messages = new LinkedHashMap<String,Message>();
		return messages;
	}
	public final void setMessages(Map<String,Message> al) {
		messages = al;
	}
	// FB2013119 - 01/03/2013
	protected final String r(String key,Locale l, String bundleFilename){
		return ResourceBundle.getBundle(bundleFilename,l).getString(key);
	}
	protected final String r(String key,Locale l){
		return r(key, l, getBundleFilename());
		//else return ResourceBundle.getBundle(getBundleFilename()).getString(key);
	}
	protected final String r(String key,Locale l,Object args){
		return MessageFormat.format(r(key,l),args);
	}
	public boolean isNewAction() {
		return newAction;
	}
	public void setNewAction(boolean newAction) {
		this.newAction = newAction;
	}
}
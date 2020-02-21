package fr.toyframework.controler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;

import fr.toyframework.Log;
import fr.toyframework.action.A_Action;
import fr.toyframework.action.ActionException;
import fr.toyframework.connector.api.ServiceException;
import fr.toyframework.mapper.BeanUtils;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.mapper.converter.ConverterException;
import fr.toyframework.mapper.converter.Converters;
import fr.toyframework.model.A_ActionModel;
import fr.toyframework.model.A_SessionBean;
import fr.toyframework.model.Message;
import fr.toyframework.process.ProcessException;
import fr.toyframework.type.TypeException;

public class ActionControler {

	private static String bundleFilename = ActionControler.class.getName();

	private static String KEY_IMPOSSIBLE_THREAD = "impossibleThread";
	private static String KEY_ID_ACTION = "idAction";
	private static String KEY_OTHER_THREADS = "other_threads";

	public static String THREAD_DEFAULT = "thread_default";
	public static String THREAD_NAVIGATION = "thread_navigation";
	public static String THREAD_CONTEXT = "thread_context";
	public static String THREAD_MODAL = "thread_modal";

	private static Set<String> possibleThreads;
	
	public static String ID_ACTION_DEFAULT = ResourceBundle.getBundle(bundleFilename).getString(KEY_ID_ACTION);

	static final public A_Action service(String idThread,String idAction,Map<String,Object> map,A_ActionModel request,A_SessionBean sessionBean) throws ControlerException, ActionException, MapperException, ProcessException, TypeException, ServiceException {
		long t1 = System.currentTimeMillis();
		A_Action a = getAction(idThread, idAction, sessionBean);
		idAction = a.getClass().getName();
		String logId = sessionBean.getIdDevice()+Log.separator+sessionBean.getIdUser()+Log.separator+sessionBean.getUserName()+Log.separator+idAction;
		Thread t = getThread(idThread, sessionBean,logId);
		a.getMessages().clear();
		if (request!=null) a.setActionModel(request);
		else if (map!=null) a.getMessages().putAll(map(map,a.getActionModel(),sessionBean.getLocale(),logId));
		a.invoke(t,sessionBean,logId);
		long d = System.currentTimeMillis()-t1;
		Log.logPerf.log(Level.FINEST,"ACTION"+Log.separator+logId+Log.separator+d);
		if (a.isNext()&&sessionBean.isNext()) {
			Log.logControler.finer(logId+Log.separator+"next");
			a = service(sessionBean.getNextThreadId(),sessionBean.getNextActionId(),sessionBean.getNextMap(),sessionBean.getNextActionModel(),sessionBean);
			sessionBean.setNext(false);
		}
		return a;
	}

	private static final A_Action getAction(String idThread,String idAction,A_SessionBean sessionBean) throws ControlerException {
		String logId = sessionBean.getIdDevice()+Log.separator+sessionBean.getIdUser()+Log.separator+sessionBean.getUserName()+Log.separator;
		Thread t = getThread(idThread, sessionBean,logId);
		if(idAction==null||idAction.trim().equals("")) {
			if (t.getAction()!=null) idAction=t.getAction().getClass().getName().trim();
			else idAction = ID_ACTION_DEFAULT;
		}
		if (t.getAction()==null||!t.getAction().getClass().getName().trim().equals(idAction)) {
			t.setAction(null);
			try {
				t.setAction((A_Action)Class.forName(idAction).newInstance());
			} catch (InstantiationException e) {
				throw new ControlerException(e);
			} catch (IllegalAccessException e) {
				throw new ControlerException(e);
			} catch (ClassNotFoundException e) {
				throw new ControlerException(e);
			}
			t.getAction().setNewAction(true);
			Log.logControler.finer(logId+idAction);
		}
		A_Action a = t.getAction();
		return a;
	}

	private static final Map<String,Message> map(Map<String,Object> map,Object bean,Locale l,String logId) {
		Map<String,Message> messages = new HashMap<String,Message>();
		Iterator<String> i = map.keySet().iterator();
		String name;
		Object value;
		while (i.hasNext()) {
			name = (String)i.next();
			value = map.get(name);
			try {
				BeanUtils.map(name,value,bean,Converters.getAllConverters(),true,BeanUtils.CONNECTOR_UI,l);
			} catch (MapperException t) {
				Log.logControler.log(Level.FINER, logId+t.getMessage(), t);
				String pattern = t.getMessage();
				String idMessage = t.getPropertyName();
				int message_type = Message.TYPE_ERROR;
				String className = bean==null?"":bean.getClass().getName();
				if (t.getCause()!=null&&t.getCause() instanceof ConverterException) {
					pattern = t.getCause().getMessage();
					message_type = Message.TYPE_USER_INPUT;
				}
				messages.put(idMessage,	new Message(idMessage,message_type,pattern,className,new Object[]{idMessage,t.getPropertyValueToString()},null,t));
			}
		}
		return messages;
	}
	private static final Thread getThread(String idThread,A_SessionBean sessionBean,String logId) throws ControlerException {
		if (idThread==null) {
			idThread = sessionBean.getIdThread();
			if (idThread==null) idThread = THREAD_DEFAULT;
		}
		if (!getPossibleThreads(logId).contains(idThread)) throw new ControlerException(ResourceBundle.getBundle(bundleFilename).getString(KEY_IMPOSSIBLE_THREAD)+idThread);
		Log.logControler.finer(logId+"Thread:"+idThread);
		Thread t = sessionBean.getThreads().get(idThread);
		if (t==null) {
			t = new Thread();
			sessionBean.getThreads().put(idThread, t);
		}
		return t;
	}
	private static final Set<String> getPossibleThreads(String logId) {
		if (possibleThreads==null) {
			possibleThreads = new HashSet<String>();
			possibleThreads.addAll(Arrays.asList(THREAD_DEFAULT,THREAD_CONTEXT,THREAD_MODAL,THREAD_NAVIGATION));
			possibleThreads.addAll(Arrays.asList(ResourceBundle.getBundle(bundleFilename).getString(KEY_OTHER_THREADS).split(",")));
			Log.logControler.finer(logId+possibleThreads);
		}
		return possibleThreads;
	}
}

package fr.toyframework.connector.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Level;

import fr.toyframework.Log;
import fr.toyframework.action.A_Action;
import fr.toyframework.action.ActionException;
import fr.toyframework.controler.ActionControler;
import fr.toyframework.controler.ControlerException;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.model.A_ActionModel;
import fr.toyframework.model.A_SessionBean;
import fr.toyframework.process.ProcessException;
import fr.toyframework.type.TypeException;

public class ActionService {
	

	public static void init() throws ServiceException {
		try {
			if (!Log.isInitDone()) Log.init(); //WV - 26072012
		} catch (SecurityException e) {
			throw new ServiceException(e);
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	
	public static A_ServiceResponse call(String actionClassName,Map<String,Object> map,A_ActionModel request,String responseClassName,A_SessionBean sessionBean) throws ServiceException {
		try {
			A_Action a = ActionControler.service(null,actionClassName,map,request,sessionBean);
			A_ServiceResponse r = null;
			if (responseClassName==null) r = new DefaultServiceResponse(a); 
			else r = (A_ServiceResponse)Class.forName(responseClassName).getConstructor(new Class [] {A_Action.class}).newInstance(a);
			return r;
		} catch (ActionException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		} catch (MapperException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ProcessException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ControlerException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		} catch (TypeException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		} catch (SecurityException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		} catch (IllegalArgumentException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		} catch (InstantiationException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		} catch (IllegalAccessException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		} catch (InvocationTargetException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		} catch (NoSuchMethodException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		} catch (ClassNotFoundException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
}

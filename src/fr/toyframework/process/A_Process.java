package fr.toyframework.process;


import java.util.Locale;
import java.util.ResourceBundle;

import fr.toyframework.Log;
import fr.toyframework.action.A_Action;
import fr.toyframework.action.ActionException;
import fr.toyframework.connector.api.ServiceException;
import fr.toyframework.controler.ControlerException;
import fr.toyframework.mapper.A_Mapper;
import fr.toyframework.mapper.BlankMapper;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.model.A_ProcessModel;
import fr.toyframework.model.BlankProcessModel;
import fr.toyframework.type.TypeException;
import fr.toyframework.type.format.MessageFormat;

public abstract class A_Process {
	
	public static final String METHOD_PROCESS = "process";
	
	private A_ProcessModel model;
	private A_Mapper mapper;
	private boolean newProcess = false;
	
	public abstract A_ProcessModel createModel();
	public abstract A_Mapper createMapper() throws ProcessException;
	public abstract void init			(A_Action a) throws ProcessException,MapperException,TypeException,ServiceException;
	public abstract void contextSystem	(A_Action a) throws ProcessException,MapperException,TypeException,ServiceException;
	public abstract void context		(A_Action a) throws ProcessException,MapperException,TypeException,ServiceException;
	public abstract void reset			(A_Action a) throws ProcessException,MapperException,TypeException,ServiceException;
	public abstract void process		(A_Action a) throws CloneNotSupportedException,ProcessException,MapperException,TypeException,ServiceException, ControlerException, ActionException;
	
	public String getBundleFilename() { return getClass().getName(); }
	
	public A_ProcessModel getProcessModel() {
		if (model==null) {
			model = createModel();
			if (model==null) model = new BlankProcessModel();
			Log.logControler.finer(model.getClass().getName());
		}
		return model;
	}
	public A_Mapper getMapper() throws MapperException, ProcessException {
		if (mapper==null) {
			mapper = createMapper();
			if (mapper==null) mapper = new BlankMapper();
			Log.logControler.finer(mapper.getClass().getName());
		}
		return mapper;
	}
	// FB2013119 - 01/03/2013
	protected final String r(String key,Locale l, String bundleFilename){
		return ResourceBundle.getBundle(bundleFilename,l).getString(key);
	}
	// get resource for process (ex: messages) //
	protected final String r(String key,Locale l){
		return ResourceBundle.getBundle(getBundleFilename(),l).getString(key);
		//else return ResourceBundle.getBundle(getBundleFilename()).getString(key);
	}
	protected final String r(String key,Locale l,Object[] args){
		return MessageFormat.format(r(key,l),args);
	}
	
	public final boolean isNewProcess() {
		return newProcess;
	}
	public final void setNewProcess(boolean newProcess) {
		this.newProcess = newProcess;
	}
	
}

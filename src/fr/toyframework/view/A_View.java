package fr.toyframework.view;

import java.util.Locale;
import java.util.ResourceBundle;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.format.MessageFormat;

public abstract class A_View {

	private String bundleFilename = getClass().getName();

	public abstract void init(A_Action a) throws ViewException;
	public abstract void render(A_Action a) throws ViewException;

	protected String r(String key,Locale l){
		return ResourceBundle.getBundle(bundleFilename,l).getString(key);
		//else return ResourceBundle.getBundle(bundleFilename).getString(key);
	}
	protected String r(String key,Locale l,Object args){
		return MessageFormat.format(r(key,l),args);
	}
}

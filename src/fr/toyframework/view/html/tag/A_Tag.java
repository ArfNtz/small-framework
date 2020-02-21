package fr.toyframework.view.html.tag;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.TypeException;
import fr.toyframework.type.format.MessageFormat;
import fr.toyframework.view.ViewException;

public abstract class A_Tag implements I_Tag, Cloneable {

	private String bundleFilename = getClass().getName();

	private String name;
	public String id;
	public String style_class;
	/**
	 * TODO constraints : La syntaxe de cette cha�ne d�pendra de l'outil de controle utilis� (ex: javascript : obligatoire/regexp ...) 
	 */
	protected String constraints;

	private String other_attributes="";

	protected A_Type data;

	public A_Tag(String name,String id,String style_class,A_Type data){
		this.name=name;
		this.id=id;
		this.data=data;
		this.style_class=style_class;
	}
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public A_Tag addAttribute(String name,String value) {
		other_attributes += " "+name;
		if (value!=null) other_attributes += "=\""+value+"\"";
		return this;
	}
	public A_Tag setAttribute(String name,String value) {
		other_attributes = name;
		if (value!=null) other_attributes += "=\""+value+"\"";
		return this;
	}
	public A_Tag clear() {
		other_attributes = "";
		return this;
	}
	protected void print(HttpServletResponse res,String s) throws ViewException {
		try {
			res.getWriter().print(s);
		} catch (IOException e) {
			throw new ViewException(e);
		}
	}
	public void print(A_Action a, HttpServletRequest req, HttpServletResponse res) throws ViewException {
		try {
			build(a);
		} catch (TypeException e) {
			throw new ViewException(e);
		}
		printStart(a,req,res);
		printEnd(a,req,res);
	}
	public final void printStart(A_Action a,HttpServletRequest req, HttpServletResponse res) throws ViewException {
		if (name!=null) {
			print(res,"<"+name);
			if (id!=null) print(res," id=\""+id+"\" name=\""+id+"\"");
			if (style_class!=null) print(res," class=\""+style_class+"\"");
			printStartAttributes(a, req, res);
			print(res,">");
		}
	}
	public final void printStartAttributes(A_Action a,HttpServletRequest req, HttpServletResponse res) throws ViewException {
		print(res," "+other_attributes);
	}
	public final void printEnd(A_Action a,HttpServletRequest req, HttpServletResponse res) throws ViewException {
		if (name!=null){
			print(res,"</"+name+">");
		}
	}
	public A_Type getData() {
		return data;
	}
	public void setData(A_Type data) {
		this.data = data;
	}
	protected String r(String key,Locale l){
		return ResourceBundle.getBundle(bundleFilename,l).getString(key);
	}
	protected String r(String key,Locale l,Object[] args){
		return MessageFormat.format(r(key,l),args);
	}

}

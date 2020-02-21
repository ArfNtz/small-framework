package fr.toyframework.view.html.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.ViewException;

public abstract class A_CompositeTag extends A_Tag implements I_CompositeTag,Cloneable {

	protected List<A_Tag> components = new ArrayList<A_Tag>();

	public A_CompositeTag(String name,String id,String style_class,A_Type data){
		super(name,id,style_class,data);
	}
	
	public abstract void build(A_Action a) throws ViewException, TypeException;
	
	public Object clone() throws CloneNotSupportedException {
		Object o = super.clone();
		List<A_Tag> l = new ArrayList<A_Tag>();
		for (A_Tag t : components) l.add((A_Tag)t.clone()); 
		((A_CompositeTag)o).components = l;
		return o;
	}
	public void print(A_Action a, HttpServletRequest req, HttpServletResponse res) throws ViewException {
		try { build(a); } catch (TypeException e) {throw new ViewException(e);}
		printStart(a,req,res);
		printData(a,req,res);
		for (A_Tag tag : components) tag.print(a,req,res);
		printEnd(a,req,res);
	}
	private final void printData(A_Action a, HttpServletRequest req, HttpServletResponse res) throws ViewException {
		try {
			print(res,data!=null?data.getUiString(a.getSessionBean().getLocale()):"");
		} catch (TypeException e) {
			throw new ViewException(e);
		}		
	}
	public A_CompositeTag addFirst(A_Tag tag) throws ViewException {
		try {
			if (tag!=null) components.add(0,(A_Tag)tag.clone());
			return this;
		} catch (CloneNotSupportedException e) {
			throw new ViewException(e);
		}
	}
	public A_CompositeTag add(A_Tag tag) throws ViewException {
		try {
			if (tag!=null) components.add((A_Tag)tag.clone());
			return this;
		} catch (CloneNotSupportedException e) {
			throw new ViewException(e);
		}
	}
	public A_CompositeTag set(A_Tag tag) throws ViewException {
		try {
			components.clear();
			if (tag!=null) components.add((A_Tag)tag.clone());
			return this;
		} catch (CloneNotSupportedException e) {
			throw new ViewException(e);
		}
	}
	public A_CompositeTag clear() {
		super.clear();
		components.clear();
		return this;
	}
}
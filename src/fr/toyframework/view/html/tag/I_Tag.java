package fr.toyframework.view.html.tag;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.ViewException;

public interface I_Tag {

	public abstract void build(A_Action a) throws ViewException,TypeException;
	public abstract void print(A_Action a, HttpServletRequest req, HttpServletResponse res) throws ViewException;
	public abstract void printStart(A_Action a,HttpServletRequest req, HttpServletResponse res)throws ViewException;
	public abstract void printEnd(A_Action a,HttpServletRequest req, HttpServletResponse res)throws ViewException;

}

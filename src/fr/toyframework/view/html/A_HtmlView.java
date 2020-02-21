package fr.toyframework.view.html;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.toyframework.action.A_Action;
import fr.toyframework.connector.servlet.HtmlServlet;
import fr.toyframework.model.A_SessionBean;
import fr.toyframework.view.A_HttpView;
import fr.toyframework.view.ViewException;
import fr.toyframework.view.html.tag.A_Tag;

public abstract class A_HtmlView extends A_HttpView {

	public abstract A_Tag build(A_Action a) throws ViewException;

	protected void print(A_Action a, HttpServletRequest req, HttpServletResponse res) throws ViewException {
		A_SessionBean sb = a.getSessionBean();
		res.setLocale(sb.getLocale());
		res.setContentType("text/html; charset=\""+HtmlServlet.charset+"\"");
		res.setHeader("text/html","text/html; charset=\""+HtmlServlet.charset+"\"");
/*
		try {
			//<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">
			res.getWriter().print("");
		} catch (IOException e) {
			throw new ViewException(e);
		}
*/
		build(a).print(a,req,res);
		
	}
}

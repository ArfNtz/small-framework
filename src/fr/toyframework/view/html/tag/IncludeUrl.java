package fr.toyframework.view.html.tag;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.toyframework.action.A_Action;
import fr.toyframework.connector.servlet.KEYS;
import fr.toyframework.view.ViewException;

public class IncludeUrl extends A_Tag {

	private String url;
	private String idView;
	
	public IncludeUrl(String url,String idView) {
		super(null,null,null,null);
		this.setUrl(url);
		this.setIdView(idView);
	}
	public void build(A_Action a) throws ViewException {
	}
	public void print(A_Action a,HttpServletRequest req, HttpServletResponse res) throws ViewException {
		try {
			req.setAttribute(KEYS.REQUEST_ACTION_MODEL, a.getActionModel());
			req.setAttribute(KEYS.REQUEST_PROCESS_MODEL, a.getProcess().getProcessModel());
			req.setAttribute(KEYS.REQUEST_MESSAGES, a.getMessages());
			req.setAttribute(KEYS.REQUEST_ID_VIEW, getIdView());
			req.getRequestDispatcher(getUrl()).include(req,res);
		} catch (ServletException e) {
			throw new ViewException(e);
		} catch (IOException e) {
			throw new ViewException(e);
		}
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public String getIdView() {
		return idView;
	}
	public void setIdView(String idView) {
		this.idView = idView;
	}
}

package fr.toyframework.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.toyframework.action.A_Action;
import fr.toyframework.connector.servlet.KEYS;

public abstract class A_HttpIncludeView extends A_HttpView {

	public abstract String getUrl();
	
	public void render(A_Action a) throws ViewException {
		try {
			getReq().setAttribute(KEYS.REQUEST_ACTION_MODEL, a.getActionModel());
			getReq().setAttribute(KEYS.REQUEST_PROCESS_MODEL, a.getProcess().getProcessModel());
			getReq().setAttribute(KEYS.REQUEST_MESSAGES, a.getMessages());
			getReq().setAttribute(KEYS.REQUEST_ID_VIEW, this.getClass().getName());
			getReq().getRequestDispatcher(getUrl()).include(getReq(),getRes());
		} catch (ServletException e) {
			throw new ViewException(e);
		} catch (IOException e) {
			throw new ViewException(e);
		}
	}
	protected void print(A_Action p, HttpServletRequest req, HttpServletResponse res) throws ViewException {
	}

}

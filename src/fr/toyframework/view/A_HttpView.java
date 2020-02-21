package fr.toyframework.view;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.toyframework.action.A_Action;

public abstract class A_HttpView extends A_View {

	private HttpServletRequest req;
	private HttpServletResponse res;
	
	protected abstract void print(A_Action a,HttpServletRequest req, HttpServletResponse res) throws ViewException;
	
	public void render(A_Action a) throws ViewException {
		print(a,req,res);
	}

	public HttpServletRequest getReq() {
		return req;
	}

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}

	public HttpServletResponse getRes() {
		return res;
	}

	public void setRes(HttpServletResponse res) {
		this.res = res;
	}
}

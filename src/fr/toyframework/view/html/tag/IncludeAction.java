package fr.toyframework.view.html.tag;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.toyframework.Log;
import fr.toyframework.action.A_Action;
import fr.toyframework.action.ActionException;
import fr.toyframework.connector.api.ServiceException;
import fr.toyframework.controler.ActionControler;
import fr.toyframework.controler.ControlerException;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.process.ProcessException;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.A_HttpView;
import fr.toyframework.view.ViewException;

public class IncludeAction extends A_Tag {

	private String idThread;
	private String idAction;
	
	public IncludeAction(String idThread,String idAction){
		super(null,null,null,null);
		this.idThread=idThread;
		this.idAction=idAction;
	}
	public void build(A_Action a) throws ViewException {
	}
	public void print(A_Action a,HttpServletRequest req, HttpServletResponse res) throws ViewException {
		try {
			String logId = a.getSessionBean().getIdDevice()+Log.separator+a.getSessionBean().getIdUser()+Log.separator+a.getSessionBean().getUserName()+Log.separator;
			A_Action _a = ActionControler.service(idThread,idAction,null,null,a.getSessionBean());
			A_HttpView v = (A_HttpView)_a.getView(logId);
			v.setReq(req);v.setRes(res);
			v.render(a);
		} catch (ControlerException e) {
			throw new ViewException(e);
		} catch (MapperException e) {
			throw new ViewException(e);
		} catch (ProcessException e) {
			throw new ViewException(e);
		} catch (ActionException e) {
			throw new ViewException(e);
		} catch (TypeException e) {
			throw new ViewException(e);
		} catch (ServiceException e) {
			throw new ViewException(e);
		}
	}
}
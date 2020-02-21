package fr.toyframework.connector.servlet;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.toyframework.Log;
import fr.toyframework.action.A_Action;
import fr.toyframework.controler.ActionControler;
import fr.toyframework.view.A_PrintStreamView;
import fr.toyframework.view.A_View;

public class MessageServlet extends HtmlServlet {
	private static final long serialVersionUID = -4350386039934209460L;
	private Map<String,Object> toMap(String message) {
		Map<String,Object> m = new HashMap<String,Object>();
		//@TODO
		return m;
	}
	private String getIdThread(String message){
		//@TODO
		return null;
	}
	private String getIdAction(String message){
		//@TODO
		return null;
	}
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException {
		String logId = req.getRemoteAddr()+Log.separator;
		try {
		//[listen] / read
			ServletInputStream in = req.getInputStream();
			String message = null;
			if (req.getContentLength()>0) {
				byte[] content = new byte[req.getContentLength()];
				in.read(content);
				message = new String(content);
				System.out.println(message);
			}

		//invoke
			A_Action a = ActionControler.service(
				getIdThread(message),
				getIdAction(message),
				toMap(message),
				null,
				getSessionBean(req)
			);
			A_View v = a.getView(logId);
			((A_PrintStreamView)v).setPrintStream(new PrintStream(res.getOutputStream()));
			v.render(a);

		} catch (Throwable t) {
			if (t instanceof InvocationTargetException) t = ((InvocationTargetException)t).getTargetException();
			Log.logControler.log(Level.FINER, t.getMessage(), t);
		}
	}

}

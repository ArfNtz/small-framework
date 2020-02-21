package fr.toyframework.connector.servlet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.toyframework.Log;
import fr.toyframework.action.A_Action;
import fr.toyframework.action.ActionException;
import fr.toyframework.connector.api.ServiceException;
import fr.toyframework.controler.ActionControler;
import fr.toyframework.controler.ControlerException;
import fr.toyframework.mapper.HttpUtils;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.model.A_SessionBean;
import fr.toyframework.process.ProcessException;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.A_HttpView;
import fr.toyframework.view.ViewException;

public class HtmlServlet extends javax.servlet.http.HttpServlet {
	private static final long serialVersionUID = -1147511308111888816L;
	private static String bundleFilename = HtmlServlet.class.getName();
	private static String KEY_SERVLET_NAME = "servletName";
	private static String KEY_SESSION_BEAN_CLASS_NAME = "sessionBeanClassName";
	private static String KEY_CHARSET = "charset";
	private static String KEY_UPLOAD_FILE_SIZE_MAX = "upload.file.size.max";
	public static String KEY_UPLOAD_FILE_NAME_SUFFIX = "upload.file.name.suffix";
	public static String charset = ResourceBundle.getBundle(bundleFilename).getString(KEY_CHARSET);
	public static int uploadFileSizeMax = new Integer(ResourceBundle.getBundle(bundleFilename).getString(KEY_UPLOAD_FILE_SIZE_MAX));
	private static boolean disabled = new Boolean(ResourceBundle.getBundle(bundleFilename).getString("disable"));
	//private static final String HEADER_REFERER = "Referer";
	
	public void init() throws ServletException {
//		Locale.setDefault(new Locale("",""));
	}
	public static A_SessionBean getSessionBean(HttpServletRequest req) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		A_SessionBean sessionBean = (A_SessionBean)req.getSession().getAttribute(KEYS.SESSION_BEAN);
		if (sessionBean==null) {
			sessionBean = (A_SessionBean)Class.forName(ResourceBundle.getBundle(bundleFilename).getString(KEY_SESSION_BEAN_CLASS_NAME)).newInstance();
			req.getSession().setAttribute(KEYS.SESSION_BEAN,sessionBean);
		}
		sessionBean.setIdDevice(req.getRemoteAddr());
		if (sessionBean.getLocale()==null) sessionBean.setLocale(req.getLocale());
		String idLang = req.getParameter(KEYS.REQUEST_ID_LANG);
		if (idLang!=null) sessionBean.setLocale(new Locale(idLang)); 
		//sessionBean.setReferer(req.getHeader(HEADER_REFERER));
		return sessionBean; 
	}
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException {
		if (disabled) return;
		long t1 = System.currentTimeMillis();
		String logId = req.getRemoteAddr()+Log.separator;
		Log.logConnector.finest(logId+"Request Accept-Language header :"+req.getLocale());
		Log.logConnector.finer(logId+"Request Uri;Querystring : "+req.getRequestURI()+Log.separator+req.getQueryString());
		Log.logConnector.finest(logId+"Request Parameters : "+HttpUtils.printRequestParameters(req));
		Log.logConnector.finest(logId+"Session Attributes :"+HttpUtils.printSessionAttributes(req.getSession()));
		
		setNoCache(res);
		A_SessionBean sessionBean=null;
		try {
			sessionBean = getSessionBean(req);
			logId+=sessionBean.getIdUser()+Log.separator+sessionBean.getUserName()+Log.separator;
			A_Action a = ActionControler.service(
				req.getParameter(KEYS.REQUEST_ID_THREAD),
				req.getParameter(KEYS.REQUEST_ID_ACTION),
				HttpUtils.toMap(req),
				null,
				sessionBean
			);
			A_HttpView v = (A_HttpView)a.getView(logId);
			v.setRes(res);
			v.setReq(req);
			v.render(a);
			long d = System.currentTimeMillis()-t1;
			Log.logPerf.log(Level.FINEST,"HTTP"+Log.separator+logId+Log.separator+req.getRequestURI()+Log.separator+d);
		} catch (ProcessException e) {
			// try { res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage()); } catch (IOException e1) { throw new ServletException(e1); }
			Log.logConnector.log(Level.FINER, logId+e.getMessage(), e);
		} catch (ViewException e) {
			Log.logConnector.log(Level.FINER, logId+e.getMessage(), e);
		} catch (ControlerException e) {
			Log.logConnector.log(Level.FINER, logId+e.getMessage(), e);
		} catch (MapperException e) {
			Log.logConnector.log(Level.FINER, logId+e.getMessage(), e);
		} catch (ActionException e) {
			Log.logConnector.log(Level.FINER, logId+e.getMessage(), e);
		} catch (TypeException e) {
			Log.logConnector.log(Level.FINER, logId+e.getMessage(), e);
		} catch (ServiceException e) {
			Log.logConnector.log(Level.FINER, logId+e.getMessage(), e);
		} catch (InstantiationException e) {
			Log.logConnector.log(Level.FINER, logId+e.getMessage(), e);
		} catch (IllegalAccessException e) {
			Log.logConnector.log(Level.FINER, logId+e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			Log.logConnector.log(Level.FINER, logId+e.getMessage(), e);
		}
	}
	private void setNoCache(HttpServletResponse res){
		res.setHeader("pragma", "no-cache"); 
		res.setHeader("Cache-Control","no-cache, must-revalidate");
	}
	public static String getActionUrl(String idAction,String addedParameterQueryString) {
		String url = ResourceBundle.getBundle(bundleFilename).getString(KEY_SERVLET_NAME);
		if (idAction!=null&&!idAction.trim().equals(A_Action.ACTION_NONE)) url+="?"+KEYS.REQUEST_ID_ACTION+"="+idAction;
		if (addedParameterQueryString!=null&&!addedParameterQueryString.trim().equals("")) {
			url+= (url.indexOf('?')!=-1)?'&':'?';
			url+= addedParameterQueryString;
		}
		return url;
	}
}

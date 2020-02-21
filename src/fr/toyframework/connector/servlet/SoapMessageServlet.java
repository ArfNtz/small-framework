
package fr.toyframework.connector.servlet;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import fr.toyframework.Log;
import fr.toyframework.action.A_Action;
import fr.toyframework.connector.SoapConnect;
import fr.toyframework.controler.ActionControler;
import fr.toyframework.view.A_PrintStreamView;
import fr.toyframework.view.A_View;


public class SoapMessageServlet extends HtmlServlet {
	
	private static final long serialVersionUID = 1420471283138590001L;
	protected SoapConnect soapConnect;

	public SoapConnect getSoapConnect() {
		if (soapConnect==null)soapConnect=new SoapConnect();
		return soapConnect;
	}

	static MimeHeaders getHeaders(HttpServletRequest req) {
		Enumeration<?> e = req.getHeaderNames(); 
		MimeHeaders headers = new MimeHeaders(); 
		while (e.hasMoreElements()) { 
			String headerName = (String)e.nextElement(); 
			String headerValue = req.getHeader(headerName); 
			StringTokenizer values = new StringTokenizer(headerValue, ","); 
			while (values.hasMoreTokens()) {
				headers.addHeader(headerName, values.nextToken().trim());
			}
		}
		return headers; 
	} 

	static void putHeaders(MimeHeaders headers, HttpServletResponse res) {
		Iterator<?> it = headers.getAllHeaders(); 
		MimeHeader header;
		while (it.hasNext()) { 
			header = (MimeHeader)it.next();
			String[] values = headers.getHeader(header.getName()); 
			if (values.length == 1) res.setHeader(header.getName(),header.getValue()); 
			else { 
				StringBuffer concat = new StringBuffer(); 
				int i = 0; 
				while (i < values.length) { 
					if (i != 0) concat.append(',');
					concat.append(values[i++]); 
				}
				res.setHeader(header.getName(), concat.toString()); 
			} 
		} 
	} 
	protected Map<String,Object> toMap(Iterator<SOAPElement> soapElements) {
		Map<String,Object> m = new HashMap<String,Object>();
		SOAPElement se;
		String name;
		Object value;
		while (soapElements.hasNext()) {
			se=(SOAPElement)soapElements.next();
			name = se.getElementName().getLocalName();
			value = se.getValue();
			m.put(name,value);
		}
		return m;
	}
	@SuppressWarnings("unchecked")
	public void service(HttpServletRequest req, HttpServletResponse res)
	throws javax.servlet.ServletException {
		String logId = req.getRemoteAddr()+Log.separator;
		SOAPMessage msgIn=null;
		try {			
		//[listen] / read
			MimeHeaders headers = getHeaders(req);
			InputStream is = req.getInputStream();
			msgIn = getSoapConnect().createMessage(headers, is);
			Log.logControler.finer(SoapConnect.SOAPMessageToString(msgIn));

		//invoke
			SOAPElement se = (SOAPElement)msgIn.getSOAPPart().getEnvelope().getBody().getChildElements().next();
			String idAction = se.getElementName().getLocalName();

			res.setStatus(HttpServletResponse.SC_OK);
			res.setContentType("text/xml; charset=\""+HtmlServlet.charset+"\"");
			putHeaders(headers, res);

			A_Action a = ActionControler.service(
				null,
				idAction,
				toMap(se.getChildElements()),
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

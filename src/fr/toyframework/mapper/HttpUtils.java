package fr.toyframework.mapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import fr.toyframework.Log;
import fr.toyframework.connector.servlet.HtmlServlet;


public class HttpUtils {

	public static Map<String,Object> toMap(HttpServletRequest req) {
		Map<String,Object> m = new HashMap<String,Object>();
		
		try {
			if (ServletFileUpload.isMultipartContent(req)) {
				ServletFileUpload upload = new ServletFileUpload();
				upload.setFileSizeMax(HtmlServlet.uploadFileSizeMax);
				FileItemIterator iter = upload.getItemIterator(req);
				while (iter.hasNext()) {
				    FileItemStream item = iter.next();
				    if (item.isFormField()) {
						m.put(item.getFieldName(),Streams.asString(item.openStream(),HtmlServlet.charset));
				    } else {
				    	m.put(item.getFieldName()+HtmlServlet.KEY_UPLOAD_FILE_NAME_SUFFIX, item.getName());
				    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
				    	int b;
				    	while ((b=item.openStream().read())!=-1) bos.write(b);
				        m.put(item.getFieldName(),bos);
				    }
				}
			}
		} catch (FileUploadException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
		} catch (IOException e) {
			Log.logConnector.log(Level.FINER, e.getMessage(), e);
		}

		Enumeration<?> e = req.getParameterNames();
		String name;
		while (e.hasMoreElements()) {
			name = (String)e.nextElement();
			m.put(name,req.getParameterValues(name));
		}
		e = req.getAttributeNames();
		while (e.hasMoreElements()) {
			name = (String)e.nextElement();
			m.put(name,req.getAttribute(name));
		}
		e = req.getSession().getAttributeNames();
		while (e.hasMoreElements()) {
			name = (String)e.nextElement();
			m.put(name,req.getSession().getAttribute(name));
		}
		return m;
	}

	static public String printSessionAttributes(HttpSession session) {
		String s="";
		Enumeration<?> e = session.getAttributeNames();
		String name;
		Object value;
		while (e.hasMoreElements()) {
			name = (String)e.nextElement();
			value = session.getAttribute(name);
			s+=name;
			s+="=["+value+"]";
		}
		return s;
	}

	static public String printRequestParameters(HttpServletRequest req) {
		String s="";
		Enumeration<?> e = req.getParameterNames();
		String name;
		Object[] values;
		while (e.hasMoreElements()) {
			name = (String)e.nextElement();
			values = req.getParameterValues(name);
			s+=name+"=[";
			for (int i=0;i<values.length;i++) s+="["+values[i]+"]";
			s+="]";
		}
		return s;
	}

}

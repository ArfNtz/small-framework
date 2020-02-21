package fr.toyframework.connector;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnect {
	
	public static void main(String[] args) {
		try {
			URL u =	new URL("http://localhost:8080/framework/ParentServlet?componentId=login");
			HttpURLConnection c = (HttpURLConnection)u.openConnection();
			/**
				GET 
				POST 
				HEAD 
				OPTIONS 
				PUT 
				DELETE 
				TRACE 
			 */
			c.setRequestMethod("POST");
			c.setDoOutput(true);
			//OutputStream out = c.getOutputStream();
			//out.write(message.getBytes());
			c.connect();
			//String cookie = c.getHeaderField("Set-Cookie");
			InputStream in = c.getInputStream();
			int i;
			while ((i=in.read())!=-1) {
				System.out.print((char)i);
			}
			dumpConnection(c);
			c.disconnect();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void dumpConnection(HttpURLConnection c) throws IOException {
		System.out.println(c.getContentEncoding());
		System.out.println(c.getDefaultUseCaches());
		System.out.println(c.getDoInput());
		System.out.println(c.getDoOutput());
		System.out.println(c.getInstanceFollowRedirects());
		System.out.println(c.getUseCaches());
		System.out.println(c.getContentLength());
		System.out.println(c.getContentType());
		System.out.println(c.getDate());
		System.out.println(HttpURLConnection.getDefaultAllowUserInteraction());
		System.out.println(c.getExpiration());
		System.out.println(HttpURLConnection.getFollowRedirects());
		System.out.println(c.getHeaderFields());
		System.out.println(c.getIfModifiedSince());
		System.out.println(c.getLastModified());
		System.out.println(c.getRequestMethod());
		System.out.println(c.getResponseCode());
		System.out.println(c.getResponseMessage());
		System.out.println(c.getURL());
		System.out.println(HttpURLConnection.getFileNameMap());
	}
	public static void dumpConnectionBeforeConnect(HttpURLConnection c) throws IOException {
		System.out.println(c.getRequestProperties());
	}

}

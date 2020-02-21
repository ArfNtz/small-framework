package fr.toyframework.connector;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.toyframework.Log;



/**
 * keytool -genkey -keystore C:\bin\prg\tomcatKeystore -alias webifi -keyalg RSA
 * passwork changeit
 * "hostname" in url must match "firstAndLastName" in certificate (webifi=192.168.2.102,www.cifnet-serv.com=10.131.3.14)
 * Si besoin ajouter la ligne "10.131.3.14	www.cifnet-serv.com" dans C:\Windows\System32\drivers\etc\hosts
 *  
 * keytool -export -keystore C:\bin\prg\tomcatKeystore -alias webifi -file C:\webifi.cer
 * ou
 * IE6 menu outil option internet contenu certificats autorit�s principales de confiance exporter .cer DER X509
 * 
 * keytool -keystore C:\frameworkKeystore -import -alias webifi -file c:\webifi.cer
*/
public class HttpsConnect {
	
	static private String KEY_TRUSTSTORE = "trustStore";
	static private String KEY_PROTOCOL_HANDLER_CLASSNAME = "protocolHandlerClassName";
	static private String KEY_PROVIDER_CLASSNAME = "providerClassName";
	static private String KEY_PROXY_HOST = "proxyHost";
	static private String KEY_PROXY_PORT = "proxyPort";
	static private String KEY_SSL_DEBUG = "sslDebug";
	
	static private String bundleFilename = HttpsConnect.class.getName();
	/**
	 * System.setProperty("javax.net.ssl.trustStore","C:\\frameworkKeystore");
	 */
	static private String trustStore;
	/**
	 * System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol");
	 */
	static private String protocolHandlerClassName;
	/**
	 * Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); 
	 */
	static private String providerClassName;
	/**
	 * System.setProperty("https.proxyHost","192.168.1.10");
	 */
	static private String proxyHost;
	/**
	 * System.setProperty("https.proxyPort","80");
	 */
	static private String proxyPort;
	/**
	 * System.setProperty("javax.net.debug","ssl,handshake,data,trustmanager");
	 */
	static private String sslDebug;
	
	/**
	 * "hostname" in url must match "firstAndLastName" in certificate
	 * "https://localhost:8443/index.html"
	 */
	public static HttpURLConnection getConnection(String urlString) throws MalformedURLException, IOException {
		Log.logConnector.finer(urlString);
		System.setProperty("javax.net.ssl.trustStore",getTrustStore());
		System.setProperty("java.protocol.handler.pkgs",getProtocolHandlerClassName());
		//TODO instanciate from bundle class name
		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		System.setProperty("https.proxyHost",getProxyHost());
		System.setProperty("https.proxyPort",getProxyPort());
		System.setProperty("javax.net.debug",getSslDebug());
		return (HttpURLConnection)new URL(urlString).openConnection();
	}

	public static String getProtocolHandlerClassName() {
		if (protocolHandlerClassName==null) protocolHandlerClassName  = ResourceBundle.getBundle(bundleFilename).getString(KEY_PROTOCOL_HANDLER_CLASSNAME);
		return protocolHandlerClassName;
	}

	public static String getProviderClassName() {
		if (providerClassName==null) providerClassName  = ResourceBundle.getBundle(bundleFilename).getString(KEY_PROVIDER_CLASSNAME);
		return providerClassName;
	}

	public static String getProxyHost() {
		if (proxyHost==null) proxyHost  = ResourceBundle.getBundle(bundleFilename).getString(KEY_PROXY_HOST);
		return proxyHost;
	}

	public static String getProxyPort() {
		if (proxyPort==null) proxyPort  = ResourceBundle.getBundle(bundleFilename).getString(KEY_PROXY_PORT);
		return proxyPort;
	}

	public static String getSslDebug() {
		if (sslDebug==null) sslDebug  = ResourceBundle.getBundle(bundleFilename).getString(KEY_SSL_DEBUG);
		return sslDebug;
	}

	public static String getTrustStore() {
		if (trustStore==null) trustStore  = ResourceBundle.getBundle(bundleFilename).getString(KEY_TRUSTSTORE);
		return trustStore;
	}

	public static void setProtocolHandlerClassName(String string) {
		protocolHandlerClassName = string;
	}

	public static void setProviderClassName(String string) {
		providerClassName = string;
	}

	public static void setProxyHost(String string) {
		proxyHost = string;
	}

	public static void setProxyPort(String string) {
		proxyPort = string;
	}

	public static void setSslDebug(String string) {
		sslDebug = string;
	}

	public static void setTrustStore(String string) {
		trustStore = string;
	}

}

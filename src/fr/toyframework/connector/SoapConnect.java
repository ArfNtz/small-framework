package fr.toyframework.connector;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import fr.toyframework.Log;



public class SoapConnect {

	private String bundleFilename = SoapConnect.class.getName();
	private MessageFactory messageFactory;
	private String namespacePrefix;
	private String namespaceUri;
	private SOAPConnection soapConnection;

	public SOAPMessage createMessage(MimeHeaders headers, InputStream is)
	throws IOException, SOAPException{
		return getMessageFactory().createMessage(headers,is);
	}
	public SOAPMessage createMessage(String componentId)
	throws SOAPException{
		SOAPMessage msg = getMessageFactory().createMessage();
		SOAPPart part = msg.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPBody body = envelope.getBody();
		Name bodyName = envelope.createName(componentId,getNamespacePrefix(),getNamespaceUri());
		body.addBodyElement(bodyName);
		return msg;
	}
	public void mapParametersToSOAPElement(String[][] parameters,SOAPElement e,SOAPEnvelope envelope)
	throws SOAPException {
		if (parameters.length==2&&parameters[0].length>0&&parameters[1].length>0&&parameters[0].length==parameters[1].length) {
			Name name;
			String[] names;
			String[] values;
			String _name;
			String _value;
			names = parameters[0];
			values = parameters[1];
			SOAPElement _e;
			for (int j=0;j<names.length;j++){
				_name = names[j];
				name = envelope.createName(_name);
				_e = e.addChildElement(name);
				_value = values[j]; 
				_e.addTextNode(_value);
			}
		}
	}
	public void mapHeadersToSOAPMessage(String[][] headers, SOAPMessage msg){
		if (headers.length==2&&headers[0].length>0&&headers[1].length>0&&headers[0].length==headers[1].length) {
			for (int i=0;i<headers[0].length;i++) {
				msg.getMimeHeaders().addHeader(headers[0][i],headers[1][i]);
			}
		}
	}
	public SOAPMessage perform(String componentId, String[][] parameters, String[][] headers, Object endPoint)
	throws UnsupportedOperationException, SOAPException, IOException {

		SOAPMessage msg = createMessage(componentId);
		mapHeadersToSOAPMessage(headers,msg);
		mapParametersToSOAPElement(parameters,(SOAPElement)msg.getSOAPPart().getEnvelope().getBody().getChildElements().next(),msg.getSOAPPart().getEnvelope());
		msg.saveChanges();
		Log.logConnector.finer(SOAPMessageToString(msg));

		SOAPMessage response = getSoapConnection().call(msg, endPoint);
		Log.logConnector.finer(SOAPMessageToString(response));

		return response; 
	}
	public static String SOAPMessageToString(SOAPMessage msg)
	throws SOAPException, IOException {
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		msg.writeTo(o);
		o.flush();
		o.close();
		return o.toString();
	}
	public MessageFactory getMessageFactory()
	throws SOAPException {
		if (messageFactory==null) messageFactory = MessageFactory.newInstance();
		return messageFactory;
	}
	//PEG - 27/10/2011 - PEGA-8MBJVH
	public String getNamespacePrefix() {
		if (namespacePrefix==null) namespacePrefix = ResourceBundle.getBundle(bundleFilename).getString("namespacePrefix"); 
		return namespacePrefix;
	}
	//PEG - 27/10/2011 - PEGA-8MBJVH
	public String getNamespaceUri() {
		if (namespaceUri==null) namespaceUri = ResourceBundle.getBundle(bundleFilename).getString("namespaceUri"); 
		return namespaceUri;
	}
	public void setNamespacePrefix(String string) {
		namespacePrefix = string;
	}
	public void setNamespaceUri(String string) {
		namespaceUri = string;
	}
	public SOAPConnection getSoapConnection()
	throws UnsupportedOperationException, SOAPException {
		if (soapConnection==null) {
			SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
			soapConnection = scf.createConnection();
		}
		return soapConnection;
	}
}

package fr.toyframework.view;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import fr.toyframework.action.A_Action;
import fr.toyframework.connector.SoapConnect;
import fr.toyframework.model.Message;
import fr.toyframework.type.format.MessageFormat;

public class SoapView extends A_PrintStreamView {

	public void init(A_Action a) throws ViewException {
	}
	public void print(A_Action a,PrintStream ps) throws ViewException {
		try {
			//format / encode
			SOAPMessage msgOut;
			msgOut = new SoapConnect().createMessage(a.getProcess().getClass().getName()+a.getClass().getName());
			mapObjectToSOAPElement(a,(SOAPElement)msgOut.getSOAPPart().getEnvelope().getBody().getChildElements().next(),msgOut.getSOAPPart().getEnvelope(),a.getProcess().getClass().getName());
			mapMessagesToSOAPFault(a.getMessages().values().iterator(),msgOut.getSOAPPart().getEnvelope());
			msgOut.saveChanges();
			//write / [send]
			msgOut.writeTo(getPrintStream());
			getPrintStream().flush();
		} catch (SOAPException e) {
			throw new ViewException(e);
		} catch (IOException e) {
			throw new ViewException(e);
		}
	}

	protected void mapMessagesToSOAPFault(Iterator<Message> messages,SOAPEnvelope envelope)
	throws SOAPException {
		if (messages.hasNext()){
			SOAPFault fault = envelope.getBody().getFault(); 
			if (fault==null) {
				fault = envelope.getBody().addFault();
				fault.setFaultCode("Server");
				fault.setFaultActor("");
				fault.setFaultString("Messages");
			}
			Detail detail = fault.addDetail();
			Name name;
			DetailEntry e;
			Message m;
			while (messages.hasNext()) {
				m = (Message)messages.next();
				name = envelope.createName(""+MessageFormat.format(m.getPattern(), m.getArguments()));
				e = detail.addDetailEntry(name);
				e.addTextNode(""+MessageFormat.format(m.getPattern(), m.getArguments()));
			}
		}
	}
	protected void mapObjectToSOAPElement(Object object,SOAPElement element,SOAPEnvelope envelope,String componentId)
	throws SOAPException, IOException {
		Name name;
		SOAPElement e;
		name = envelope.createName(componentId);
		e = element.addChildElement(name);
		e.addTextNode("TODO");
		//TODO use soapConnect.mapParametersToSOAPElement()
	}

}

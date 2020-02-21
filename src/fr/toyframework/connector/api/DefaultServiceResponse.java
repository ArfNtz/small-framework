package fr.toyframework.connector.api;

import fr.toyframework.action.A_Action;
import fr.toyframework.model.A_ProcessModel;

public class DefaultServiceResponse extends A_ServiceResponse {
	private static final long serialVersionUID = 3209625163523670593L;

	private ServiceMessage[] messages;
	private ServiceSessionBean session;

	private A_ProcessModel model;

	public DefaultServiceResponse(A_Action a) throws ServiceException {
		super(a);
		this.session = new ServiceSessionBean( a.getSessionBean() );
		this.model = a.getProcess().getProcessModel();
		setMessages(mapServiceMessages(a));
	}
	
	public A_ProcessModel getModel() {return model;}
	public void setModel(A_ProcessModel model) {this.model = model;}

	public ServiceMessage[] getMessages() {	return messages; }
	public ServiceSessionBean getSession() { return session; }
	public void setMessages(ServiceMessage[] messages) { this.messages = messages; }
	public void setSession(ServiceSessionBean session) { this.session = session; }

}

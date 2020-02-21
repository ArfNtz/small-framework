package fr.toyframework.connector.api;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import fr.toyframework.action.A_Action;
import fr.toyframework.mapper.BeanUtils;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.mapper.converter.Converters;
import fr.toyframework.model.A_SessionBean;
import fr.toyframework.model.Message;


public abstract class A_ServiceResponse implements Serializable {
	
	private static final long serialVersionUID = 7079745090397458664L;
	
	public A_ServiceResponse(){	}

	@SuppressWarnings("all")
	public A_ServiceResponse( A_Action a) throws ServiceException {
		/** A_Action doit toujours �tre pass�
		 *  ServiceException est la seule exception de la m�thode **/
	}

	protected void mapProcessModelAttributes(A_Action a) throws ServiceException {
		try {
			BeanUtils.map(a.getProcess().getProcessModel(),this,Converters.getAllConverters(),false, a.getSessionBean().getLocale());
		} catch (MapperException e) {
			throw new ServiceException(e);
		}		
	}

	protected ServiceMessage[] mapServiceMessages(A_Action a) {
		ServiceMessage[] serviceMessages = new ServiceMessage[a.getMessages().size()];
		int i=0;
		for (Message m : a.getMessages().values()) {
			//TODO : if m.getThrowable() => mettre la stacktrace dans text
			ServiceMessage _m = new ServiceMessage(m.getId(),m.getType(),m.getText(),m.getArguments());
			serviceMessages[i] = _m;
			i++;
		}
		return serviceMessages;
	}

	public static Map<String,Message> mapMessages(ServiceMessage[] serviceMessages) {
		Map<String,Message> al = new LinkedHashMap<String,Message>();
		for (ServiceMessage _s : serviceMessages) al.put(_s.getId(), new Message(_s.getId(), _s.getType(), _s.getText(), null));
		return al;
	}
	
	// FB20111373 - 15/12/2011
	public static Map<String,Message> mapMessages(ServiceMessage[] serviceMessages, A_SessionBean sb) {
		Map<String,Message> al = new LinkedHashMap<String,Message>();
		for (ServiceMessage _s : serviceMessages) al.put(_s.getId(), new Message(_s.getId(), _s.getType(), _s.getText(), null, null, sb, null));
		return al;
	}
}

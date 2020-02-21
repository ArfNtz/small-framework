package fr.toyframework.connector;

/*

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.NamingException;

//"jms/TopicConnectionFactory"
//"jms/Topic"
// property java.naming.provider.url localhost:2809
// property java.naming.factory.initial com.ibm.websphere.naming.WsnInitialContextFactory
// java.naming.factory.initial com.ibm.websphere.naming.WsnInitialContextFactory
// java.naming.factory.url.pkgs com.ibm.ws.naming
// com.sun.jndi.fscontext.RefFSContextFactory
//env.put(Context.PROVIDER_URL, "localhost:2809");
//env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
//env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.fscontext.RefFSContextFactory");
//j2eeadmin -addJmsFactory testTopic topic

*/

public class JmsConnect {

/*
	private static final String queueConnectionFactoryName="";
	private static final String topicConnectionFactoryName="";
	private static Context jndiContext = null;
	
	private static QueueConnectionFactory queueConnectionFactory;
	private static TopicConnectionFactory topicConnectionFactory;

	public static QueueConnectionFactory getQueueConnectionFactory() throws NamingException {
		return (QueueConnectionFactory) jndiLookup(queueConnectionFactoryName);
	}
	public static TopicConnectionFactory getTopicConnectionFactory() throws NamingException {
		return (TopicConnectionFactory) jndiLookup(topicConnectionFactoryName);
	}
	public static Queue getQueue(String name, QueueSession session) throws NamingException {
		return (Queue) jndiLookup(name);
	}
	public static Topic getTopic(String name, TopicSession session) throws NamingException {
		return (Topic) jndiLookup(name);
	}
	public static Object jndiLookup(String name) throws NamingException {
		return jndiContext.lookup(name);
	}
      
	/**
	 * Waits for 'count' messages on controlQueue before
	 * continuing.  Called by a publisher to make sure that
	 * subscribers have started before it begins publishing
	 * messages.
	 */
/*
	public static void receiveSynchronizeMessages(String prefix,String controlQueueName,int count) throws JMSException, NamingException { 
		QueueConnection queueConnection = null;
		try {
			queueConnection = getQueueConnectionFactory().createQueueConnection();
			QueueSession queueSession = queueConnection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
			Queue controlQueue = getQueue(controlQueueName,queueSession);
			queueConnection.start();
			QueueReceiver queueReceiver = queueSession.createReceiver(controlQueue);
			while (count > 0) {
				queueReceiver.receive();
				count--;
			}
			queueConnection.close();
		} catch (JMSException e) {
			if (queueConnection != null) {
				queueConnection.close();
			}
			throw e;
		}
	}
*/
	/**
	 * Sends a message to controlQueue.  Called by a subscriber
	 * to notify a publisher that it is ready to receive
	 * messages.
	 */
/*
	public static void sendSynchronizeMessage(String prefix,String controlQueueName,Message synchroniseMessage) throws JMSException, NamingException {
		QueueConnection         queueConnection = null;
		try {
			queueConnection = getQueueConnectionFactory().createQueueConnection();
			QueueSession queueSession = queueConnection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
			Queue controlQueue = getQueue(controlQueueName,queueSession);
			QueueSender queueSender = queueSession.createSender(controlQueue);
			queueSender.send(synchroniseMessage);
			queueConnection.close();
		} catch (JMSException e) {
			if (queueConnection != null) {
				queueConnection.close();
			}
			throw e;
		}
	}
*/
}

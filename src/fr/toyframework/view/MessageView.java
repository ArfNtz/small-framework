package fr.toyframework.view;

import java.beans.XMLEncoder;
import java.io.PrintStream;

import fr.toyframework.action.A_Action;

public class MessageView extends A_PrintStreamView {

	public void init(A_Action a) throws ViewException {	}

	public void print(A_Action a,PrintStream ps) throws ViewException {
		XMLEncoder e = new XMLEncoder(getPrintStream());
		e.writeObject(a.getProcess().getProcessModel());
		e.flush();
		e.close();
	}

}

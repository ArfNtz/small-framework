package fr.toyframework.view;

import java.io.PrintStream;

import fr.toyframework.action.A_Action;

public abstract class A_PrintStreamView extends A_View {

	protected PrintStream ps;

	protected abstract void print(A_Action a,PrintStream ps) throws ViewException;

	public void render(A_Action a) throws ViewException {
		print(a,ps);
	}
	
	public PrintStream getPrintStream() {
		return ps;
	}
	public void setPrintStream(PrintStream ps) {
		this.ps = ps;
	}

}

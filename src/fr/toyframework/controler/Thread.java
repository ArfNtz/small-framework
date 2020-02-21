package fr.toyframework.controler;

import fr.toyframework.action.A_Action;
import fr.toyframework.process.A_Process;

public class Thread {
	private A_Process process;
	private A_Action action;
	public A_Process getProcess() {	return process;	}
	public void setProcess(A_Process p) {	this.process = p;	}
	public A_Action getAction() { return action; }
	public void setAction(A_Action a) { this.action = a; }
}

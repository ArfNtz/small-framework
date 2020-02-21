package fr.toyframework.action;

import java.util.List;

import fr.toyframework.model.A_ProcessModel;


public abstract class A_SetAction extends A_Action {

	public abstract A_ProcessModel getList(int i);
	
	public abstract void setList(int i,A_ProcessModel m);
	
	public abstract List<A_ProcessModel> getList();
	
	public abstract void setList(List<A_ProcessModel> list);
	
	public abstract boolean getListSelectedIndexes(int i);
	
	public abstract void setListSelectedIndexes(int i,boolean b);
	
	public abstract boolean[] getListSelectedIndexes();
	
	public abstract void setListSelectedIndexes(boolean[] listSelectedIndexes);
	
}

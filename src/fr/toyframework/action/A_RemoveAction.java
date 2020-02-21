package fr.toyframework.action;

public abstract class A_RemoveAction extends A_Action {

	public abstract boolean getListSelectedIndexes(int i);

	public abstract void setListSelectedIndexes(int i, boolean b);

	public abstract boolean[] getListSelectedIndexes();

	public abstract void setListSelectedIndexes(boolean[] listSelectedIndexes);

}
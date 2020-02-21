package fr.toyframework.model;

import java.util.Collection;

/**
 * Insert the type's description here.
 * Creation date: (07/11/2001 12:01:19)
 * @author: Administrator
 */
public class Segment<E> extends java.util.ArrayList<E> {
	private static final long serialVersionUID = 2420179579360684416L;
	
	private int parentSize;

	public Segment(){
		super();
	}
	public Segment(Collection<? extends E> c) {
		super(c);
	}
	
	/**
	 * Insert the method's description here.
	 * Creation date: (07/11/2001 12:06:21)
	 * @return int
	 */
	public int getParentSize() {
		return parentSize;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (07/11/2001 12:06:21)
	 * @param newParentSize int
	 */
	public void setParentSize(int newParentSize) {
		parentSize = newParentSize;
	}

	public void addElement(E obj) {
		super.add(obj);
	}

	public Object elementAt(int index) {
		return super.get(index);
	}

	public Object firstElement() {
		return super.get(0);
	}

}

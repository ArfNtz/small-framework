package fr.toyframework.view.html.tag;

import fr.toyframework.view.ViewException;

public interface I_CompositeTag extends I_Tag {
	public abstract A_CompositeTag addFirst(A_Tag tag) throws ViewException;
	public abstract A_CompositeTag add(A_Tag tag) throws ViewException;
	public abstract A_CompositeTag set(A_Tag tag) throws ViewException;
}

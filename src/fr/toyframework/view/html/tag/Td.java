package fr.toyframework.view.html.tag;


import fr.toyframework.action.A_Action;
import fr.toyframework.view.ViewException;

public class Td extends A_CompositeTag {

	public Td(){
		this(null,null);
	}
	public Td(String id,String style_class) {
		super("td",id,style_class,null);
	}
	public void build(A_Action a) throws ViewException {
	}
}
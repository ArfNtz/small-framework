package fr.toyframework.view.html.tag;


import fr.toyframework.action.A_Action;
import fr.toyframework.view.ViewException;

public class Html extends A_CompositeTag {

	public Html() {	
		super("html",null,null,null);
	}
	public void build(A_Action a) throws ViewException {
	}
}

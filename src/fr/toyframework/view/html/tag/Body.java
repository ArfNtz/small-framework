package fr.toyframework.view.html.tag;


import fr.toyframework.action.A_Action;
import fr.toyframework.view.ViewException;

public class Body extends A_CompositeTag {

	public Body() {	
		super("body",null,null,null); 
	}

	public void build(A_Action a) throws ViewException {		
	}

}

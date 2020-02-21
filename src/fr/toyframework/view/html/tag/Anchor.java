package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.view.ViewException;

public class Anchor extends A_Tag {

	public Anchor(String id) {
		super("a",id,null,null);
	}
	public Anchor(String id,String style_class) {
		super("a",id,style_class,null);
	}

	public void build(A_Action a) throws ViewException {
	}

}
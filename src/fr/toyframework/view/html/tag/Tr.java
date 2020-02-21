package fr.toyframework.view.html.tag;


import fr.toyframework.action.A_Action;
import fr.toyframework.view.ViewException;

public class Tr extends A_CompositeTag {

	public Tr(){
		this(null,null);
	}
	public Tr(String id,String style_class) {
		super("tr",id,style_class,null);
	}
	public void build(A_Action a) throws ViewException {
	}
}

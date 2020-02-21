package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.Chars;
import fr.toyframework.view.ViewException;

public class Pre extends A_CompositeTag {

	public Pre(String text) {
		this(null,null,new Chars(text));
	}
	public Pre(A_Type data) {
		this(null,null,data);
	}
	public Pre(String id,String style_class,String text) {
		this(id,style_class,new Chars(text));
	}
	public Pre(String id,String style_class,A_Type data) {
		super("pre",id,style_class,data);
	}
	public void build(A_Action a) throws ViewException {
	}
}
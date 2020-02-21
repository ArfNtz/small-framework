package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.Chars;
import fr.toyframework.view.ViewException;

public class Label extends A_CompositeTag {

	public Label(String text) {
		this(null,null,new Chars(text));
	}
	public Label(A_Type data) {
		this(null,null,data);
	}
	public Label(String id,String style_class,String text) {
		this(id,style_class,new Chars(text));
	}
	public Label(String id,String style_class,A_Type data) {
		super("span",id,style_class,data);
	}
	public void build(A_Action a) throws ViewException {
	}
}
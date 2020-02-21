package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.Chars;
import fr.toyframework.view.ViewException;

public class Div extends A_CompositeTag {

	public Div(String id, String style_class, A_Tag tag) throws ViewException {
		super("div",id,style_class,null);
		add(tag);
	}
	public Div(String id, String style_class) {
		super("div",id, style_class,null);
	}
	public Div(String id, String style_class, A_Type data) {
		super("div", id, style_class, data);
	}
	public Div(String id, String style_class, String data) {
		this(id, style_class, new Chars(data));
	}

	public void build(A_Action a) throws ViewException {
	}

}

package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.Chars;
import fr.toyframework.view.ViewException;

public class InnerText extends A_CompositeTag {

	public InnerText(String text) {
		this(new Chars(text));
	}
	public InnerText(A_Type data) {
		super(null,null,null,data);
	}
	public void build(A_Action a) throws ViewException {
	}
}

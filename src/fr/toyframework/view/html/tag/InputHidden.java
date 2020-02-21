package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.Chars;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.ViewException;

public class InputHidden extends A_Tag {

	public InputHidden(A_Type data) {
		this(null,data);
	}
	public InputHidden(String value) {
		this(null,new Chars(value));
	}
	public InputHidden(String id,String value) {
		this(id,new Chars(value));
	}
	public InputHidden(String id,A_Type data) {
		super("input",id,null,data);
	}
	public void build(A_Action a) throws ViewException {
		try {
			addAttribute("type","hidden");
			addAttribute("value",data!=null?data.getUiInputString(a.getSessionBean().getLocale()):"");
		} catch (TypeException e) {	throw new ViewException(e);	}
	}
}

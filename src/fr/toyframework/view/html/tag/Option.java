package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.ViewException;

public class Option extends A_CompositeTag {

	boolean selected = false;
	A_Type value;

	public Option(A_Type value,String style_class,A_Type label,boolean selected) {
		super("option",value.toString(),style_class,label);
		this.value = value;
		this.selected = selected;
	}	
	public void build(A_Action a) throws ViewException {
		try {
			addAttribute("value",value!=null?value.getUiInputString(a.getSessionBean().getLocale()):"");
		} catch (TypeException e) {
			throw new ViewException(e);
		}
		if (selected) addAttribute("selected",null);
	}

}

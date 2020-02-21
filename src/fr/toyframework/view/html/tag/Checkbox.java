package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.ViewException;

public class Checkbox extends A_Tag {

	public boolean checked;

	public Checkbox (String id,A_Type data,boolean checked) {
		this(id,null,data,checked);
	}
	public Checkbox (String id,String style_class,A_Type data,boolean checked) {
		super("input",id,style_class,data);
		this.checked=checked;
	}
	public void build(A_Action a) throws ViewException {
		try {
			addAttribute("type","checkbox");
			addAttribute("value",data!=null?data.getUiInputString(a.getSessionBean().getLocale()):"");
		} catch (TypeException e) {
			throw new ViewException(e);
		}		
		if (checked) addAttribute("checked",null);
	}
}

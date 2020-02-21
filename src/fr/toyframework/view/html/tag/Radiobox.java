package fr.toyframework.view.html.tag;


import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.ViewException;

public class Radiobox extends A_Tag {

	public String value;
	public boolean checked;

	public Radiobox (String id,String style_class,A_Type data,boolean checked) {
		super("input",style_class,id,data);
		this.checked=checked;
	}
	public void build(A_Action a) throws ViewException {
		try {
			addAttribute("type","radio");
			addAttribute("value",data!=null?data.getUiInputString(a.getSessionBean().getLocale()):"");
		} catch (TypeException e) {
			throw new ViewException(e);
		}
		if (checked) addAttribute("checked",null);
	}

}

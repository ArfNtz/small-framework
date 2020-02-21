package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.ViewException;

public class InputText extends A_CompositeTag {

	private A_Type value;

	public InputText(A_Type value) {
		this(null,null,value,false,10,0);
	}
	public InputText(String id,String style_class,A_Type value,boolean afficheMessage) {
		super("input",id,style_class,null);
		this.value = value;
	}
	public InputText(String id,String style_class,A_Type value,boolean afficheMessage,int size,int maxlength) {
		this(id,style_class,value,afficheMessage);
		addAttribute("size",""+size);
		if (maxlength > 0){
			addAttribute("maxlength",""+maxlength);
		}
	}
	public void build(A_Action a) throws ViewException {
		try {
			addAttribute("type","text");
			addAttribute("value",value!=null?value.getUiInputString(a.getSessionBean().getLocale()):"");
		} catch (TypeException e) {
			throw new ViewException(e);
		}
		
		if ((a.getMessages().get(id))!=null) {
			style_class = "error";
		}

	} 
}

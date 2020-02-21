package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.Chars;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.ViewException;

public class InputImage extends A_Tag {

	private String src;
	
	public InputImage(A_Type data) {
		this(null,null,data);
	}
	public InputImage(String src,String title) {
		this(null,null,title,src);
	}
	public InputImage(String id,String style_class,String title) {
		this(id,style_class,new Chars(title));
	}
	public InputImage(String id,String style_class,String title, String src) {
		this(id,style_class,new Chars(title));
		this.src=src;
	}
	
	public InputImage(String id,String style_class,A_Type data) {
		super("input",id,style_class,data);
	}
	public void build(A_Action a) throws ViewException {
		try {
			addAttribute("type","image");
			addAttribute("title",data!=null?data.getUiInputString(a.getSessionBean().getLocale()):"");
			addAttribute("src", src);
		} catch (TypeException e) {	throw new ViewException(e);	}
	}
}

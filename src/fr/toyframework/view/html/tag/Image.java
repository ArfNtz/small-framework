package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.view.ViewException;

public class Image extends A_Tag {

	public String src;
	
	public Image(String id,String src) { this(id,null,src);}
	public Image(String id,String style_class,String src) {
		super("img",id,style_class,null);
		this.src = src;
	}
	public Image(Image i) { this(i.id,i.style_class,i.src); }
	public void build(A_Action a) throws ViewException {
		addAttribute("border","0");
		if (src!=null) addAttribute("src",src);
	}
}
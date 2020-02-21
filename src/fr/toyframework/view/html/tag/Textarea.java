package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.Chars;
import fr.toyframework.view.ViewException;

public class Textarea extends A_CompositeTag {

	public Textarea() {
		this(null,null,new Chars(""));
	}
	
	public Textarea(String text) {
		this(null,null,new Chars(text));
	}
	public Textarea(A_Type data) {
		this(null,null,data);
	}
	public Textarea(String id,String style_class,A_Type data) {
		super("textarea",id,style_class,data);
	}
	public Textarea(String id,String style_class,A_Type data,int nbCols,int nbRows) {
		super("textarea",id,style_class,data);
		addAttribute("rows",""+nbRows);addAttribute("cols",""+nbCols);
	}
	public Textarea(String id,String style_class,String text) {
		this(id,style_class,new Chars(text));
	}
	public Textarea(String id,String style_class,String text,int nbCols,int nbRows) {
		this(id,style_class,new Chars(text),nbCols,nbRows);
	}
	public void build(A_Action a) throws ViewException {
	}
}

package fr.toyframework.view.html.tag;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.Chars;
import fr.toyframework.view.ViewException;

public class Script extends A_CompositeTag {
	
	public Script(String script_text) {
		this(null,null,new Chars(script_text));
	}
	public Script(String type,String src){
		this(null,null,null);
		addAttribute("type",type);
		addAttribute("src",src);
	}
	public Script setScriptText(String script_text) {
		setData(new Chars(script_text));
		return this;
	}

	private Script(String id,String style_class,A_Type data) {
		super("script",id,style_class,data);
	}
	public void build(A_Action a) throws ViewException { }

}
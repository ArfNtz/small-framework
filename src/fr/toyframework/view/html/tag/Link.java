package fr.toyframework.view.html.tag;


import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.type.Chars;
import fr.toyframework.view.ViewException;

public class Link extends A_CompositeTag {

	public String href;

	public Link(String id,String style_class,String href,A_Tag innerTag) throws ViewException {
		super("a",id,style_class,null);
		this.href=href;
		add(innerTag);
	}
	public Link(String id,String style_class,String href,A_Type link) throws ViewException {
		this(id,style_class,href,new Data(link));
	}
	public Link(String id,String style_class,String href,String link) throws ViewException {
		this(id,style_class,href,new Chars(link));
	}
	public Link(String href,String link) throws ViewException {
		this(null,null,href,link);
	}
	public Link(String href,A_Tag innerTag) throws ViewException {
		this(null,null,href,innerTag);
	}
	public Link(String href,A_Type link) throws ViewException {
		this(null,null,href,link);
	}
	public void build(A_Action a) throws ViewException {
		addAttribute("href",href);
	}
}
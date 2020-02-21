package fr.toyframework.view.html.tag;


import fr.toyframework.action.A_Action;
import fr.toyframework.view.ViewException;

/**
 * 
 * @author loic
 *
 */
public class Th extends A_CompositeTag {

	public Th(){
		this(null,null);
	}
	public Th(String id,String style_class) {
		super("th",id,style_class,null);
	}
	public void build(A_Action a) throws ViewException {
	}
}
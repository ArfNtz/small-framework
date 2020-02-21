package fr.toyframework.view.html.tag;


import fr.toyframework.action.A_Action;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.ViewException;

public class Table extends A_CompositeTag {

	public Table(){
		this(null,null);
	}

	public Table(String id,String style_class) {
		super("table",id,style_class,null);
	}
	public void build(A_Action a) throws ViewException, TypeException {
	}
}

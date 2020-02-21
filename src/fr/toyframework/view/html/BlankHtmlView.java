package fr.toyframework.view.html;

import fr.toyframework.action.A_Action;
import fr.toyframework.view.ViewException;
import fr.toyframework.view.html.tag.A_Tag;
import fr.toyframework.view.html.tag.Html;

public class BlankHtmlView extends A_HtmlView {

	A_Tag main;
	
	public void init(A_Action a) throws ViewException { }

	public A_Tag build(A_Action a) throws ViewException {
		if (main==null) main = new Html();
		return getMain();
	}

	public A_Tag getMain() {
		return main;
	}
	public void setMain(A_Tag main) {
		this.main = main;
	}
}

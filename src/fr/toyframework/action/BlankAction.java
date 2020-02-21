package fr.toyframework.action;


import fr.toyframework.mapper.MapperException;
import fr.toyframework.model.A_ActionModel;
import fr.toyframework.process.BlankProcess;
import fr.toyframework.process.ProcessException;
import fr.toyframework.view.A_View;
import fr.toyframework.view.html.BlankHtmlView;

public class BlankAction extends A_Action {

	public void checkAccess(A_ActionModel m) {
	}
	public void check(A_ActionModel m) {
	}
	public void init(A_ActionModel m) {
	}
	public void reset(A_ActionModel m) {
	}
	public A_View createView() {
		return new BlankHtmlView();
	}
	public boolean isNext() {
		return false;
	}
	public String getProcessClassName() {
		return BlankProcess.class.getName();
	}
	public String getProcessMethodName() {
		return null;
	}
	public A_ActionModel createModel() {
		return null;
	}

	public void context(A_ActionModel am) throws MapperException, ProcessException {

		
	}
	public void contextSystem(A_ActionModel am) throws MapperException, ProcessException {}
	public void checkSystem(A_ActionModel am) throws MapperException, ProcessException {}

}

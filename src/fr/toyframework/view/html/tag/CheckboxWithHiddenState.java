package fr.toyframework.view.html.tag;


import java.util.List;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.view.ViewException;

public class CheckboxWithHiddenState extends A_CompositeTag {

	public static String CHECKBOX_SUFFIX_ID = "_checkbox";
	private Checkbox cb;
	private InputHidden h;

	public CheckboxWithHiddenState(String id, String style_class, fr.toyframework.type.Boolean value) {
		this(id,style_class,value,value.booleanValue());
	}

	public CheckboxWithHiddenState(String id, String style_class, A_Type value, boolean checked) {
		super(null,null,null,null);
		this.h = new InputHidden(id,new Boolean(checked).toString());
		this.cb = new Checkbox(id+CHECKBOX_SUFFIX_ID,style_class,value,checked);
		cb.addAttribute("onClick", CheckboxWithHiddenState.printJsIsChecked(id+CHECKBOX_SUFFIX_ID, id));
	}

	public void build(A_Action a) throws ViewException {
		this.add(h);
		this.add(cb);
	}
	public A_Tag addAttribute(String name,String value) {
		cb.addAttribute(name, value);
		return this;
	}
	public A_Tag setAttribute(String name,String value) {
		cb.setAttribute(name, value);
		return this;
	}

	public static String printJsSwitch(String id){
		return "switchBoolean('"+id+"');switchCheckbox('"+id+CHECKBOX_SUFFIX_ID+"');";
	}
	public static String printJsCheck(String id){
		return "switchBoolean('"+id+"','true');switchCheckbox('"+id+CHECKBOX_SUFFIX_ID+"',true);";
	}
	public static String printJsSwitch(List<String> ids){
		String js="";
		for(String id: ids) {
			js+="switchBoolean('"+id+"');switchCheckbox('"+id+CHECKBOX_SUFFIX_ID+"');";
		}
		return js;
	}
	
	public static String printJsIsChecked(String idCheckBox, String idHidden)
	{
		return "isChecked('"+idCheckBox+"','"+idHidden+"')";
	}
}

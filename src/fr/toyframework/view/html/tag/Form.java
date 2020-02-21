package fr.toyframework.view.html.tag;


import fr.toyframework.action.A_Action;
import fr.toyframework.connector.servlet.HtmlServlet;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.Images;
import fr.toyframework.view.ViewException;

public class Form extends A_CompositeTag {
	
	public static String METHOD_POST = "POST"; 
	public static String METHOD_GET = "GET";
	public static String ENCTYPE_MULTIPART_FORM_DATA = "multipart/form-data";

	public String method;
	public String action_url;
	
	public Form (String id,String method,String action_url) {
		super("form",id,null,null);
		this.method=method;
		this.action_url=action_url;
	}
	public void build(A_Action a) throws ViewException, TypeException {
		this.addAttribute("accept-charset",HtmlServlet.charset);
		this.addAttribute("method",method);
		this.addAttribute("action",action_url);
		this.add(new InputImage(null,"hiddenImage","",Images.srcImgEmpty)); // pour le submit en pressant enter
	}
	public void setEnctype (String enctype) {
		addAttribute("ENCTYPE",enctype);
	}
	public String printJsSubmit() {
		return "document.forms['"+id+"'].submit();";
	}
	public String printJsCheckbox(String checkBoxName) {
		return "document.forms['"+id+"'].elements['"+checkBoxName+"'].checked=true;";		
	}
	public String printJsSetFieldValue(String idField,String value) {
		return "document.forms['"+id+"'].elements['"+idField+"'].value='"+value+"';";
	}
	public String printJsSetAction(String url) {
		return "document.forms['"+id+"'].action='"+url+"';";
	}
	
	public static String printJsSubmit(String idForm)
	{
		return "document.forms['"+idForm+"'].submit();";
	}
}

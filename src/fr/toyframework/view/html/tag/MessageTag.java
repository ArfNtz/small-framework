package fr.toyframework.view.html.tag;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import fr.toyframework.action.A_Action;
import fr.toyframework.connector.servlet.HtmlServlet;
import fr.toyframework.controler.NotInstalledException;
import fr.toyframework.controler.UnauthorizedAccessException;
import fr.toyframework.model.Message;
import fr.toyframework.type.Chars;
import fr.toyframework.type.format.MessageFormat;
import fr.toyframework.view.ViewException;

public class MessageTag extends Label {
	private static String KEY_MESSAGE_EXCEPTION = "message.exception";
	private Message m;
	private String style_class_info="go";
	private String style_class_error="stop";
	private String style_class_user_input="warning";
	private String style_class_error_details="error_details";
	public MessageTag(Message m){
		super(m!=null?m.getId():"","","");
		this.m=m;
	}
	public void build(A_Action a) throws ViewException {
		super.build(a);
		Locale l = a.getSessionBean().getLocale();
		if (m!=null) {
			style_class = style_class_info;
			if (m.getType()==Message.TYPE_ERROR) style_class = style_class_error;
			if (m.getType()==Message.TYPE_USER_INPUT) style_class = style_class_user_input;
			Throwable t = m.getThrowable(); 
			if (t==null) {
				setData(new Chars(MessageFormat.format(m.getPattern(), m.getArguments())));
			} else {
				if (t instanceof NotInstalledException) {
					setData(new Chars(MessageFormat.format(m.getPattern(), m.getArguments())));
					//@TODO dans commun ou InstallAction en properties framework ... ?
					//add(new Link(HtmlServlet.getActionUrl(ActionList.InstallAction, null),Images.imgGo()));
				} else if (t instanceof UnauthorizedAccessException) {
					setData(new Chars(MessageFormat.format(m.getPattern(), m.getArguments())));
				} else {
					setData(new Chars(MessageFormat.format(r(KEY_MESSAGE_EXCEPTION,l),new Object[]{})));
				}
	    		StringWriter sw = new StringWriter();
	    		PrintWriter p = new PrintWriter(sw);
	    		t.printStackTrace(p);
	    		p.flush();
	 			Chars stackTrace = new Chars(sw.getBuffer().toString());
				Div div = new Div(this.id+"_error_details",style_class_error_details);
				div.addAttribute("style","display:none");
				Textarea ta = new Textarea(this.id+"stackTrace","stackTrace",stackTrace,120,20);
				div.set(ta);
				add(div);
				addAttribute("onClick","javascript:positionSouris(event,'"+this.id+"_error_details');switchDisplay('"+this.id+"_error_details');");
				addAttribute("style","cursor:pointer;");
			}
			if (m.getIdAction_target()!=null) {
				//@TODO dans commun pour le "Images.Go" � la place de "=>"
				add(new Link(HtmlServlet.getActionUrl(m.getIdAction_target(),m.getTargetActionParametersQueryString()),"=>"));
			}
		}
	}
}

package fr.toyframework.type;

import java.text.Format;
import java.util.Locale;

public class Boolean extends A_Type {
	private static final long serialVersionUID = 6959294076315100446L;

	public static Boolean FALSE() { return new Boolean(false); }
	public static Boolean TRUE() { return new Boolean(true); }

	public static String sqlType = "CHAR(1)";
	public Boolean(boolean b) { o = b; }
	public Boolean() { o = false; }

	protected Format getFormatDb() { return null; }
	protected Format getFormatDbInput() { return null; }
	protected Format getFormatUi(Locale l) { return null; }
	protected Format getFormatUiInput(Locale l) { return null; }

//	protected boolean o;
	
	public String getDbString() {
		return ((java.lang.Boolean)o).booleanValue()?"1":"0";
	}
	public A_Type setDbString(String s) {
		
		// NAU - NAUD-8SKHDT - 28/03/2012
		if (s!=null&& trim) s = s.trim();
		
		o=(s!=null&&((String)s).trim().equals("1"));
		return this;
	}

	public String getUiString(Locale l) {
		return ((java.lang.Boolean)o).booleanValue()?"Oui":"Non";
	}
	public String getUiInputString(Locale l) {
		return ((java.lang.Boolean)o).booleanValue()?"true":"false";
	}
	public A_Type setUiString(String s, Locale l) {
		o=java.lang.Boolean.parseBoolean((String)s);
		return this;
	}
	
	public boolean booleanValue() {
		return ((java.lang.Boolean)o).booleanValue();
	}

}
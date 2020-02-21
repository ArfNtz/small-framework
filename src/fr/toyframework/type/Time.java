package fr.toyframework.type;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

import fr.toyframework.type.format.TextFormat;

public class Time extends A_Type {
	private static final long serialVersionUID = 764657720601425481L;

	private int dbStringLength = 8;
	
	public static String sqlType = "CHAR(8)";
	
	public Time(){ o = new java.util.Date();}
	public Time(java.util.Date date){ o = date;}
	public void setTime(java.util.Date date){ o = date;}

	protected String setDbStringLength(String s) {
		return TextFormat.format(s, "0", dbStringLength, false, false);	
	}
	public A_Type setDbString(String s) throws TypeException {
		
		// NAU - NAUD-8SKHDT - 28/03/2012
		if (s!=null&& trim) s = s.trim();
		
		if (s!=null&&!s.trim().equals("")&&!s.trim().equals("0")) {
			super.setDbString(setDbStringLength(s));
		}
		return this;
	}
	protected Format getFormatDb() {
		if (fdb==null) fdb = new SimpleDateFormat("HHmmss");
		return fdb;
	}
	protected Format getFormatDbInput() {
		if (fdb==null) fdb = new SimpleDateFormat("HHmmssSS"); // FB2013339 - 22/04/2013
		return fdb;
	}
	protected Format getFormatUi(Locale l) {
		if (fui==null) fui =  new SimpleDateFormat("HH:mm:ss");
		return fui;
	}
	protected Format getFormatUiInput(Locale l) {
		if (fuii==null) fuii =  new SimpleDateFormat("HH:mm:ss");
		return fuii;
	}
	public java.util.Date getTime(){
		return (java.util.Date)o;
	}
}
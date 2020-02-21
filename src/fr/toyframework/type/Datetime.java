package fr.toyframework.type;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fr.toyframework.type.format.TextFormat;


public class Datetime extends A_Type {
	private static final long serialVersionUID = 222516860699138834L;
	
	private boolean dateVide = true;
	private int dbStringLength = 14;

	public static String sqlType = "CHAR(14)";

	public Datetime(){}
	public Datetime(java.util.Date date){ o = date; dateVide=false; }
	public static Datetime getInstance() { return new Datetime(); }
	public static Datetime getInstanceDateVide() {return new Datetime();}
	
	public void setDatetime(java.util.Date date){ o = date; dateVide=false; }	
	
	protected String setDbStringLength(String s) {
		return TextFormat.format(s, "0", dbStringLength, false, false);	
	}
	public String getDbString() throws TypeException {
		if (dateVide) return "0";
		else return o==null?null:""+getFormatDb().format(o)+"";
	}
	public A_Type setDbString(String s) throws TypeException {
		
		// NAU - NAUD-8SKHDT - 28/03/2012
		if (s!=null&& trim) s = s.trim();
		
		if (s!=null&&!s.trim().equals("")&&!s.trim().equals("0")) {
			dateVide = false;
			super.setDbString(setDbStringLength(s));
		}else{
			o = new java.util.Date();
		}
		return this;
	}
	public A_Type setUiString(String s, Locale l) throws TypeException {
		super.setUiString(s, l);
		if (o!=null) dateVide=false;
		return this;
	}
	protected Format getFormatDb() {
		if (fdb==null) fdb = new SimpleDateFormat("yyyyMMddHHmmss");
		return fdb;
	}
	protected Format getFormatDbInput() {
		if (fdb==null) fdb = new SimpleDateFormat("yyyyMMddHHmmss");
		return fdb;
	}
	protected Format getFormatUi(Locale l) {
		if (fui==null) fui =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return fui;
	}
	protected Format getFormatUiInput(Locale l) {
		if (fuii==null) fuii =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return fuii;
	}
	public java.util.Date getDate(){
		java.util.Date  retour = (java.util.Date)o;
		if (!dateVide){
			Calendar cal = Calendar.getInstance();
			cal.setTime(retour);
			cal.set(Calendar.HOUR_OF_DAY,12);
			cal.setTimeZone(new java.util.SimpleTimeZone(0, "TS"));
			retour =cal.getTime();
		}
		return retour;
	}
}
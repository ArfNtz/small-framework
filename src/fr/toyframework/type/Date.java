package fr.toyframework.type;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fr.toyframework.type.format.TextFormat;

public class Date extends A_Type {
	private static final long serialVersionUID = -1496388435933096764L;

	private boolean dateVide = true; 
	private boolean dateInfinie = false;	// FB2013119 - 11/03/2013 - Sert � tester les dates � 99999999, car elles sont perdues lors du parse
	private boolean dateSpecifique = false;	// FB2016709 - 25/08/2016 - remplace les dates � 88888888 car plus g�n�ral
	private int dbStringLength = 8;
	
	public static String sqlType = "CHAR(8)";
	
	public Date(){}
	public Date(java.util.Date date){ o = date; if (date==null) dateVide=true; else dateVide=false;}
	public static Date getInstance() { return new Date(new java.util.Date()); }
	public static Date getInstanceDateVide() { return new Date(); }
	public static Date getInstanceDateInfinie() {Date d = new Date(); d.dateVide = false; d.dateInfinie = true; return d;}
	
	public void setDate(java.util.Date date){ o = date; if (date==null) dateVide=true; else dateVide=false; }

	protected String setDbStringLength(String s) {
		return TextFormat.format(s, "0", dbStringLength, false, false);
	}
	
	public String getDbString() throws TypeException {
		if (dateVide) return "0";
		if (dateInfinie) return "99999999";
		//if (dateAHuit) return "88888888"; // FB2014649 - 27/10/2014
		else return o==null?null:""+getFormatDb().format(o)+"";
	}
	
	public A_Type setDbString(String s) throws TypeException {
		
		// NAU - NAUD-8SKHDT - 28/03/2012
		if (s!=null&& trim) s = s.trim();
		
		if (s!=null&&!s.trim().equals("")&&!s.trim().equals("0")) {
			dateVide = false;
			if (s.equals("99999999"))dateInfinie = true;
			if (java.lang.Integer.parseInt(s) >= 30000000)dateSpecifique = true; // FB2016709 - 25/08/2016
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
		if (fdb==null) fdb = new SimpleDateFormat("yyyyMMdd");
		return fdb;
	}
	protected Format getFormatDbInput() {
		if (fdb==null) fdb = new SimpleDateFormat("yyyyMMdd");
		return fdb;
	}
	protected Format getFormatUi(Locale l) {
		if (fui==null) fui =  new SimpleDateFormat("dd/MM/yyyy");
		return fui;
	}
	protected Format getFormatUiInput(Locale l) {
		if (fuii==null) fuii =  new SimpleDateFormat("dd/MM/yyyy");
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
		} else {
			retour = null;
		}
		return retour;
	}
	public boolean equals(Date obj) { 
		return !isNull()&&obj!=null&&getDate().equals(obj.getDate());
	}
	public boolean isDateVide() {return dateVide;}
	public void setDateVide(boolean dateVide) {this.dateVide = dateVide;}
	public boolean isDateInfinie() {return dateInfinie;}
	public void setDateInfinie(boolean dateInfinie) {this.dateInfinie = dateInfinie;}
	public boolean isDateSpecifique() {return dateSpecifique;}
	public void setDateSpecifique(boolean dateSpecifique) {this.dateSpecifique = dateSpecifique;}
}
package fr.toyframework.type;

import java.text.Format;
import java.util.Locale;

import fr.toyframework.type.format.TextFormat;

public class Char extends A_Type {
	private static final long serialVersionUID = -6007711429687534581L;

	protected static int maxlength = 1;
	public static String sqlType = "VARCHAR("+maxlength+")";

	public Char(){ }
	public Char(String s){ if (s!=null && s.length()>0) o = s.charAt(0) ; else o = ' ' ;  } // SH - 17/07/2013 - 296
	public Char(char c){ o = c;}

	protected Format getFormatDb() {
		if (fdb==null) fdb = new TextFormat(getMaxLength()); 
		return fdb;
	}
	protected Format getFormatDbInput() {
		return getFormatDb();
	}
	protected Format getFormatUi(Locale l) {
		return getFormatDb();
	}
	protected Format getFormatUiInput(Locale l) {
		return getFormatDb();
	}
	protected int getMaxLength() { return maxlength; }
	
	public char charValue() { return  (o!=null&&((String)o).length()>0 ) ? ((String)o).charAt(0) : ' ';  }
	
	public String stringValue() { return toString(); }
	
	/** si vide ou blanc **/
	public boolean isEmpty(){
		return toString().trim().isEmpty();
	}
}

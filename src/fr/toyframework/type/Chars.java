package fr.toyframework.type;

import java.text.Format;
import java.util.Locale;

import fr.toyframework.type.format.TextFormat;

public class Chars extends A_Type implements Comparable<Chars> {
	private static final long serialVersionUID = -8874907913549688783L;

	protected static int maxlength = 400;
	public static String sqlType = "VARCHAR("+maxlength+")";

	public Chars(){ }
	public Chars(String s){ o = s;}

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

	public String stringValue() { return (String)o; }
	
	public void trimToMaxLength() { 
		String _o = (String)o;
		if ( _o.length() > getMaxLength())
			o = _o.substring(0, getMaxLength());
	}
	
	/** si vide ou blanc **/
	public boolean isEmpty(){
		return stringValue().trim().isEmpty();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) || this.stringValue().equals(obj.toString());
	}
	
	public Chars toUpperCase() {
		return new Chars(stringValue().toUpperCase());
	}
	
	public int compareTo(Chars arg0) {
		if (isNull() && (arg0.isNull())) return 0;
 		return stringValue().compareTo(arg0.stringValue());
	}
	
	public Chars trim(){
		return new Chars(stringValue().trim());
	}
	
	public Chars prefixeByZero(int finalLength) {
		String _o = stringValue().trim();
		 while (_o.length() < finalLength)
			 _o = "0"+_o;
		 o = _o;
		 return this;
	}

	public Chars prefixeBySpace(int finalLength) {
		String _o = stringValue().trim();
		 while (_o.length() < finalLength)
			 _o = " "+_o;
		 o = _o;
		 return this;
	}
	
	public Chars suffixeBySpace(int finalLength) {
		 return suffixeBySpace(finalLength, true );
	}
	
	public Chars suffixeBySpace(int finalLength, boolean trim) {
		String _o = (trim)?stringValue().trim():stringValue();
		 while (_o.length() < finalLength)
			 _o = _o + " ";
		 o = _o;
		 return this;
	}
	
	public Chars supprimeZero() {
		boolean ok = false;
		String _o = stringValue().trim();
		 while (ok==false && !_o.equals("")) {
			 if (_o.substring(0, 1).equals("0"))
				 _o = _o.substring(1, _o.length());
			 else
				 ok = true;
		 }
		 o = _o;
		 return this;
	}
}
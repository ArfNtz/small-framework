package fr.toyframework.type;

import java.io.Serializable;
import java.text.Format;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

import fr.toyframework.model.A_SessionBean;
import fr.toyframework.type.format.MessageFormat;

public abstract class A_Type implements Cloneable , Serializable {
	private static final long serialVersionUID 	= -114888959209543104L;
	private static String bundleFilename = A_Type.class.getName();

	private static String KEY_TRIM 				= "trim";
	public static String FIELDNAME_SQLTYPE		="sqlType";
	public static String sqlType = null;
	public static String KEY_MANDATORY = "mandatory"; //ex : "Donn�e obligatoire."
	public static String KEY_BAD_FORMAT = "bad_format"; // ex: "Format incorrect : {0}"
	public static String KEY_MAXLENGTH = "maxlength"; // "Longueur max ({1}) d�pass�e : {0}"

	protected static boolean trim = new java.lang.Boolean(ResourceBundle.getBundle(bundleFilename).getString(KEY_TRIM));

	private boolean uiKey;
	private boolean dbKey;
	private boolean dbNotNull;

	private String dbPrefix="";
	private String dbSufix="";

	private String uiPrefix="";
	private String uiSufix="";

	//private Filter filterUi;
	//private Filter filterDb;

	protected Object o;

	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public A_Type clone_() throws TypeException {
		try {
			return (A_Type)clone();
		} catch (CloneNotSupportedException e) {
			throw new TypeException(e.getMessage(),e);
		}
	}
	public String toString() {
		String s = "";
		try { s = getDbString(); } catch (TypeException e) {}
		if (s==null) s = "";
		return s;
	}
	public boolean isNull() {
		return o==null;
	}
	public boolean equals(Object obj) { 
		return !isNull()&&obj!=null&&hashCode()==obj.hashCode();
	}
	public int hashCode() {
		return o.toString().hashCode();
	}

	protected Format format;
	protected Format formatInput;
	protected Format fdb;
	protected Format fui;
	protected Format fuii;
	protected Format getFormat() {return getFormatDb(); };
	protected Format getFormatInput() { return getFormatDbInput(); };
	protected abstract Format getFormatDb();
	protected abstract Format getFormatDbInput();
	protected abstract Format getFormatUi(Locale l);
	protected abstract Format getFormatUiInput(Locale l);

	public String getString() {
		return o==null?null:getFormat().format(o);
	}
	public void setString(String s) throws TypeException {
		if (s!=null) {
			try { o = getFormatInput().parseObject(s); }
			catch (ParseException e) { throw new TypeException(r(KEY_BAD_FORMAT,null)); }
		}
	}

	public String getDbString() throws TypeException {
		return o==null?null:dbPrefix+getFormatDb().format(o)+dbSufix;
	}
	public A_Type setDbString(String s) throws TypeException {
		
		// NAU - NAUD-8SKHDT - 28/03/2012
		if (s!=null&&trim) s = s.trim();
		
		try{
			if (s!=null) o = getFormatDbInput().parseObject(s);
		}catch (ParseException e){throw new TypeException(e.getMessage(),e);}
		
		return this;
	}
	public String getUiString() throws TypeException {
		return getUiString(A_SessionBean.getDefaultLocale());
	}
	public A_Type setUiString(String s) throws TypeException {
		setUiString(s, A_SessionBean.getDefaultLocale());
		return this;
	}
	public String getUiString(Locale l) throws TypeException {
		return o==null?"":uiPrefix+getFormatUi(l).format(o)+uiSufix;
	}
	public A_Type setUiString(String s, Locale l) throws TypeException {
		if ( isUiKey() && (s==null||s.trim().equals("")) ) {
			throw new TypeException(r(KEY_MANDATORY,l));
		} else if (s!=null) {
			try{
				o = getFormatUi(l).parseObject(s);
			} catch (ParseException e) {
				String key = KEY_BAD_FORMAT;
				if (e.getMessage()!=null&&e.getMessage().trim().equals(KEY_MAXLENGTH)) key = KEY_MAXLENGTH; 
				throw new TypeException(r(key,l,new Object[]{""+s,e.getErrorOffset()}));
			}
		} else {
			o = null;
		}
		return this;
	}
	public String getUiInputString(Locale l) throws TypeException {
				return o==null?"":getFormatUiInput(l).format(o);		
	}

	protected String r(String key,Locale l){
		if (l!=null) return ResourceBundle.getBundle(bundleFilename,l).getString(key);
		else return ResourceBundle.getBundle(bundleFilename).getString(key);
	}
	protected String r(String key,Locale l,Object[] args){
		return MessageFormat.format(r(key,l),args);
	}

	public boolean isUiKey() {
		return uiKey;
	}
	public void setUiKey(boolean uiKey) {
		this.uiKey = uiKey;
	}
	public boolean isDbKey() {
		return dbKey;
	}
	public void setDbKey(boolean dbKey) {
		this.dbKey = dbKey;
	}
	public void setDbNotNull(boolean dbNotNull) {
		this.dbNotNull = dbNotNull;
	}
	public boolean isDbNotNull() {
		return dbNotNull;
	}
	public A_Type setConcatDb(String prefix,String sufix) {
		dbPrefix = prefix; dbSufix = sufix;
		return this;
	}
	public A_Type setConcatUi(String prefix,String sufix) {
		uiPrefix = prefix; uiSufix = sufix;
		return this;
	}
}

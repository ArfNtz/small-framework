package fr.toyframework.type;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.ParseException;
import java.util.Locale;

public abstract class Numeric extends A_Type {
	private static final long serialVersionUID = 1018119964996030364L;

	public static String sqlType = "DECIMAL(18,3)";

	protected Format getFormatDb() {
		if (fdb==null) {
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			fdb = new DecimalFormat("#.###",dfs);
			((DecimalFormat)fdb).setDecimalSeparatorAlwaysShown(false);
			((DecimalFormat)fdb).setGroupingUsed(false);
		}
		return fdb;
	}
	protected Format getFormatDbInput() {
		return getFormatDb();
	}
	protected Format getFormatUi(Locale l) {
		if (fui==null) {
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator(',');
			fui = new DecimalFormat("#.###",dfs);
			((DecimalFormat)fui).setDecimalSeparatorAlwaysShown(false);
			((DecimalFormat)fui).setGroupingUsed(false);
		}
		return fui;
	}
	protected Format getFormatUiInput(Locale l) {
		if (fuii==null) {
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator(',');
			fuii = new DecimalFormat("#.###",dfs);
			((DecimalFormat)fuii).setDecimalSeparatorAlwaysShown(false);
			((DecimalFormat)fuii).setGroupingUsed(false);
		}
		return fuii;
	}

	public abstract Number getNumber();
	
	public A_Type setUiString(String s, Locale l) throws TypeException {
		if ( isUiKey() && (s==null||s.trim().equals("")) ) {
			throw new TypeException(r(KEY_MANDATORY,l));
		} else if (s!=null && !s.trim().equals("")) {
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
	
	
}
package fr.toyframework.type;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.util.Locale;

public class Rate extends Decimal {
	private static final long serialVersionUID = 8477513254451807429L;

	public static String sqlType = "DECIMAL(13,8)";

	public Rate() { super(); }
	public Rate(Double d) {	super(d); }
	public Rate(double d) {	super(d); }

	public double doubleValue() { return getNumber().doubleValue(); } 
	
	// FB2012469 - 26/04/2012
	protected Format getFormatDb() {
		if (fdb==null) {
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			fdb = new DecimalFormat("#.########",dfs);
			((DecimalFormat)fdb).setDecimalSeparatorAlwaysShown(false);
			((DecimalFormat)fdb).setGroupingUsed(false);
		}
		return fdb;
	}
	
	// FB2012469 - 26/04/2012
	protected Format getFormatUi(Locale l) {
		if (fui==null) {
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator(',');
			fui = new DecimalFormat("#.########",dfs);
			((DecimalFormat)fui).setDecimalSeparatorAlwaysShown(false);
			((DecimalFormat)fui).setGroupingUsed(false);
		}
		return fui;
	}
	
	// FB2012469 - 26/04/2012
	protected Format getFormatUiInput(Locale l) {
		if (fuii==null) {
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator(',');
			fuii = new DecimalFormat("#.########",dfs);
			((DecimalFormat)fuii).setDecimalSeparatorAlwaysShown(false);
			((DecimalFormat)fuii).setGroupingUsed(false);
		}
		return fuii;
	}
}
package fr.toyframework.type.format;

import java.math.BigDecimal;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class NumericFormat extends Format {

	private static final long serialVersionUID = 8475592090943294785L;

	public StringBuffer format(Object obj, StringBuffer toAppendTo,FieldPosition pos) {
		return toAppendTo.append(obj);
	}

	public Object parseObject(String source, ParsePosition pos) {
		return source;
	}
	
	public static float arrondi(float montant, int nbDecimal) {
		BigDecimal montantB = new BigDecimal(montant).divide(new BigDecimal("1"),nbDecimal,BigDecimal.ROUND_HALF_UP);
		return montantB.floatValue();
	}

	public static double arrondi(double montant, int nbDecimal){
		BigDecimal montantB = new BigDecimal(montant).divide(new BigDecimal("1"),nbDecimal,BigDecimal.ROUND_HALF_UP);
		return montantB.doubleValue();
	}
}
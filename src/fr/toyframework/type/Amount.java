package fr.toyframework.type;

public class Amount extends Decimal {
	private static final long serialVersionUID = 5634675520618368463L;

	public static String sqlType = "DECIMAL(18,3)";

	public Amount() { super(); }
	public Amount(Double d) { super(d); }
	public Amount(double d) { super(d); }
	
	public double doubleValue() { return getNumber().doubleValue(); }

}
package fr.toyframework.type;

public class Decimal extends Numeric {
	private static final long serialVersionUID = -9063896064617405050L;

	public static String sqlType = "DECIMAL(18,3)";

	public Decimal() { this.o = null; }
	public Decimal(Double d) {	this.o = d; }
	public Decimal(double d) {	this.o = new Double(d); }

	public double doubleValue() { return getNumber().doubleValue(); }
	// FB2013119 - 07/02/2013
	public Number getNumber() { if (o==null) return (Number)0; return (Number)o; } 
	//public Number getNumber() { if (o==null) o=new java.lang.Double(0); return (Number)o; } 
}
package fr.toyframework.type;

public class Integer extends Numeric {
	private static final long serialVersionUID = -7812701899676915750L;

	public static String sqlType = "DECIMAL(18)";
	
	public Integer() { this.o = null; }
	public Integer(java.lang.Integer integer) {	this.o = integer; }
	public Integer(int i) {	this.o = new java.lang.Integer(i); }
	public Integer(long i) {	this.o = new java.lang.Long(i); }
	public Integer(String s) {	this.o = new java.lang.Integer(s); } // SH - 29/11/2012 - 1269
	
	public void set(java.lang.Integer integer) {	this.o = integer; }
	public int intValue() { return getNumber().intValue(); }
	public long longValue() { return getNumber().longValue(); }
	// FB2013119 - 07/02/2013
	public Number getNumber() { if (o==null) return (Number)0; return (Number)o; }
	//public Number getNumber() { if (o==null) o=new java.lang.Integer(0); return (Number)o; }
}
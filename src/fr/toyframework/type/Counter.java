package fr.toyframework.type;

public class Counter extends Identifier {
	private static final long serialVersionUID = -3663977401008028527L;

	public static String sqlType = "DECIMAL(18) ";//auto_increment";
	public Counter(){ super(); }
	public Counter(int i){ o = new java.lang.Integer(i); }
	public Counter(long l){ o = new java.lang.Long(l);   }
	public Counter(Identifier i){ this(i.intValue()); }
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

}
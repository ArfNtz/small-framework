package fr.toyframework.type;

public class Text extends Chars {
	private static final long serialVersionUID = 6723701773806109664L;

	private static int maxlength = 5000;
	public static String sqlType = "VARCHAR("+maxlength+")";

	public Text(){ super(); }
	public Text(String s){ super(s);}
	
	protected int getMaxLength() { return maxlength; }
	
	public Text trim(){return new Text(stringValue().trim());}
}

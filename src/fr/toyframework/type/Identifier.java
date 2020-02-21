package fr.toyframework.type;

public class Identifier extends Integer {
	private static final long serialVersionUID = -5434699447262874713L;

	public static String sqlType = "DECIMAL(18) ";//auto_increment";
	public Identifier(){ super(); }
	public Identifier(int i) { o = new java.lang.Integer(i);}
	public Identifier(long l){ o = new java.lang.Long(l);   }
	public Identifier(String s){
		try{
			o = new java.lang.Long(s); 
		}catch (Throwable t){
			 o =  0l;
		}
	}
	public long longValue() { return getNumber().longValue(); }
	
//	@Override
//	public boolean equals(Object obj) {
//		return (obj instanceof Identifier) ?
//				super.equals(obj)
//			:	(obj instanceof Long) ? this.longValue()==(Long)obj : false;
//	}
}
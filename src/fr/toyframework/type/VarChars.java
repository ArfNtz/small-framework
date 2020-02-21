package fr.toyframework.type;

import java.text.ParseException;

public class VarChars extends Chars {
	
	private static final long serialVersionUID = 5443850347400098514L;

	public A_Type setDbString(String s) throws TypeException {
		try{
			if (s!=null) o = getFormatDbInput().parseObject(s.trim());
		}catch (ParseException e){throw new TypeException(e.getMessage(),e);}
		return this;
	}
}

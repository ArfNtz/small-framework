package fr.toyframework.type.format;

import java.util.Locale;

public class MessageFormat extends java.text.MessageFormat {
	private static final long serialVersionUID = 4929604371925820403L;
	
	
    public MessageFormat(String pattern) {
        super(pattern);
    }
    
    public MessageFormat(String pattern, Locale locale) {
		super(pattern, locale);
	}
    
    public static String format(String pattern, Object ... arguments) {
    	String _pattern = escapePattern(pattern);
    	java.text.MessageFormat temp = new java.text.MessageFormat(_pattern);
        return temp.format(arguments);
    }
	
	//WV - 887 - 28102014 - doublement de la simple quote centralis�e (pas dans les fichiers .properties)
	// Code pris dans struts
	static protected String escapePattern(String string) {
		if ((string == null) || (string.indexOf('\'') < 0)) {
			return string;
		}
		int n = string.length();
		StringBuffer sb = new StringBuffer(n);
		for (int i = 0; i < n; i++) {
			char ch = string.charAt(i);
			if (ch == '\'') {
				sb.append('\'');
			}
			sb.append(ch);
		}
		return sb.toString();
	}
	
}
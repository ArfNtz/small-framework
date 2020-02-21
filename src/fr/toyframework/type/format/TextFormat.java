package fr.toyframework.type.format;

import java.text.CharacterIterator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.StringCharacterIterator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import fr.toyframework.type.A_Type;

public class TextFormat extends Format {
	private static final long serialVersionUID = -217094366079970561L;
	
	private int maxlength;

	public TextFormat(int maxlength) {
		super();
		this.maxlength = maxlength;
	}
	
	public StringBuffer format(Object obj, StringBuffer toAppendTo,FieldPosition pos) {
		return toAppendTo.append(obj);
	}
	public Object parseObject(String source, ParsePosition pos) {
		return source;
	}
	public Object parseObject(String source) throws ParseException {
		if (source!=null&&source.length()>maxlength) throw new ParseException(A_Type.KEY_MAXLENGTH,maxlength);
		return source;		
	}	
	
	// tools
	public static String format(
		String donnee,
		String filler,
		int length,
		boolean cutFromRightToLeft,
		boolean fillAfter  // true =suffix, false=prefix
	) {
			String _filler = "";
			int l = donnee.length();
			if (l<length) {
				for(int i=l; i < length; i++) {
					_filler += filler;
				}
				if (fillAfter) {
					// fill after
					donnee += _filler;
				} else {
					// fill before
					donnee = _filler.concat(donnee);
				}
			} else if (l>length) {
				if (cutFromRightToLeft) {
					//cut last 'length' chars
					donnee = donnee.substring(l-length);
				} else {
					//cut first 'length' chars
					donnee = donnee.substring(0,length);
				}
			}
			return donnee;
	}

	public static String toXml(Map<Object, Object> map) {
		String xml = "";
		Iterator<Object> i = map.keySet().iterator();
		Object k;
		String name;
		while (i.hasNext()) {
			k = i.next();
			name = k.toString();
			xml += "<" + name + ">" + map.get(k) + "</" + name + ">";
		}
		return xml;
	}

	/**
	 * trim leading and trailing spaces
	 */
	public static String trimSpace(String s) {
		int start = 0;
		int limit = s.length();
		while ((limit > 0) && (s.charAt(limit - 1) <= ' '))
			--limit;
		while ((start < limit) && (s.charAt(start) <= ' '))
			++start;
		return s.substring(start, limit);
	}
	
	
	public static HashSet<String> toHashSet(String commaSeparatedString){
		HashSet<String> h = new HashSet<String>();
		if (commaSeparatedString==null)return h;
		StringTokenizer st = new StringTokenizer(commaSeparatedString,",");
		while (st.hasMoreElements()){
			h.add(st.nextToken());
		}
		return h;
	}

	public static String toJavaScriptInnerText(String text) {
		text = text.toString();
		String r = "";
		StringCharacterIterator i = new StringCharacterIterator(text);
		char c = i.first();
		while (c!=CharacterIterator.DONE) {
			if (
				(!Character.isISOControl(c)) ||
				(c=='\n')||
				(c=='\t')
			) {
				switch(c) {
					case '\'' :
						r = r+"\\'";
						break;
					case '\"' :
						r = r+"\\'";
						break;
					case '\n' :
						r = r+"<BR>";
						break;
					case '\t' :
						r = r+"&nbsp;&nbsp;&nbsp;&nbsp;";
						break;
					default :
						r = r + c;
						break;
				}
			}
			c = i.next();
		}
		return r.trim();
	}
	
	public static String prefixeByZero(String str, int finalLength) {
		 
		 String strPrefixee = str; 

		 while (strPrefixee.length() < finalLength)
			strPrefixee = "0" + strPrefixee;

		 return strPrefixee;
	}

	public static String suffixeByZero(String str, int finalLength) {

		 String strsuffixee = str; 

		 while (strsuffixee.length() < finalLength)
			strsuffixee = strsuffixee + "0";

		 return strsuffixee;
	}
	
	// FB2016264 - 12/04/2016
	public static String capitalizeFirstLetter(String value) {
		if (value == null) 			return null;
		if (value.length() == 0) 	return value;
		StringBuilder result = new StringBuilder(value);
		result.replace(0, 1, result.substring(0, 1).toUpperCase());
		result.replace(1, result.length(), result.substring(1, result.length()).toLowerCase());
		return result.toString();
	}
}
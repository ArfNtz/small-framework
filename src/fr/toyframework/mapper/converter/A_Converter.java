package fr.toyframework.mapper.converter;

import java.lang.reflect.Type;
import java.util.Locale;

import fr.toyframework.mapper.converter.ConverterException;

public abstract class A_Converter {
	protected Locale l;
	public A_Converter(Locale l){this.l=l;}
	public abstract Object convert(Object data,Object into,Class<?> intoClass,Type genericIntoType) throws ConverterException;	
}

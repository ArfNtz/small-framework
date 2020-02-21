package fr.toyframework.process;

import fr.toyframework.action.A_SetAction;
import fr.toyframework.mapper.MapperException;

public interface I_SetProcess {
	public static final String METHOD_DO_SET = "doSet";
	public abstract void doSet(A_SetAction a) throws ProcessException,MapperException;
}

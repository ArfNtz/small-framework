package fr.toyframework.process;

import fr.toyframework.action.A_AddAction;
import fr.toyframework.mapper.MapperException;

public interface I_AddProcess {
	public static final String METHOD_DO_ADD = "doAdd";
	public abstract void doAdd(A_AddAction a) throws ProcessException,MapperException;

}

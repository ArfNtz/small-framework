package fr.toyframework.process;

import fr.toyframework.action.A_CreateAction;
import fr.toyframework.mapper.MapperException;

public interface I_CreateProcess {
	public static final String METHOD_DO_CREATE = "doCreate";
	public abstract void doCreate(A_CreateAction a) throws ProcessException,MapperException;
}

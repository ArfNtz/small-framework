package fr.toyframework.process;

import fr.toyframework.action.A_RemoveAction;
import fr.toyframework.mapper.MapperException;

public interface I_RemoveProcess {
	public static final String METHOD_DO_REMOVE = "doRemove";
	public abstract void doRemove(A_RemoveAction a) throws ProcessException,MapperException;
}

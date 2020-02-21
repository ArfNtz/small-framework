package fr.toyframework.process;

import fr.toyframework.action.A_GetAction;
import fr.toyframework.mapper.MapperException;

public interface I_GetProcess {

	public static final String METHOD_DO_GET = "doGet";

	public abstract void doGet(A_GetAction a) throws ProcessException,MapperException;
}

package fr.toyframework.process;

import fr.toyframework.action.A_CallAction;
import fr.toyframework.mapper.MapperException;

public interface I_CallProcess {
	public static final String METHOD_DO_CALL = "doCall";
	public void doCall(A_CallAction a) throws ProcessException,MapperException;
}
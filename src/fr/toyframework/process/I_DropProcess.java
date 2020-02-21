package fr.toyframework.process;

import fr.toyframework.action.A_DropAction;
import fr.toyframework.mapper.MapperException;

public interface I_DropProcess {
	public static final String METHOD_DO_DROP = "doDrop";
	public abstract void doDrop(A_DropAction a) throws ProcessException,MapperException;
}

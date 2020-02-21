package fr.toyframework.process;

import java.util.List;

import fr.toyframework.action.A_ListAction;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.model.A_Model;

public interface I_ListProcess {

	public static final String METHOD_DO_LIST = "doList";

	public void doList(A_ListAction a) throws ProcessException,MapperException;
	public List<A_Model> getList();
	public void setList(List<A_Model> list);

}

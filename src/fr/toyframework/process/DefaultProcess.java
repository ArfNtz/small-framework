package fr.toyframework.process;

import java.util.ArrayList;
import java.util.List;

import fr.toyframework.action.A_AddAction;
import fr.toyframework.action.A_CallAction;
import fr.toyframework.action.A_CreateAction;
import fr.toyframework.action.A_DropAction;
import fr.toyframework.action.A_GetAction;
import fr.toyframework.action.A_ListAction;
import fr.toyframework.action.A_RemoveAction;
import fr.toyframework.action.A_SetAction;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.mapper.jdbc.A_JdbcMapper;
import fr.toyframework.model.A_Model;

public abstract class DefaultProcess extends A_Process implements I_ListProcess,I_DropProcess,I_CreateProcess,I_AddProcess,I_GetProcess,I_RemoveProcess,I_SetProcess,I_CallProcess {

	protected List<A_Model> list = new ArrayList<A_Model>();
	
	public final A_JdbcMapper getMapper() throws MapperException, ProcessException {
		return (A_JdbcMapper)super.getMapper();
	}

	public void doAdd(A_AddAction a) throws ProcessException,MapperException {
		// TODO copy action model to process model then insert
		getMapper().insert(getProcessModel());
		doList(null);
	}
	@SuppressWarnings("unchecked")
	public void doList(A_ListAction a) throws ProcessException, MapperException {
		setList((List<A_Model>)getMapper().selectAll(getProcessModel()));
	}
	public void doGet(A_GetAction a) throws ProcessException,MapperException {
		// TODO copy action model to process model then insert
		getMapper().select(getProcessModel());
	}
	public void doRemove(A_RemoveAction a) throws ProcessException,MapperException {
		A_RemoveAction _a = (A_RemoveAction)a;
		for (int i=0;i<_a.getListSelectedIndexes().length;i++) {
			if (_a.getListSelectedIndexes(i)) {
				getMapper().delete(getList().get(i));
			}
		}
		doList(null);
	}
	public void doSet(A_SetAction a) throws ProcessException,MapperException {
		A_SetAction _a = (A_SetAction)a; 
		for (int i=0;i<_a.getListSelectedIndexes().length;i++) {
			if (_a.getListSelectedIndexes(i))
				getMapper().update(getList().get(i));
		}
		doList(null);
	}
	public void doCreate(A_CreateAction a) throws ProcessException,MapperException {
		getMapper().create(getProcessModel());
	}
	public void doDrop(A_DropAction a) throws ProcessException,MapperException {
		getMapper().drop(getProcessModel());
		getList().clear();
	}
	public void doCall(A_CallAction a) throws ProcessException,MapperException {
		getMapper().call(getProcessModel());
	}
	
// getters and setters //
	public List<A_Model> getList() {
		if (list==null) { list = new ArrayList<A_Model>(); }
		return list;
	}
	public void setList(List<A_Model> list) {
		this.list = list;
	}

}

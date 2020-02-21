package fr.toyframework.process;

import fr.toyframework.action.A_Action;
import fr.toyframework.mapper.A_Mapper;
import fr.toyframework.mapper.MapperException;
import fr.toyframework.mapper.jdbc.BlankJdbcMapper;
import fr.toyframework.model.A_ProcessModel;
import fr.toyframework.model.BlankProcessModel;

public class BlankProcess extends A_Process {

	public A_ProcessModel createModel() {
		return new BlankProcessModel();
	}
	public A_Mapper createMapper() {
		return new BlankJdbcMapper();
	}
	public void addActions() {
	}
	public void init(A_Action a) throws ProcessException {
	}
	public void contextSystem (A_Action a) throws ProcessException {
	}
	public void context(A_Action a) throws ProcessException {
	}
	public void reset(A_Action a) throws ProcessException {		
	}
	public void process(A_Action a) throws ProcessException, MapperException {
	}

}

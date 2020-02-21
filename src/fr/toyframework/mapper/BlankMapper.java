package fr.toyframework.mapper;

import java.util.List;

import fr.toyframework.model.A_Model;
import fr.toyframework.model.A_ProcessModel;

public class BlankMapper extends A_Mapper {

	public void open() throws MapperException {
	}
	public void close() throws MapperException {
	}
	public void commit() throws MapperException {
	}
	public void rollback() throws MapperException {
	}
	public A_Model map(A_Model m) throws MapperException {
		return m;
	}
	public void init(A_Model m) throws MapperException {
	}
	public void call(A_ProcessModel m) throws MapperException {
	}
	public void create(A_ProcessModel m) throws MapperException {
	}
	public void drop(A_ProcessModel m) throws MapperException {
	}
	public void delete(A_ProcessModel m) throws MapperException {
	}
	public void insert(A_ProcessModel m) throws MapperException {
	}
	public void select(A_ProcessModel m) throws MapperException {
	}
	public List<A_ProcessModel> selectAll(A_ProcessModel m) throws MapperException {
		return null;
	}
	public void update(A_ProcessModel m) throws MapperException {
	}

}

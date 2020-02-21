package fr.toyframework.mapper.jdbc;

import java.util.List;

import fr.toyframework.mapper.MapperException;
import fr.toyframework.model.A_Model;

public class BlankJdbcMapper extends A_JdbcMapper {

	protected JdbcCounter createCounter() throws MapperException {
		return null;
	}
	public A_Model map(A_Model m) throws MapperException {
		return m;
	}
	public void call(A_Model m) throws MapperException {
	}
	public void create(A_Model m) throws MapperException {
	}
	public void initCounters(A_Model pm) throws MapperException {
	}
	public void delete(A_Model m) throws MapperException {
	}
	public void drop(A_Model m) throws MapperException {
	}
	public void init(A_Model m) throws MapperException {
	}
	public A_Model insert(A_Model m) throws MapperException {
		return m;
	}
	public A_Model select(A_Model m) throws MapperException {
		return m;
	}
	public List<A_Model> selectAll(A_Model m) throws MapperException {
		return null;
	}
	public void update(A_Model m) throws MapperException {
	}
	public boolean existStorage(A_Model m) throws MapperException {
		return false;
	}
	public A_Model insertOrUpdate(A_Model m) throws MapperException {
		return null;
	}
	public A_Model insertIfNotExist(A_Model m) throws MapperException {
		return null;
	}
}

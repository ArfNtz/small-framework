package fr.toyframework.mapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import fr.toyframework.connector.JdoConnect;
import fr.toyframework.model.A_Model;
import fr.toyframework.model.A_ProcessModel;

public abstract class JdoMapper extends A_Mapper {
	public void init(A_Model m) throws MapperException { /* TODO */ }
	public void call(A_ProcessModel m) throws MapperException { /* TODO */ }
	public void create(A_ProcessModel m) throws MapperException{ /* TODO */ }
	public void drop(A_ProcessModel m) throws MapperException{ /* TODO */ }
	public void insert(A_ProcessModel m) throws MapperException{ /* TODO */ }
	public void select(A_ProcessModel m) throws MapperException{ /* TODO */ }
	public List<A_ProcessModel> selectAll(A_ProcessModel m) throws MapperException{ /* TODO */ return null;}
	public void update(A_ProcessModel m) throws MapperException{/* TODO */}
	public void delete(A_ProcessModel m) throws MapperException{/* TODO */}
	public void open() throws MapperException {
		try {
			JdoConnect.getPersistenceManager().currentTransaction().begin();
		} catch (FileNotFoundException e) {
			throw new MapperException(e,null,null);
		} catch (IOException e) {
			throw new MapperException(e,null,null);
		}
	}
	public void commit() throws MapperException {
		try {
			JdoConnect.getPersistenceManager().currentTransaction().commit();
		} catch (FileNotFoundException e) {
			throw new MapperException(e,null,null);
		} catch (IOException e) {
			throw new MapperException(e,null,null);
		}
	}
	public void rollback() throws MapperException {
		try {
			if (JdoConnect.getPersistenceManager().currentTransaction().isActive())
			JdoConnect.getPersistenceManager().currentTransaction().rollback();
		} catch (FileNotFoundException e) {
			throw new MapperException(e,null,null);
		} catch (IOException e) {
			throw new MapperException(e,null,null);
		}
	}
	public void close() throws MapperException {
	}

}

package osama.atyponfinalproject.model.dao;

import java.util.List;
import java.io.IOException;

public interface GenericDao<T,I> {
	
	public abstract T save(T object) throws Exception;
	public abstract T getById(I id) throws Exception;
	public abstract boolean deleteById(I id) throws Exception;
	public abstract List<T> getAll() throws Exception;
	
	/*
	 * public void getConnection() { ///return connection; }
	 */
	
}

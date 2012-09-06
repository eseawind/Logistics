package Logistics.DAO;

import java.util.ArrayList;

public interface DAOInterface<T> {
	public T getDTOByID(T dto)throws Exception;
	public boolean insert(T dto)throws Exception;
	public boolean update(T dto)throws Exception;
	public boolean delete(T dto)throws Exception;
	public ArrayList<T> queryOnCondition(T dto,int start,int limit)throws Exception;
	public int queryQualifiedAmount(T dto)throws Exception;
	public ArrayList<T> queryAll()throws Exception;
}

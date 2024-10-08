package DAO;

import java.util.ArrayList;

/**
 *
 * @author robot
 */
public interface DAOinterface<T> {
    public int insert(T t);

    public int update(T t);

    public int delete(String t);

    public int restore(String t);

    public ArrayList<T> selectAll();

    public ArrayList<T> getAllStopped();

    public T selectById(String t);

    int getAutoIncrement();
}

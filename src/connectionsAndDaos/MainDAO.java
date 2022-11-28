package connectionsAndDaos;

import exeptions.DAOException;

import java.util.List;

public interface MainDAO<T> {
    public int isExists(String email, String password) throws DAOException;
    public void add(T object) throws DAOException;
    public void update(T object) throws DAOException;
    public void delete(int objectID) throws DAOException;
    public List<T> getAll() throws DAOException;
    public T getOne(int objectID) throws DAOException;
}

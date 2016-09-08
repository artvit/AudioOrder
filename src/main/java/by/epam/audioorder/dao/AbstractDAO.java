package by.epam.audioorder.dao;

import by.epam.audioorder.exception.DAOException;
import by.epam.audioorder.pool.ConnectionPool;
import by.epam.audioorder.pool.ConnectionPoolException;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDAO<T> {
    public abstract List<T> findAll(int page, int rowsPerPage) throws DAOException;
    public abstract T findById(long id) throws DAOException;
    public abstract void delete(T entity) throws DAOException;
    public abstract void insert(T entity) throws DAOException;
    public abstract void update(T entity) throws DAOException;
    Connection getConnection() throws ConnectionPoolException {
        return ConnectionPool.getInstance().getConnection();
    }
}

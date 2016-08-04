package by.epam.audioorder.dao;

import by.epam.audioorder.exception.DAOException;
import by.epam.audioorder.pool.ConnectionPool;
import by.epam.audioorder.pool.ConnectionPoolException;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDAO<T> {
    public abstract List<T> findAll() throws DAOException;
    public abstract T findById(long id) throws DAOException;
    public abstract void delete(T entity);
    public abstract void insert(T entity) throws DAOException;
    public abstract void update(T entity);
    Connection getConnection() throws ConnectionPoolException {
        return ConnectionPool.getInstance().getConnection();
    }
}

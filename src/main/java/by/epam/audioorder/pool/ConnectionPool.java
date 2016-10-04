package by.epam.audioorder.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();

    private static ConnectionPool instance;
    private static final int CAPACITY = 15;
    private static Lock accessLock = new ReentrantLock();
    private static AtomicBoolean initialized = new AtomicBoolean();

    private String url;
    private String user;
    private String password;

    private int maxConnectionsNumber;
    private int createdConnectionsNumber;
    private BlockingQueue<ProxyConnection> connectionQueue;
    private Set<ProxyConnection> busyConnections;

    private ConnectionPool(int capacity) {
        this.busyConnections = Collections.synchronizedSet(new HashSet<>());
        this.maxConnectionsNumber = capacity;
        this.connectionQueue = new ArrayBlockingQueue<>(capacity, true);
        this.createdConnectionsNumber = 0;
        ResourceBundle setting = ResourceBundle.getBundle("db");
        this.url = setting.getString("url");
        this.user = setting.getString("user");
        this.password = setting.getString("password");
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.error("Cannot register driver");
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        try {
            ProxyConnection connection;
            if (connectionQueue.isEmpty() && maxConnectionsNumber - createdConnectionsNumber > 0) {
                connection = new ProxyConnection(DriverManager.getConnection(url, user, password));
                ++createdConnectionsNumber;
                busyConnections.add(connection);
            } else {
                connection = connectionQueue.take();
                busyConnections.add(connection);
            }
            return connection;
        } catch (SQLException e) {
            throw new ConnectionPoolException("Cannot get connection", e);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Interrupted while waiting for available connection", e);
        }
    }

    public void returnConnection(ProxyConnection connection) throws ConnectionPoolException {
        try {
            busyConnections.remove(connection);
            connectionQueue.put(connection);
        } catch (InterruptedException e) {
            --createdConnectionsNumber;
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e1) {
                throw new ConnectionPoolException("Cannot close connection", e1);
            }
            throw new ConnectionPoolException("Cannot return connection", e);
        }
    }

    public void close() {
        while (createdConnectionsNumber > 0) {
            try {
                ProxyConnection connection = instance.connectionQueue.poll(5, TimeUnit.SECONDS);
                connection.closeInnerConnection();
            } catch (InterruptedException e) {
                LOGGER.error("Cannot get connection for real closing from pool.", e);
            } catch (SQLException e) {
                LOGGER.error("Cannot close real connection", e);
            }
            --createdConnectionsNumber;
        }
        for (ProxyConnection connection : busyConnections) {
            try {
                connection.closeInnerConnection();
            } catch (SQLException e) {
                LOGGER.error("Cannot close connection");
            }
        }
        for (ProxyConnection connection : connectionQueue) {
            try {
                connection.closeInnerConnection();
            } catch (SQLException e) {
                LOGGER.error("Cannot close connection");
            }
        }
    }

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (!initialized.get()) {
            accessLock.lock();
            localInstance = instance;
            if (localInstance == null) {
                instance = localInstance = new ConnectionPool(CAPACITY);
            }
            accessLock.unlock();
        }
        return localInstance;
    }
}

package com.epam.hr.data;

import com.epam.hr.exception.DaoRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String URL_PROPERTY = "url";
    private static final String DATABASE_PROPERTIES_PATH = "database.properties";
    private static final String HEROKU_DATABASE_PROPERTIES_PATH = "dbheroku.properties";
    private static final String CONNECTIONS_COUNT_PROPERTY = "connections.count";
    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static ConnectionPool instance;

    private final Lock connectionLock = new ReentrantLock();
    private final LinkedList<ProxyConnection> availableConnections;
    private final LinkedList<ProxyConnection> usedConnections;
    private int connectionsTotalCount;
    private Semaphore semaphore;

    private ConnectionPool() {
        if (instance != null) {
            throw new DaoRuntimeException("Attempt to create second instance of connection pool");
        }

        availableConnections = new LinkedList<>();
        usedConnections = new LinkedList<>();
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        connectionLock.lock();
        try {
            semaphore.acquire();

            connection = availableConnections.pop();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
            LOGGER.error("Unable to get connection", e);
            Thread.currentThread().interrupt();
        } finally {
            connectionLock.unlock();
        }

        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (!(connection instanceof ProxyConnection)) {
            LOGGER.warn("Attempt to return invalid connection to the pool {}", connection);
            return;
        }

        usedConnections.remove(connection);
        ProxyConnection proxyConnection = (ProxyConnection) connection;
        availableConnections.add(proxyConnection);
        semaphore.release();
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            INSTANCE_LOCK.lock();
            try {
                if (instance == null) {
                    ConnectionPool pool = new ConnectionPool();
                    pool.init();

                    instance = pool;
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }

        return instance;
    }

    private void init() {
        try {
            tryEstablishConnections(HEROKU_DATABASE_PROPERTIES_PATH);
            semaphore = new Semaphore(connectionsTotalCount);
        } catch (SQLException e) {
            throw new DaoRuntimeException(e);
        }
    }

    private void tryEstablishConnections(String connectionPropPath) throws SQLException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Properties properties = new Properties();

        try {
            properties.load(classLoader.getResourceAsStream(connectionPropPath));
        } catch (IOException e) {
            throw new DaoRuntimeException("Incorrect db properties path");
        }

        String url = properties.getProperty(URL_PROPERTY);
        String connectionsCountProperty = properties.getProperty(CONNECTIONS_COUNT_PROPERTY);
        connectionsTotalCount = Integer.parseInt(connectionsCountProperty);

        for (int i = 0; i < connectionsTotalCount; i++) {
            Connection connection = DriverManager.getConnection(url, properties);

            LOGGER.info("Created connection {}", connection);

            ProxyConnection proxyConnection = new ProxyConnection(connection, this);
            availableConnections.add(proxyConnection);
        }
    }

    public void destroy() {
        connectionLock.lock();
        try {
            semaphore.acquire(connectionsTotalCount);
            while (!usedConnections.isEmpty()) {
                ProxyConnection connection = usedConnections.pop();
                connection.destroy();
            }

            while (!availableConnections.isEmpty()) {
                ProxyConnection connection = availableConnections.pop();
                connection.destroy();
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(e);
            Thread.currentThread().interrupt();
        } finally {
            connectionLock.unlock();
            semaphore.release(connectionsTotalCount);
        }
    }
}

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
import java.util.concurrent.locks.Condition;
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
    private final Condition connectionAvailable = connectionLock.newCondition();
    private final LinkedList<ProxyConnection> availableConnections;
    private final LinkedList<ProxyConnection> usedConnections;

    private ConnectionPool() {
        availableConnections = new LinkedList<>();
        usedConnections = new LinkedList<>();
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        connectionLock.lock();
        try {
            while (availableConnections.isEmpty()) {
                connectionAvailable.await();
            }

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
        connectionLock.lock();

        try {
            if (!(connection instanceof ProxyConnection)) {
                LOGGER.warn("Attempt to return invalid connection to the pool {}", connection);
                return;
            }

            usedConnections.remove(connection);
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            availableConnections.add(proxyConnection);
            connectionAvailable.signal();
        } finally {
            connectionLock.unlock();
        }
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

    // todo
    private void init() {
        try {
            tryEstablishConnections(DATABASE_PROPERTIES_PATH);
        } catch (SQLException ex) {
            throw new DaoRuntimeException(ex);
        }

//        try {
//            tryEstablishConnections(HEROKU_DATABASE_PROPERTIES_PATH);
//        } catch (SQLException e) {
//
//        }
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
        int connectionsCount = Integer.parseInt(connectionsCountProperty);


        for (int i = 0; i < connectionsCount; i++) {
            Connection connection = DriverManager.getConnection(url, properties);

            LOGGER.info("Created connection {}", connection);

            ProxyConnection proxyConnection = new ProxyConnection(connection, this);
            availableConnections.add(proxyConnection);
        }
    }


    // TODO lock
    public void destroy() {
        try {
            while (!usedConnections.isEmpty()) {
                ProxyConnection connection = usedConnections.pop();
                connection.destroy();
            }

            while (!availableConnections.isEmpty()) {
                ProxyConnection connection = availableConnections.pop();
                connection.destroy();
            }
        } catch (SQLException sqlException) {
            LOGGER.error(sqlException);
        }
    }
}

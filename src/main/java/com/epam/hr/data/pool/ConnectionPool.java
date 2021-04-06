package com.epam.hr.data.pool;

import com.epam.hr.exception.DaoRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static final int MAX_CONNECTION_WAIT_TIME_IN_SECONDS = 10;
    private static final AtomicReference<ConnectionPool> INSTANCE = new AtomicReference<>();

    private final BlockingQueue<ProxyConnection> availableConnections;
    private final AtomicInteger connectionsTotalCount;

    private ConnectionPool() {
        this(new MySQLConnectionFactory());
    }

    private ConnectionPool(AbstractConnectionFactory connectionFactory) {
        if (INSTANCE.get() != null) { // Preventing second instantiation
            throw new DaoRuntimeException("Attempt to create second instance of the ConnectionP ool");
        }

        List<Connection> connections = connectionFactory.establishConnections();
        connectionsTotalCount = new AtomicInteger(connections.size());
        if (connectionsTotalCount.get() < 1) { // Preventing init mistake
            throw new DaoRuntimeException("Pool contains no connections");
        }

        this.availableConnections = new LinkedBlockingQueue<>();
        for (Connection connection : connections) {
            availableConnections.add(new ProxyConnection(connection, this));
        }
    }

    /**
     * @return sql connection
     * @throws DaoRuntimeException if connection wasn't provided within 10 seconds
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            LOGGER.debug("attempt to get connection {}", Thread.currentThread().getName());
            connection = availableConnections.poll(MAX_CONNECTION_WAIT_TIME_IN_SECONDS, TimeUnit.SECONDS);
            if (connection == null) {
                throw new DaoRuntimeException("Max connection wait time exceeded");
            }

            LOGGER.debug("Thread {} got connection", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /* package private access */
    void releaseConnection(Connection connection) {
        if (!(connection instanceof ProxyConnection)) {
            LOGGER.warn("Attempt to add extra connection to the pool");
            return;
        }

        ProxyConnection proxyConnection = (ProxyConnection) connection;
        LOGGER.debug("Thread {} released connection", Thread.currentThread().getName());
        availableConnections.add(proxyConnection);
    }

    /**
     * @return instance of the connection pool
     */
    public static ConnectionPool getInstance() {
        if (INSTANCE.get() == null) {
            INSTANCE_LOCK.lock();
            try {
                if (INSTANCE.get() == null) {
                    ConnectionPool pool = new ConnectionPool();
                    INSTANCE.set(pool);
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }

        return INSTANCE.get();
    }

    public void destroy() {
        try {
            while (connectionsTotalCount.get() > 0) {
                ProxyConnection connection = availableConnections.take();
                connection.destroy();
                connectionsTotalCount.set(connectionsTotalCount.decrementAndGet());
                LOGGER.debug("Connection destroyed available connections {}", connectionsTotalCount);
            }
            LOGGER.info("All connections destroyed, available connections {}", connectionsTotalCount);
        } catch (SQLException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /* package private, for test proposes only */
    static void instantiateForTest(AbstractConnectionFactory factory) {
        if (INSTANCE.get() == null) {
            INSTANCE_LOCK.lock();
            try {
                if (INSTANCE.get() == null) {
                    ConnectionPool pool = new ConnectionPool(factory);
                    INSTANCE.set(pool);
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
    }

    /* package private for test */
    int getAvailableConnectionsCount() {
        return availableConnections.size();
    }

    /* package private for test */
    int getTotalConnectionsCount() {
        return availableConnections.size();
    }
}

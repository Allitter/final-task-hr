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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

;

public final class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static final int MAX_CONNECTION_WAIT_TIME_IN_SECONDS = 10;
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> availableConnections;
    private final ConnectionFactory connectionFactory;
    private AtomicInteger connectionsTotalCount;

    /* package private access */
    ConnectionPool() {
        this(new ConnectionFactory());
    }

    /* package private access */
    ConnectionPool(ConnectionFactory connectionFactory) {
        if (instance != null) {
            throw new DaoRuntimeException("Attempt to create second instance of connection pool");
        }

        this.availableConnections = new LinkedBlockingQueue<>();
        this.connectionFactory = connectionFactory;
    }

    public Connection getConnection() {
        if (connectionsTotalCount.get() < 1) {
            throw new DaoRuntimeException("Pool contains no connections");
        }

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

    public void releaseConnection(Connection connection) {
        if (!(connection instanceof ProxyConnection)) {
            return;
        }

        ProxyConnection proxyConnection = (ProxyConnection) connection;
        LOGGER.debug("Thread {} released connection", Thread.currentThread().getName());
        availableConnections.add(proxyConnection);
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
        List<Connection> connections = connectionFactory.establishConnections();
        connectionsTotalCount = new AtomicInteger(connections.size());

        for (Connection connection : connections) {
            availableConnections.add(new ProxyConnection(connection, this));
        }
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
}

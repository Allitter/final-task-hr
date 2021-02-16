package com.epam.hr.data.pool;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ConnectionPoolTest {
    private static final int EXPECTED_CONNECTIONS_COUNT_AFTER_DESTROY = 0;

    /* prevents using default connection factory */
    @BeforeAll
    public static void setup() {
        AbstractConnectionFactory connectionFactory = getConnectionFactoryMockCreatingOneConnection();
        ConnectionPool.instantiateForTest(connectionFactory);
    }

    private static AbstractConnectionFactory getConnectionFactoryMockCreatingOneConnection() {
        Connection connection = Mockito.mock(Connection.class);
        MySQLConnectionFactory connectionFactory = Mockito.mock(MySQLConnectionFactory.class);
        Mockito.when(connectionFactory.establishConnections())
                .thenReturn(Collections.singletonList(connection));

        return connectionFactory;
    }

    @Test
    void testGetInstanceShouldReturnTheSameInstance() {
        ConnectionPool first = ConnectionPool.getInstance();

        ConnectionPool second = ConnectionPool.getInstance();

        assertSame(first, second);
    }

    @Test
    void testGetConnectionShouldReturnConnectionFromThePool() {
        ConnectionPool pool = ConnectionPool.getInstance();
        int countBeforeGet = pool.getAvailableConnectionsCount();

        pool.getConnection();

        int countAfterGet = pool.getAvailableConnectionsCount();
        assertEquals(countBeforeGet - 1, countAfterGet);
    }

    @Test
    void testReleaseConnectionShouldReturnConnectionToThePool() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = Mockito.mock(ProxyConnection.class);
        int countBeforeRelease = pool.getAvailableConnectionsCount();

        pool.releaseConnection(connection);

        int countAfterRelease = pool.getAvailableConnectionsCount();
        assertEquals(countBeforeRelease + 1, countAfterRelease);
    }

    @Test
    void testDestroyShouldCloseAllConnections() {
        ConnectionPool pool = ConnectionPool.getInstance();

        pool.destroy();

        int actualConnectionsCount = pool.getTotalConnectionsCount();
        assertEquals(EXPECTED_CONNECTIONS_COUNT_AFTER_DESTROY, actualConnectionsCount);
    }
}
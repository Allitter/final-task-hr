package com.epam.hr.data.pool;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.Collections;

/* package private */
class ConnectionPoolTest {
    private static final int EXPECTED_CONNECTIONS_COUNT_AFTER_DESTROY = 0;

    /* prevents using default connection factory */
    @BeforeClass
    public static void setup() {
        ConnectionFactory connectionFactory = getConnectionFactoryMockCreatingOneConnection();
        ConnectionPool.instantiateForTest(connectionFactory);
    }

    private static ConnectionFactory getConnectionFactoryMockCreatingOneConnection() {
        Connection connection = Mockito.mock(Connection.class);
        MySQLConnectionFactory connectionFactory = Mockito.mock(MySQLConnectionFactory.class);
        Mockito.when(connectionFactory.establishConnections())
                .thenReturn(Collections.singletonList(connection));

        return connectionFactory;
    }

    /* package private */
    @Test
    void testGetInstanceShouldReturnTheSameInstance() {
        ConnectionPool first = ConnectionPool.getInstance();

        ConnectionPool second = ConnectionPool.getInstance();

        Assertions.assertSame(first, second);
    }

    /* package private */
    @Test
    void testGetConnectionShouldReturnConnectionFromThePool() {
        ConnectionPool pool = ConnectionPool.getInstance();
        int countBeforeGet = pool.getAvailableConnectionsCount();

        pool.getConnection();

        int countAfterGet = pool.getAvailableConnectionsCount();
        Assertions.assertEquals(countBeforeGet - 1, countAfterGet);
    }

    /* package private */
    @Test
    void testReleaseConnectionShouldReturnConnectionToThePool() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = Mockito.mock(ProxyConnection.class);
        int countBeforeRelease = pool.getAvailableConnectionsCount();

        pool.releaseConnection(connection);

        int countAfterRelease = pool.getAvailableConnectionsCount();
        Assertions.assertEquals(countBeforeRelease + 1, countAfterRelease);
    }

    /* package private */
    @Test
    void testDestroyShouldCloseAllConnections() {
        ConnectionPool pool = ConnectionPool.getInstance();

        pool.destroy();

        int actualConnectionsCount = pool.getTotalConnectionsCount();
        Assertions.assertEquals(EXPECTED_CONNECTIONS_COUNT_AFTER_DESTROY, actualConnectionsCount);
    }
}
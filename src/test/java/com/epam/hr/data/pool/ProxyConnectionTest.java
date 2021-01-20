package com.epam.hr.data.pool;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

class ProxyConnectionTest {

    @Test
    void testDestroyShouldCloseConnection() throws SQLException {
        ConnectionPool pool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        ProxyConnection proxyConnection = new ProxyConnection(connection, pool);
        proxyConnection.destroy();

        Mockito.verify(connection, Mockito.times(1)).close();
    }

    @Test
    void testCloseShouldReturnConnectionToThePool() {
        ConnectionPool pool = Mockito.mock(ConnectionPool.class);
        Connection connection = Mockito.mock(Connection.class);
        ProxyConnection proxyConnection = new ProxyConnection(connection, pool);
        proxyConnection.close();

        Mockito.verify(pool, Mockito.times(1)).releaseConnection(Mockito.same(proxyConnection));
    }
}
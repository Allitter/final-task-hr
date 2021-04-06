package com.epam.hr.data.pool;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MySQLAbstractConnectionFactoryTest {
    private static final String PROPERTIES_PATH = "testdbconnection.properties";
    private static final int EXPECTED_CONNECTIONS_COUNT = 2;

    @Test
    void testEstablishConnectionsShouldEstablishConnectionsWhenPropertiesAreCorrect() {
        MySQLConnectionFactory connectionFactory = new MySQLConnectionFactory();

        List<Connection> connections = connectionFactory.establishConnections(PROPERTIES_PATH);

        int actualCount = connections.size();
        assertEquals(EXPECTED_CONNECTIONS_COUNT, actualCount);
    }
}
package com.epam.hr.data.pool;

import com.epam.hr.exception.DaoRuntimeException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/* package private access */
class ConnectionFactory {
    private static final String HEROKU_DATABASE_PROPERTIES_PATH = "dbheroku.properties";
    private static final String CONNECTIONS_COUNT_PROPERTY = "connections.count";
    private static final String URL_PROPERTY = "url";

    /* package private access */
    List<Connection> establishConnections() {
        Properties properties = getConnectionProperties();
        return createConnections(properties);
    }

    private List<Connection> createConnections(Properties properties) {
        String url = properties.getProperty(URL_PROPERTY);
        String connectionsCountProperty = properties.getProperty(CONNECTIONS_COUNT_PROPERTY);
        int connectionsTotalCount = Integer.parseInt(connectionsCountProperty);

        List<Connection> connections = new LinkedList<>();
        for (int i = 0; i < connectionsTotalCount; i++) {
            Connection connection = createConnection(url, properties);
            connections.add(connection);
        }

        return connections;
    }

    private Properties getConnectionProperties() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Properties properties = new Properties();

        try {
            properties.load(classLoader.getResourceAsStream(HEROKU_DATABASE_PROPERTIES_PATH));
            return properties;
        } catch (IOException e) {
            throw new DaoRuntimeException("Incorrect db properties path");
        }
    }

    private Connection createConnection(String url, Properties properties) {
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new DaoRuntimeException("Unable to create connection", e);
        }
    }
}

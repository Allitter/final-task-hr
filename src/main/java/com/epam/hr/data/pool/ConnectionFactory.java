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
abstract class ConnectionFactory {
    private static final String CONNECTIONS_COUNT_PROPERTY = "connections.count";
    private static final String URL_PROPERTY = "url";

    protected abstract String getPropertiesPath();
    protected abstract void loadDriverIfNotLoaded();

    /* package private access */
    List<Connection> establishConnections() {
        String path = getPropertiesPath();
        return establishConnections(path);
    }

    /* package private access, for test proposes */
    List<Connection> establishConnections(String propertiesPath) {
        loadDriverIfNotLoaded();
        Properties properties = getConnectionProperties(propertiesPath);
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

    private Properties getConnectionProperties(String propertiesPath) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Properties properties = new Properties();

        try {
            properties.load(classLoader.getResourceAsStream(propertiesPath));
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

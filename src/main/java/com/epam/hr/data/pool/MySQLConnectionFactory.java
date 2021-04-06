package com.epam.hr.data.pool;

import com.epam.hr.exception.DaoRuntimeException;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/* package private access */
class MySQLConnectionFactory extends AbstractConnectionFactory {
    private static final String HEROKU_DATABASE_PROPERTIES_PATH = "properties/app.properties";

    @Override
    protected String getPropertiesPath() {
        return HEROKU_DATABASE_PROPERTIES_PATH;
    }

    @Override
    protected void loadDriverIfNotLoaded() {
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            throw new DaoRuntimeException(e);
        }
    }
}

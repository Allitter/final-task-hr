package com.epam.hr.data.pool;

import com.epam.hr.exception.DaoRuntimeException;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/* package private access */
class MySQLConnectionFactory extends ConnectionFactory {
    private static final String HEROKU_DATABASE_PROPERTIES_PATH = "dbheroku.properties";

    @Override
    protected String getPropertiesPath() {
        return HEROKU_DATABASE_PROPERTIES_PATH;
    }

    @Override
    protected void loadDriverIfNotLoaded() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            throw new DaoRuntimeException(e);
        }
    }
}

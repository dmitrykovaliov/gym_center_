package com.dk.gym.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

class PoolManager {

    private static final Logger LOGGER = LogManager.getLogger();

    private String url;
    private Properties properties;
    private int initPoolSize;
    private int maxPoolSize;

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("resource/database");

     PoolManager() {
        url = resourceBundle.getString("db.url");

        properties = new Properties();
        properties.put("user", resourceBundle.getString("db.user"));
        properties.put("password", resourceBundle.getString("db.password"));
        properties.put("characterEncoding", resourceBundle.getString("db.encoding"));
        properties.put("useUnicode", resourceBundle.getString("db.useUnicode"));

        initPoolSize = Integer.parseInt(resourceBundle.getString("db.pool.initsize"));
        maxPoolSize = Integer.parseInt(resourceBundle.getString("db.pool.maxsize"));

    }

     int getInitPoolSize() {
        return initPoolSize;
    }

     int getMaxPoolSize() {
        return maxPoolSize;
    }

    ProxyConnection getConnection() throws SQLException {
        ProxyConnection proxyConnection;
            proxyConnection = new ProxyConnection(DriverManager.getConnection(url, properties));
        return proxyConnection;
    }

    void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.fatal("Driver not registered: ", e);
            throw new RuntimeException("Driver not registered: ", e);
        }
    }

    void deregisterDrivers() {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Drivers not deregistered: ", e);
        }
    }
}

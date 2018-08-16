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

/**
 * The Class PoolManager. Support pool, initialize, register driver, deregister driver, providing connection.
 */
class PoolManager {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The url. */
    private String url;
    
    /** The properties. */
    private Properties properties;
    
    /** The init pool size. */
    private int initPoolSize;
    
    /** The max pool size. */
    private int maxPoolSize;

    /** The Constant resourceBundle. */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("resource/database");

     /**
      * Instantiates a new pool manager.
      */
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

     /**
      * Gets the inits the pool size.
      *
      * @return the inits the pool size
      */
     int getInitPoolSize() {
        return initPoolSize;
    }

     /**
      * Gets the max pool size.
      *
      * @return the max pool size
      */
     int getMaxPoolSize() {
        return maxPoolSize;
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws SQLException the SQL exception
     */
    ProxyConnection getConnection() throws SQLException {
        ProxyConnection proxyConnection;
            proxyConnection = new ProxyConnection(DriverManager.getConnection(url, properties));
        return proxyConnection;
    }

    /**
     * Register driver.
     */
    void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.fatal("Driver not registered: ", e);
            throw new RuntimeException("Driver not registered: ", e);
        }
    }

    /**
     * Deregister drivers.
     */
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

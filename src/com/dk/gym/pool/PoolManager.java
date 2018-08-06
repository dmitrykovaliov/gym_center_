package com.dk.gym.pool;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

class PoolManager {

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
}

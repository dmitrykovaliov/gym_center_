package com.dk.gym.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.DatabaseManager;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int POOL_INIT_ATTEMPTS = 3;
    private static final int BUFFER_CONNECTIONS = 2;

    private static AtomicBoolean instantiated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private BlockingQueue<ProxyConnection> freeConnections;
    private Deque<ProxyConnection> boundConnections;

    private String url;
    private Properties properties;

    private int initPoolSize;
    private int maxPoolSize;

    private int poolSize;     // size of pool, total quantity of connections in both queues
    private int initAttempts; // factual attempts to initialize pool


    private static ConnectionPool instance;

    private ConnectionPool() {

        registerDriver();

        url = DatabaseManager.getProperty("db.url");

        properties = new Properties();
        properties.put("user", DatabaseManager.getProperty("db.user"));
        properties.put("password", DatabaseManager.getProperty("db.password"));
        properties.put("characterEncoding", DatabaseManager.getProperty("db.encoding"));
        properties.put("useUnicode", DatabaseManager.getProperty("db.useUnicode"));

        initPoolSize = Integer.parseInt(DatabaseManager.getProperty("db.pool.initsize"));
        maxPoolSize = Integer.parseInt(DatabaseManager.getProperty("db.pool.maxsize"));

        poolSize = 0;
        initAttempts = 0;

        freeConnections = new LinkedBlockingQueue<>();
        boundConnections = new ArrayDeque<>();
    }

    public static ConnectionPool getInstance() {
        if (!instantiated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instantiated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    //protection from clone
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.fatal("Driver was not registered", e);
            throw new RuntimeException("Driver was not registered", e);
        }
    }

    public void initPool() {

        poolSize = createConnection(initPoolSize - poolSize);

        LOGGER.log(Level.DEBUG, "First" + poolSize);

        if (initPoolSize == poolSize) {

            LOGGER.log(Level.DEBUG, "Second" + poolSize);


            LOGGER.log(Level.INFO, "Successfully initialized connection pool. PoolSize: " + poolSize);

        } else if (poolSize > 0 && poolSize < initPoolSize) {
            if (POOL_INIT_ATTEMPTS > initAttempts) {
                initAttempts++;

                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.WARN, "Interrupted!", e);
                    Thread.currentThread().interrupt();
                }

                initPool();
            }
        } else {
            LOGGER.fatal("Couldn't init connection pool");
            throw new RuntimeException();
        }
    }

    private int createConnection(int size) {
        for (int i = 0; i < size; i++) {
            try {
                freeConnections.add(new ProxyConnection(DriverManager.getConnection(url, properties)));
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        return freeConnections.size();
    }

    public ProxyConnection receiveConnection() {
        ProxyConnection connection = null;

        try {
            monitorPool();

            connection = freeConnections.take();
            boundConnections.add(connection);

            poolSize = freeConnections.size() + boundConnections.size();

        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, "Interrupted!", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }


    public void releaseConnection(ProxyConnection connection) {
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }

            if (poolSize > 0) {
                boolean operationResult = boundConnections.remove(connection);
                if (operationResult) {
                    freeConnections.add(connection);
                }
            }

            poolSize = freeConnections.size() + boundConnections.size();

        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Connection was not realeased");
        }
        monitorPool();
    }

    private void monitorPool() {  //todo check all carefully in the morning

        if (poolSize < initPoolSize) {

            createConnection(initPoolSize - poolSize);

        } else if (poolSize < maxPoolSize) {

            int bufferDifference = BUFFER_CONNECTIONS - freeConnections.size();

            if (bufferDifference > 0) {
                createConnection(bufferDifference);
            } else {
                for (int i = bufferDifference; i < 0; i++) {
                    try {
                        freeConnections.take();
                    } catch (InterruptedException e) {
                        LOGGER.log(Level.WARN, "Interrupted!", e);
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } else {
            LOGGER.log(Level.INFO, "All connections are given out");
        }
    }

    public void closePool() {

        for (int i = 0; i < freeConnections.size(); i++) {
            try {
                ProxyConnection connection = boundConnections.pop();
                connection.closeConnection();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }

        for (int i = 0; i < boundConnections.size(); i++) {
            try {
                ProxyConnection connection = freeConnections.take();
                connection.closeConnection();
            } catch (InterruptedException | SQLException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
}

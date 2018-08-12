package com.dk.gym.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

   private PoolManager poolManager;

    private int initPoolSize;
    private int maxPoolSize;

    private int currentPoolSize;  // size of pool, total quantity of connections in both queues
    private int initAttempts; // factual attempts to initialize pool


    private static ConnectionPool instance;

    private ConnectionPool() {

        registerDriver();

        currentPoolSize = 0;
        initAttempts = 0;

        poolManager = new PoolManager();

        initPoolSize = poolManager.getInitPoolSize();
        maxPoolSize = poolManager.getMaxPoolSize();

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
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.fatal("Driver was not registered: ", e);
            throw new RuntimeException("Driver was not registered: ", e);
        }
    }

    public void initPool() {

        currentPoolSize = createConnection(initPoolSize - currentPoolSize);

        if (initPoolSize == currentPoolSize) {

            LOGGER.log(Level.INFO, "Successfully initialized connection pool. PoolSize: " + currentPoolSize);

        } else if (initPoolSize > currentPoolSize) {
            if (POOL_INIT_ATTEMPTS > initAttempts) {
                initAttempts++;

                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.WARN, "Interrupted: ", e);
                    Thread.currentThread().interrupt();
                }

                initPool();
            }
        } else {
            LOGGER.fatal("Couldn't init connection pool");
            throw new RuntimeException("Couldn't init connection pool");
        }
    }

    private int createConnection(int size) {
        for (int i = 0; i < size; i++) {
            try {
                freeConnections.add(poolManager.getConnection());
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Connection not created: ", e);
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

            currentPoolSize = freeConnections.size() + boundConnections.size();

        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, "Interrupted!", e);
            Thread.currentThread().interrupt();
        }

        LOGGER.log(Level.INFO, "ReceiveConnection_freeConnections: " + freeConnections.size());
        LOGGER.log(Level.INFO, "ReceiveConnection_boundConnections: " + boundConnections.size());
        LOGGER.log(Level.INFO, "ReceiveConnection_PoolSize: " + currentPoolSize);

        return connection;
    }


    public void releaseConnection(ProxyConnection connection) {
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
            if (currentPoolSize > 0 && boundConnections.remove(connection)) {
                freeConnections.add(connection);
            }
            currentPoolSize = freeConnections.size() + boundConnections.size();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Connection was not released: ", e);
        }

        monitorPool();
    }

    private void monitorPool() {

        if (currentPoolSize < initPoolSize) {

            createConnection(initPoolSize - currentPoolSize);

        } else if (currentPoolSize < maxPoolSize) {

            if (BUFFER_CONNECTIONS >= freeConnections.size()) {
                createConnection(BUFFER_CONNECTIONS - freeConnections.size());
            } else {

                for (int i = 0; i < freeConnections.size() - initPoolSize; i++) {
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
                ProxyConnection connection = freeConnections.take();
                connection.closeConnection();
            } catch (InterruptedException | SQLException e) {
                LOGGER.log(Level.ERROR, "FreeConnections not closed: ", e);
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
            LOGGER.log(Level.ERROR, "DeregisterDrivers:", e);
        }
    }
}

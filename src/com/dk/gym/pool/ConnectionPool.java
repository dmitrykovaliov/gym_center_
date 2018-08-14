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

    private int currentPoolSize;
    private int initAttempts;

    private static ConnectionPool instance;

    private ConnectionPool() {
        currentPoolSize = 0;
        initAttempts = 0;

        poolManager = new PoolManager();

        poolManager.registerDriver();

        initPoolSize = poolManager.getInitPoolSize();
        maxPoolSize = poolManager.getMaxPoolSize();

        freeConnections = new LinkedBlockingQueue<>(maxPoolSize);
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
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
            LOGGER.fatal("Not init connection pool");
            throw new RuntimeException("Not init connection pool");
        }
    }

    public ProxyConnection receiveConnection() {
        ProxyConnection connection = null;

        try {
            monitorPool();

            connection = freeConnections.take();
            boundConnections.add(connection);

            currentPoolSize = freeConnections.size() + boundConnections.size();

        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, "Receive connection interrupted: ", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) {

        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
            if (currentPoolSize > 0 && boundConnections.remove(connection)) {
                freeConnections.put(connection);
            }
            currentPoolSize = freeConnections.size() + boundConnections.size();
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, "ReleaseConnection interrupted: ", e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Connection not released: ", e);
        }


        monitorPool();
    }

    private void monitorPool() {

        if (currentPoolSize < initPoolSize) {
            createConnection(initPoolSize - currentPoolSize);
        } else if (currentPoolSize == initPoolSize) {
            if (freeConnections.size() < BUFFER_CONNECTIONS) {
                createConnection(BUFFER_CONNECTIONS - freeConnections.size());
            }
        } else if (currentPoolSize < maxPoolSize) {
            if (freeConnections.size() < BUFFER_CONNECTIONS) {
                createConnection(BUFFER_CONNECTIONS - freeConnections.size());
            } else {
                closeConnection();
            }
        } else {
            if (freeConnections.size() >= BUFFER_CONNECTIONS) {
                closeConnection();
            }
        }
        LOGGER.log(Level.INFO, "FreeConnections: " + freeConnections.size());
        LOGGER.log(Level.INFO, "BoundConnections: " + boundConnections.size());
        LOGGER.log(Level.INFO, "PoolSize: " + currentPoolSize);
    }

    public void closePool() {
        for (int i = 0; i < freeConnections.size(); i++) {
            closeConnection();
        }
        poolManager.deregisterDrivers();
    }


    public int getCurrentPoolSize() {
        return currentPoolSize;
    }

    private int createConnection(int size) {
        for (int i = 0; i < size; i++) {
            try {
                freeConnections.put(poolManager.getConnection());
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARN, "CreateConnection interrupted: ", e);
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Connections not created: ", e);
            }
        }

        return freeConnections.size();
    }

    private void closeConnection() {
        try {
            ProxyConnection connection = freeConnections.take();
            connection.closeConnection();
        } catch (InterruptedException | SQLException e) {
            LOGGER.log(Level.ERROR, "FreeConnections not closed: ", e);
        }
    }
}

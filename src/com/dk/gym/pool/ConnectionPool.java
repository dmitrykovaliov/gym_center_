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

/**
 * The Class ConnectionPool. Maintain connections to support access to database of many users.
 * This class is decision maker of closing connections.
 */
public final class ConnectionPool {
    
    private static final Logger LOGGER = LogManager.getLogger();

    /** The Constant POOL_INIT_ATTEMPTS. */
    private static final int POOL_INIT_ATTEMPTS = 3;
    
    /** The Constant BUFFER_CONNECTIONS. */
    private static final int BUFFER_CONNECTIONS = 2;

    /** The instantiated. */
    private static AtomicBoolean instantiated = new AtomicBoolean(false);
    
    /** The lock. */
    private static ReentrantLock lock = new ReentrantLock();
    
    /** The free connections. */
    private BlockingQueue<ProxyConnection> freeConnections;
    
    /** The bound connections. */
    private Deque<ProxyConnection> boundConnections;

    /** The pool manager. */
    private PoolManager poolManager;

    /** The init pool size. */
    private int initPoolSize;
    
    /** The max pool size. */
    private int maxPoolSize;

    /** The current pool size. */
    private int currentPoolSize;
    
    /** The init attempts. */
    private int initAttempts;

    /** The instance. */
    private static ConnectionPool instance;

    /**
     * Instantiates a new connection pool.
     */
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

    /**
     * Gets the single instance of ConnectionPool.
     *
     * @return single instance of ConnectionPool
     */
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

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Inits the pool.
     */
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

    /**
     * Receive connection.
     *
     * @return the proxy connection
     */
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

    /**
     * Release connection.
     *
     * @param connection the connection
     */
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

    /**
     * Monitor pool.
     */
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

    /**
     * Close pool.
     */
    public void closePool() {
        for (int i = 0; i < freeConnections.size(); i++) {
            closeConnection();
        }
        poolManager.deregisterDrivers();
    }


    /**
     * Gets the current pool size.
     *
     * @return the current pool size
     */
    public int getCurrentPoolSize() {
        return currentPoolSize;
    }

    /**
     * Creates the connection.
     *
     * @param size the size
     * @return the int
     */
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

    /**
     * Close connection.
     */
    private void closeConnection() {
        try {
            ProxyConnection connection = freeConnections.take();
            connection.closeConnection();
        } catch (InterruptedException | SQLException e) {
            LOGGER.log(Level.ERROR, "FreeConnections not closed: ", e);
        }
    }
}

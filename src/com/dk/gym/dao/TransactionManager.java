package com.dk.gym.dao;

import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * The Class TransactionManager. Operate transaction via set all dao to one connection.
 */
public class TransactionManager {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The connection. */
    private ProxyConnection connection;

    /**
     * Instantiates a new transaction manager.
     */
    public TransactionManager() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    /**
     * Start transaction.
     *
     * @param daoList the dao list
     */
    public void startTransaction(AbstractDao... daoList) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Autocommit not changed: " + e);
        }

        for (AbstractDao dao : daoList) {
            dao.close();
            dao.setConnection(connection);
        }
        LOGGER.log(Level.INFO, "Transaction started");
    }

    /**
     * Commit.
     */
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Transaction not committed: " + e);
        }
        LOGGER.log(Level.INFO, "Transaction commited");
    }

    /**
     * Rollback.
     */
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Transaction not rolled back: " + e);
        }
        LOGGER.log(Level.INFO, "Transaction rollbacked");
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     */
    public ProxyConnection getConnection() {
        return connection;
    }
}

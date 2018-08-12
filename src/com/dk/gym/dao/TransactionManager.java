package com.dk.gym.dao;

import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class TransactionManager {

    private static final Logger LOGGER = LogManager.getLogger();

    private ProxyConnection connection;

    public TransactionManager() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

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

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Transaction not committed: " + e);
        }
        LOGGER.log(Level.INFO, "Transaction commited");
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Transaction not rolled back: " + e);
        }
        LOGGER.log(Level.INFO, "Transaction rollbacked");
    }

    public ProxyConnection getConnection() {
        return connection;
    }
}

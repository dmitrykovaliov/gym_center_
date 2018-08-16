package com.dk.gym.pool;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * The Class ProxyConnection. Represent connection class.
 * Replacing close method in order to revert connections to pool instead of closing them.
 */
public class ProxyConnection implements Connection {

    /** The connection. */
    private Connection connection;

    /**
     * Instantiates a new proxy connection.
     *
     * @param connection the connection
     */
    //package visibility
    ProxyConnection(Connection connection) {
        this.connection = connection;
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createStatement()
     */
    @Override
    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String)
     */
    @Override
    public PreparedStatement prepareStatement(String s) throws SQLException {
        return connection.prepareStatement(s);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareCall(java.lang.String)
     */
    @Override
    public CallableStatement prepareCall(String s) throws SQLException {
        return connection.prepareCall(s);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#nativeSQL(java.lang.String)
     */
    @Override
    public String nativeSQL(String s) throws SQLException {
        return connection.nativeSQL(s);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setAutoCommit(boolean)
     */
    @Override
    public void setAutoCommit(boolean b) throws SQLException {
        connection.setAutoCommit(b);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getAutoCommit()
     */
    @Override
    public boolean getAutoCommit() throws SQLException {
        return connection.getAutoCommit();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#commit()
     */
    @Override
    public void commit() throws SQLException {
        connection.commit();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#rollback()
     */
    @Override
    public void rollback() throws SQLException {
        connection.rollback();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#close()
     */
    @Override
    public void close() {
        ConnectionPool.getInstance().releaseConnection(this);
    }

    /**
     * Close connection.
     *
     * @throws SQLException the SQL exception
     */
    //package visibility
    void closeConnection() throws SQLException {
        connection.close();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#isClosed()
     */
    @Override
    public boolean isClosed() throws SQLException {
        return connection.isClosed();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getMetaData()
     */
    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return connection.getMetaData();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setReadOnly(boolean)
     */
    @Override
    public void setReadOnly(boolean b) throws SQLException {
        connection.setReadOnly(b);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#isReadOnly()
     */
    @Override
    public boolean isReadOnly() throws SQLException {
        return connection.isReadOnly();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setCatalog(java.lang.String)
     */
    @Override
    public void setCatalog(String s) throws SQLException {
        connection.setCatalog(s);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getCatalog()
     */
    @Override
    public String getCatalog() throws SQLException {
        return connection.getCatalog();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setTransactionIsolation(int)
     */
    @Override
    public void setTransactionIsolation(int i) throws SQLException {
        connection.setTransactionIsolation(i);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getTransactionIsolation()
     */
    @Override
    public int getTransactionIsolation() throws SQLException {
        return connection.getTransactionIsolation();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getWarnings()
     */
    @Override
    public SQLWarning getWarnings() throws SQLException {
        return connection.getWarnings();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#clearWarnings()
     */
    @Override
    public void clearWarnings() throws SQLException {
        connection.clearWarnings();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createStatement(int, int)
     */
    @Override
    public Statement createStatement(int i, int i1) throws SQLException {
        return connection.createStatement(i, i1);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, int, int)
     */
    @Override
    public PreparedStatement prepareStatement(String s, int i, int i1) throws SQLException {
        return connection.prepareStatement(s, i, i1);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareCall(java.lang.String, int, int)
     */
    @Override
    public CallableStatement prepareCall(String s, int i, int i1) throws SQLException {
        return connection.prepareCall(s, i, i1);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getTypeMap()
     */
    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return connection.getTypeMap();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setTypeMap(java.util.Map)
     */
    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        connection.setTypeMap(map);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setHoldability(int)
     */
    @Override
    public void setHoldability(int i) throws SQLException {
        connection.setHoldability(i);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getHoldability()
     */
    @Override
    public int getHoldability() throws SQLException {
        return connection.getHoldability();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setSavepoint()
     */
    @Override
    public Savepoint setSavepoint() throws SQLException {
        return connection.setSavepoint();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setSavepoint(java.lang.String)
     */
    @Override
    public Savepoint setSavepoint(String s) throws SQLException {
        return connection.setSavepoint(s);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#rollback(java.sql.Savepoint)
     */
    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        connection.rollback(savepoint);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
     */
    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        connection.releaseSavepoint(savepoint);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createStatement(int, int, int)
     */
    @Override
    public Statement createStatement(int i, int i1, int i2) throws SQLException {
        return connection.createStatement(i, i1, i2);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
     */
    @Override
    public PreparedStatement prepareStatement(String s, int i, int i1, int i2) throws SQLException {
        return connection.prepareStatement(s, i, i1, i2);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
     */
    @Override
    public CallableStatement prepareCall(String s, int i, int i1, int i2) throws SQLException {
        return connection.prepareCall(s, i, i1, i2);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, int)
     */
    @Override
    public PreparedStatement prepareStatement(String s, int i) throws SQLException {
        return connection.prepareStatement(s, i);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
     */
    @Override
    public PreparedStatement prepareStatement(String s, int[] ints) throws SQLException {
        return connection.prepareStatement(s, ints);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, java.lang.String[])
     */
    @Override
    public PreparedStatement prepareStatement(String s, String[] strings) throws SQLException {
        return connection.prepareStatement(s, strings);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createClob()
     */
    @Override
    public Clob createClob() throws SQLException {
        return connection.createClob();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createBlob()
     */
    @Override
    public Blob createBlob() throws SQLException {
        return connection.createBlob();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createNClob()
     */
    @Override
    public NClob createNClob() throws SQLException {
        return connection.createNClob();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createSQLXML()
     */
    @Override
    public SQLXML createSQLXML() throws SQLException {
        return connection.createSQLXML();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#isValid(int)
     */
    @Override
    public boolean isValid(int i) throws SQLException {
        return connection.isValid(i);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setClientInfo(java.lang.String, java.lang.String)
     */
    @Override
    public void setClientInfo(String s, String s1) throws SQLClientInfoException {
        connection.setClientInfo(s, s1);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setClientInfo(java.util.Properties)
     */
    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        connection.setClientInfo(properties);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getClientInfo(java.lang.String)
     */
    @Override
    public String getClientInfo(String s) throws SQLException {
        return connection.getClientInfo(s);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getClientInfo()
     */
    @Override
    public Properties getClientInfo() throws SQLException {
        return connection.getClientInfo();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createArrayOf(java.lang.String, java.lang.Object[])
     */
    @Override
    public Array createArrayOf(String s, Object[] objects) throws SQLException {
        return connection.createArrayOf(s, objects);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createStruct(java.lang.String, java.lang.Object[])
     */
    @Override
    public Struct createStruct(String s, Object[] objects) throws SQLException {
        return connection.createStruct(s, objects);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setSchema(java.lang.String)
     */
    @Override
    public void setSchema(String s) throws SQLException {
        connection.setSchema(s);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getSchema()
     */
    @Override
    public String getSchema() throws SQLException {
        return connection.getSchema();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#abort(java.util.concurrent.Executor)
     */
    @Override
    public void abort(Executor executor) throws SQLException {
        connection.abort(executor);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setNetworkTimeout(java.util.concurrent.Executor, int)
     */
    @Override
    public void setNetworkTimeout(Executor executor, int i) throws SQLException {
        connection.setNetworkTimeout(executor, i);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getNetworkTimeout()
     */
    @Override
    public int getNetworkTimeout() throws SQLException {
        return connection.getNetworkTimeout();
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return connection.unwrap(aClass);
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return connection.isWrapperFor(aClass);
    }
}

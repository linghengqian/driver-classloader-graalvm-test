package org.apache.commons.dbcp2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public class TesterStatement extends AbandonedTrace implements Statement {

    protected final Connection _connection;
    protected boolean _open = true;
    protected final long _rowsUpdated = 1;
    protected final boolean _executeResponse = true;
    protected int _maxFieldSize = 1024;
    protected long _maxRows = 1024;
    protected boolean _escapeProcessing;
    protected int _queryTimeout = 1000;
    protected String _cursorName;
    protected int _fetchDirection = 1;
    protected int _fetchSize = 1;
    protected int _resultSetConcurrency = 1;
    protected int _resultSetType = 1;
    private int _resultSetHoldability = 1;
    protected ResultSet _resultSet;
    protected boolean _sqlExceptionOnClose;

    public TesterStatement(final Connection conn) {
        _connection = conn;
    }

    public TesterStatement(final Connection conn, final int resultSetType, final int resultSetConcurrency) {
        _connection = conn;
        _resultSetType = resultSetType;
        _resultSetConcurrency = resultSetConcurrency;
    }

    public TesterStatement(final Connection conn, final int resultSetType, final int resultSetConcurrency,
                           final int resultSetHoldability) {
        _connection = conn;
        _resultSetType = resultSetType;
        _resultSetConcurrency = resultSetConcurrency;
        _resultSetHoldability = resultSetHoldability;
    }

    @Override
    public void addBatch(final String sql) throws SQLException {
        checkOpen();
    }

    @Override
    public void cancel() throws SQLException {
        checkOpen();
    }

    protected void checkOpen() throws SQLException {
        if(!_open) {
            throw new SQLException("Connection is closed.");
        }
    }

    @Override
    public void clearBatch() throws SQLException {
        checkOpen();
    }

    @Override
    public void clearWarnings() throws SQLException {
        checkOpen();
    }

    @Override
    public void close() throws SQLException {
        if (_sqlExceptionOnClose) {
            throw new SQLException("TestSQLExceptionOnClose");
        }
        if (!_open) {
            return;
        }

        _open = false;
        if (_resultSet != null) {
            _resultSet.close();
            _resultSet = null;
        }
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public boolean execute(final String sql) throws SQLException {
        checkOpen();
        if("invalid".equals(sql)) {
            throw new SQLException("invalid query");
        }
        return _executeResponse;
    }

    @Override
    public boolean execute(final String sql, final int autoGeneratedKeys)
        throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public boolean execute(final String sql, final int[] columnIndexes)
        throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public boolean execute(final String sql, final String[] columnNames)
        throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public int[] executeBatch() throws SQLException {
        checkOpen();
        return new int[0];
    }

    @Override
    public long[] executeLargeBatch() throws SQLException {
        checkOpen();
        return new long[0];
    }

    @Override
    public long executeLargeUpdate(final String sql) throws SQLException {
        checkOpen();
        return _rowsUpdated;
    }

    @Override
    public long executeLargeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public long executeLargeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public long executeLargeUpdate(final String sql, final String[] columnNames) throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public ResultSet executeQuery(final String sql) throws SQLException {
        checkOpen();
        if("null".equals(sql)) {
            return null;
        }
        if("invalid".equals(sql)) {
            throw new SQLException("invalid query");
        }
        if ("broken".equals(sql)) {
            throw new SQLException("broken connection");
        }
        if("select username".equals(sql)) {
            final String userName = ((TesterConnection) _connection).getUserName();
            final Object[][] data = {{userName}};
            return new TesterResultSet(this, data);
        }
        if (_queryTimeout > 0 && _queryTimeout < 5) {
            throw new SQLException("query timeout");
        }
        return new TesterResultSet(this);
    }

    @Override
    public int executeUpdate(final String sql) throws SQLException {
        checkOpen();
        return (int) _rowsUpdated;
    }

    @Override
    public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public Connection getConnection() throws SQLException {
        checkOpen();
        return _connection;
    }

    @Override
    public int getFetchDirection() throws SQLException {
        checkOpen();
        return _fetchDirection;
    }

    @Override
    public int getFetchSize() throws SQLException {
        checkOpen();
        return _fetchSize;
    }

    @Override
    public ResultSet getGeneratedKeys() {
        return new TesterResultSet(this);
    }

    @Override
    public long getLargeMaxRows() throws SQLException {
        checkOpen();
        return _maxRows;
    }

    @Override
    public long getLargeUpdateCount() throws SQLException {
        checkOpen();
        return _rowsUpdated;
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        checkOpen();
        return _maxFieldSize;
    }

    @Override
    public int getMaxRows() throws SQLException {
        checkOpen();
        return (int) _maxRows;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        checkOpen();
        return false;
    }

    @Override
    public boolean getMoreResults(final int current) throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        checkOpen();
        return _queryTimeout;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        checkOpen();
        if (_resultSet == null) {
            _resultSet = new TesterResultSet(this);
        }
        return _resultSet;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        checkOpen();
        return _resultSetConcurrency;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        checkOpen();
        return _resultSetHoldability;
    }

    @Override
    public int getResultSetType() throws SQLException {
        checkOpen();
        return _resultSetType;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        checkOpen();
        return (int) _rowsUpdated;
    }


    @Override
    public SQLWarning getWarnings() throws SQLException {
        checkOpen();
        return null;
    }

    @Override
    public boolean isClosed() {
        return !_open;
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public void setCursorName(final String name) throws SQLException {
        checkOpen();
        _cursorName = name;
    }

    @Override
    public void setEscapeProcessing(final boolean enable) throws SQLException {
        checkOpen();
        _escapeProcessing = enable;
    }

    @Override
    public void setFetchDirection(final int direction) throws SQLException {
        checkOpen();
        _fetchDirection = direction;
    }

    @Override
    public void setFetchSize(final int rows) throws SQLException {
        checkOpen();
        _fetchSize = rows;
    }

    @Override
    public void setLargeMaxRows(final long max) throws SQLException {
        checkOpen();
        _maxRows = max;
    }

    @Override
    public void setMaxFieldSize(final int max) throws SQLException {
        checkOpen();
        _maxFieldSize = max;
    }

    @Override
    public void setMaxRows(final int max) throws SQLException {
        checkOpen();
        _maxRows = max;
    }

    @Override
    public void setPoolable(final boolean poolable) throws SQLException {
        throw new SQLException("Not implemented.");
    }

    @Override
    public void setQueryTimeout(final int seconds) throws SQLException {
        checkOpen();
        _queryTimeout = seconds;
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        throw new SQLException("Not implemented.");
    }
}

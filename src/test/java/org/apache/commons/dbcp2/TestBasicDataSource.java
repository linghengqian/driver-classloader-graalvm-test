package org.apache.commons.dbcp2;

import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBasicDataSource extends TestConnectionPool {
    private static final String CATALOG = "test catalog";

    @BeforeAll
    public static void setUpClass() {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.dbcp2.StackMessageLog");
    }

    protected BasicDataSource ds;

    protected BasicDataSource createDataSource() {
        return new BasicDataSource();
    }

    @Override
    protected Connection getConnection() throws Exception {
        return ds.getConnection();
    }

    @BeforeEach
    public void setUp() {
        ds = createDataSource();
        ds.setDriverClassName("org.apache.commons.dbcp2.TesterDriver");
        ds.setUrl("jdbc:apache:commons:testdriver");
        ds.setMaxTotal(getMaxTotal());
        ds.setMaxWaitMillis(getMaxWaitMillis());
        ds.setDefaultAutoCommit(Boolean.TRUE);
        ds.setDefaultReadOnly(Boolean.FALSE);
        ds.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        ds.setDefaultCatalog(CATALOG);
        ds.setUsername("userName");
        ds.setPassword("password");
        ds.setValidationQuery("SELECT DUMMY FROM DUAL");
        ds.setConnectionInitSqls(Arrays.asList("SELECT 1", "SELECT 2"));
        ds.setDriverClassLoader(new TesterClassLoader());
        ds.setJmxName("org.apache.commons.dbcp2:name=test");
    }

    @Override
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
        ds.close();
        ds = null;
    }

    @Test
    public void testDriverClassLoader() throws Exception {
        getConnection();
        final ClassLoader cl = ds.getDriverClassLoader();
        assertNotNull(cl);
        assertTrue(cl instanceof TesterClassLoader);
        assertTrue(((TesterClassLoader) cl).didLoad(ds.getDriverClassName()));
    }

    @Override
    @Test
    public void testPooling() throws Exception {
        ds.setAccessToUnderlyingConnectionAllowed(true);
        super.testPooling();
    }
}

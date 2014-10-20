package ruking.db;

import java.sql.SQLException;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

// this is a roll-your-own implementation for the DataSource interface such that
// we can use char set encoding easily, where we can not really using other
// standard
// DataSource implementation
public class ThePoolingDataSource extends PoolingDataSource
{
	private String driverClassName;
	private String connectUrl;
	
	public String getDriverClassName()
	{
		return this.driverClassName;
	}
	
	public String getConnectUrl()
	{
		return this.connectUrl;
	}
	
	public ThePoolingDataSource(String driverClassName, String connectUrl) throws SQLException
	{
		super();

		this.driverClassName = driverClassName;
		this.connectUrl = connectUrl;
		
		try
		{
			Class.forName(driverClassName, true, Thread.currentThread().getContextClassLoader());
		}
		catch (ClassNotFoundException ex)
		{
			throw new SQLException("Could not load JDBC driver class [" + driverClassName + "]");
		}

		// First, we'll need a ObjectPool that serves as the
		// actual pool of connections.
		//
		// We'll use a GenericObjectPool instance, although
		// any ObjectPool implementation will suffice.
		GenericObjectPool connectionPool = new GenericObjectPool(null);
		connectionPool.setMaxActive(10);
		connectionPool.setMaxIdle(10);
		connectionPool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
		// If a positive maxWait value is supplied, the borrowObject() will
		// block
		// for at most that many milliseconds, after which a
		// NoSuchElementException
		// will be thrown. If maxWait is non-positive, the borrowObject() method
		// will
		// block indefinitely
		connectionPool.setMaxWait(-1);

		// Next, we'll create a ConnectionFactory that the
		// pool will use to create Connections.
		// We'll use the DriverManagerConnectionFactory,
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectUrl, null);

		// Now we'll create the PoolableConnectionFactory, which wraps
		// the "real" Connections created by the ConnectionFactory with
		// the classes that implement the pooling functionality.
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);

		// Finally, we create the PoolingDriver itself,
		// passing in the object pool we created.
		super.setPool(connectionPool);
	}
}
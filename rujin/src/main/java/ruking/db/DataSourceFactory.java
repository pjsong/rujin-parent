package ruking.db;

import javax.sql.DataSource;

import java.sql.SQLException;

// this is a singleton object
public class DataSourceFactory
{
	private static ThePoolingDataSource ds;
	private static boolean hasInit = false;
	
	private static void init(String hostName,String dbName,String user,String password) throws SQLException
	{	
		String jdbcUrl = "jdbc:mysql://"+hostName+":3306/"+dbName+"?user="+user+"&password="+password+"&useUnicode=true&characterEncoding=UTF-8";
		ds = new ThePoolingDataSource("org.gjt.mm.mysql.Driver", jdbcUrl);
        hasInit = true;
	}

	public static synchronized DataSource getDataSource(String hostName,String dbName,String user,String password) throws SQLException
	{
		if (!hasInit)
		{
			init(hostName,dbName,user,password);
		}

		// ping the database to see if it is still alive, if not, re-establish the datasource
		try
		{
			checkHeartBeat(ds);
		}
		catch (SQLException e)
		{
			ds = new ThePoolingDataSource(ds.getDriverClassName(), ds.getConnectUrl());
		}

		return ds;		
	}
	
	// if no heartbeat, will throw exception
	private static void checkHeartBeat(DataSource ds) throws SQLException
	{
		QueryRunner runner = new QueryRunner(ds, new MDTMySQLRowMapper(), false);
		runner.query("select 1");
	}
}
package ruking.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.*;
import javax.sql.DataSource;

public class TransRunner
{
	private DataSource ds;

	private boolean doLogging;

	private RowMapper rowMapper;

	private Connection persistentConn = null;

	public TransRunner(DataSource ds, RowMapper rowMapper)
	{
		this.ds = ds;
		this.rowMapper = rowMapper;
		this.doLogging = false;
	}

	public TransRunner(DataSource ds, RowMapper rowMapper, boolean isLogged)
	{
		this.ds = ds;
		this.rowMapper = rowMapper;
		this.doLogging = isLogged;
	}
	
	// start of a transaction
	// We want to keep track of which session is being used.
	// This allows, among other things, maintain an open transaction
	// accross multiple queries/updates.
	// CAREFUL: You need to manually commit the transaction, otherwise
	// it won't happen.
	public void begin() throws SQLException
	{
		preQueryCheck();
		this.persistentConn = this.ds.getConnection();
		this.persistentConn.setAutoCommit(false);
	}

	public void commit() throws SQLException
	{
		preQueryCheck();
		persistentConn.commit();
		DbUtil.close(persistentConn);
		this.persistentConn = null;
	}

	public void abort() throws SQLException
	{
		preQueryCheck();
		try
		{
			this.persistentConn.rollback();
		} 
        catch (SQLException e)
		{
            
		} 
        finally
		{
			DbUtil.close(persistentConn);
			persistentConn = null;
		}
	}

	public int queryForInt(String sql) throws SQLException
	{
		preQueryCheck();
		doLogging(sql);
		Connection conn = (this.persistentConn == null ? this.ds.getConnection() : this.persistentConn);
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		int result = 0;
		try
		{
			rs = stmt.executeQuery(sql);
			rs.next();
			result = rs.getInt(1);
		} 
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DbUtil.close(rs);
			DbUtil.close(stmt);
	         if (this.persistentConn == null)
	         {
	            DbUtil.close(conn);
	         }
		}
		return result;
	}

	// return null if there is no result
	public Map queryForMap(String sql) throws SQLException
	{
		List<Map> result = query(sql);
		return result.size() == 0 ? null : result.get(0);
	}
	
	// return an empty list if there is no result
	public List<Map> query(String sql) throws SQLException
	{
		preQueryCheck();
		doLogging(sql);
		Connection conn = (this.persistentConn == null ? this.ds.getConnection() : this.persistentConn);
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		List<Map> result = new ArrayList<Map>();
		try
		{
			rs = stmt.executeQuery(sql);
			int rowNum = 0;
			while (rs.next())
			{
				result.add(rowMapper.mapRow(rs, rowNum++));
			}
		} 
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DbUtil.close(rs);
			DbUtil.close(stmt);
	         if (this.persistentConn == null)
	         {
	            DbUtil.close(conn);
	         }
		}
		return result;
	}

	public int update(String sql) throws SQLException
	{
		preQueryCheck();
		doLogging(sql);
		Connection conn = (this.persistentConn == null ? this.ds.getConnection() : this.persistentConn);
		Statement stmt = conn.createStatement();
		int rows = 0;
		try
		{
			rows = stmt.executeUpdate(sql);
		} 
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DbUtil.close(stmt);
	         if (this.persistentConn == null)
	         {
	            DbUtil.close(conn);
	         }
		}
		return rows;
	}
    
	// ========================= private methods ========================//

	private void doLogging(String sql)
	{
		if (this.doLogging)
		{
			if(!sql.trim().startsWith("select"))Logger.info(sql);
		}
	}
	
	private void preQueryCheck() throws SQLException
	{
		if (this.ds == null)
		{
			throw new SQLException("datasource is null");
		}
		if (this.rowMapper == null)
		{
			throw new SQLException("row mapper is null");
		}
	}
	
	public int updateBlob(String sql, byte[]... bytesArray) throws SQLException, IOException
	{
		preQueryCheck();
		doLogging(sql);
		Connection conn = (this.persistentConn == null ? this.ds.getConnection() : this.persistentConn);
		PreparedStatement ps = conn.prepareStatement(sql);
		int rows = 0;
		try
		{
			int i = 1;
			for (byte[] bytes : bytesArray)
			{
				ps.setBytes(i++, bytes);
			}
			rows = ps.executeUpdate();
		} 
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DbUtil.close(ps);
	         if (this.persistentConn == null)
	         {
	            DbUtil.close(conn);
	         }
		}
		return rows;
	}
	
//	create table test (
//		    ID integer not null auto_increment,
//		    obj blob,
//		    primary key (ID)
//		) type=myisam;
//	public static void main(String[] args) throws Exception 
//	{
//        TransRunner runner = new TransRunner(DataSourceFactory.getDataSource("mdt","ChinacaT"), new MDTMySQLRowMapper());
//
//        // insert
//    	byte[] bytes = "hello world".getBytes("UTF-8");
//        runner.updateBlob("insert into test(obj) values (?)", bytes);
//
//        // select
//    	String sql = "select * from test where id = 1";
//        Map map = runner.queryForMap(sql);
//        byte[] resultBytes = (byte[]) map.get("obj");
//        System.out.println(new String(resultBytes, "UTF-8"));
//	}
}
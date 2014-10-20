package ruking.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class QueryRunner
{
	private DataSource ds = null;

	private boolean doLogging;
	
	private RowMapper rowMapper = null;

	public QueryRunner(DataSource ds, RowMapper rowMapper)
	{
		this.ds = ds;
		this.rowMapper = rowMapper;
		this.doLogging = true;
	}

	public QueryRunner(DataSource ds, RowMapper rowMapper, boolean isLogged)
	{
		this.ds = ds;
		this.rowMapper = rowMapper;
		this.doLogging = isLogged;
	}

	public int queryForInt(String sql) throws SQLException
	{
		preQueryCheck();
		doLogging(sql);
		Connection conn = this.ds.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try
		{
			stmt = conn.createStatement();
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
			DbUtil.close(conn);
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
        Connection conn = this.ds.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Map> result = new ArrayList<Map>();
		try
		{
			stmt = conn.createStatement();
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
			DbUtil.close(conn);
		}
		return result;
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
}
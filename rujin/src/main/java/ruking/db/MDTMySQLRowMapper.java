package ruking.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;

public class MDTMySQLRowMapper implements RowMapper
{
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		ResultSetMetaData meta = rs.getMetaData();
		int numCols = meta.getColumnCount();
		String[] fieldNames = new String[numCols];
		int[] fieldSqlTypes = new int[numCols];
		for (int i = 0; i < numCols; i++)
		{
			fieldNames[i] = meta.getColumnName(i + 1);
			fieldSqlTypes[i] = meta.getColumnType(i + 1);
		}

		Map<String, Object> dataHash = new HashMap<String, Object>();
		for (int i = 0; i < fieldNames.length; i++)
		{
			Object value = mapValueBySqlType(rs, fieldNames[i], fieldSqlTypes[i]);
			dataHash.put(fieldNames[i], value);
		}

		return dataHash;
	}

	private Object mapValueBySqlType(ResultSet rs, String fieldName, int sqlType) throws SQLException
	{
		Object value = null;
		if (Types.BINARY == sqlType || Types.VARBINARY == sqlType || Types.LONGVARBINARY == sqlType || Types.BLOB == sqlType)
		{
			byte[] byteArr = rs.getBytes(fieldName);
			return byteArr;
		}
		else if (Types.CHAR == sqlType || Types.VARCHAR == sqlType || Types.LONGVARCHAR == sqlType)
		{
			byte[] byteArr = rs.getBytes(fieldName);
			if (rs.wasNull())
			{
				return null;
			}
			else
			{
				try
				{
					return new String(byteArr, "UTF-8");
				}
				catch (Exception e)
				{
					throw new SQLException(e.getMessage());
				}
			}
		}
		else if (Types.DATE == sqlType)
		{
			value = rs.getDate(fieldName);
			return rs.wasNull() ? null : value; 
		}
		else if (Types.TIME == sqlType)
		{
			value = rs.getTime(fieldName);
			return rs.wasNull() ? null : value;
		}
		else if (Types.TIMESTAMP == sqlType)
		{
			Timestamp v = rs.getTimestamp(fieldName);
			return rs.wasNull() ? null : new SqlTimestamp(v.getTime());
		}
		else if (Types.BIGINT == sqlType)
		{
			long longValue = rs.getLong(fieldName);
			return rs.wasNull() ? null : new Long(longValue);
		}
		else if (Types.INTEGER == sqlType || Types.SMALLINT == sqlType || Types.TINYINT == sqlType)
		{
			int intValue = rs.getInt(fieldName);
			return rs.wasNull() ? null : new Integer(intValue);
		}
		else if (Types.REAL == sqlType || Types.DOUBLE == sqlType || Types.FLOAT == sqlType || Types.NUMERIC == sqlType || Types.DECIMAL == sqlType)
		{
			double doubleValue = rs.getDouble(fieldName);
			return rs.wasNull() ? null : new Double(doubleValue);
		}
		else if (Types.BOOLEAN == sqlType || Types.BIT == sqlType)
		{
			boolean b = rs.getBoolean(fieldName);
			return rs.wasNull() ? null : new Boolean(b);
		}
		else if (Types.LONGVARBINARY == sqlType)
		{
			InputStream b = rs.getBinaryStream(fieldName);
			return b;
		}
		else
		{
			throw new SQLException("In " + this.getClass().getName() + ":fieldName: " + fieldName + " Unsupported type: " + sqlType);
		}
	}
}

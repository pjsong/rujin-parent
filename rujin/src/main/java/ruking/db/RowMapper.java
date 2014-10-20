package ruking.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface RowMapper 
{
	/** 
	 * Implementations must implement this method to map each row of data
	 * in the ResultSet. This method should not call next() on the ResultSet,
	 * but extract the current values. 
	 * @param rs the ResultSet to map
	 * @param rowNum The number of the current row
	 * @throws SQLException if a SQLException is encountered getting
	 * column values (that is, there's no need to catch SQLException)
	 */
	Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException;
}

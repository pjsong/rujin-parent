package ruking.db;

import java.sql.Timestamp;
import java.util.Date;

/* Jian: this class is created to enable the XMLEncoder to save the timestamp objects.
 * the XMLEncoder requires that the objects have default public constructors, 
 * in case of java.sql.Timestamp, there is no default constructor. So, it bombed when
 * trying to do XMLEncoder.writeObject(). So, we created this class to get around the problem.
 */
public class SqlTimestamp extends Timestamp 
{
	public SqlTimestamp()
	{
		super(new Date().getTime());
	}

	public SqlTimestamp(long time)
	{
		super(time);
	}
	
	public void setTime(long time)
	{
		super.setTime(time);
	}
	
	public long getTime()
	{
		return super.getTime();
	}
}

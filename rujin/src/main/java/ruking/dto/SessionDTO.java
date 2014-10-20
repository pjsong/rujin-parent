package ruking.dto;

import java.util.Date;
import java.util.Map;

public class SessionDTO
{
	private String sessId;
	private Date lastUpdated;
	private Map<String, Object> sessData;
	
	public SessionDTO(String sessId, Date lastUpdated, Map<String, Object> sessData)
	{
		this.sessId = sessId;
		this.lastUpdated = lastUpdated;
		this.sessData = sessData;
	}
	
	public String getSessId()
	{
		return sessId;
	}
	
	public Date getLastUpdated()
	{
		return lastUpdated;
	}
	
	public Map<String, Object> getSessData()
	{
		return sessData;
	}
}

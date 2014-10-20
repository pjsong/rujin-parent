package ruking.utils;

import java.util.HashMap;

public class EmailParam
{
	private HashMap<String, String> to = new HashMap<String, String>(1);
	private HashMap<String, String> replyTo = new HashMap<String, String>(1);
	private HashMap<String, String> cc = new HashMap<String, String>(1);
	private HashMap<String, String> bcc = new HashMap<String, String>(1);
	private String fromEmail = "";
	private String from = "";
	private String subject = "";
	private String html = "";
	private String text = "";
	
	public EmailParam()
	{
		
	}
	
	public HashMap<String, String> getTo()
	{
		return to;
	}
	
	public void setTo(HashMap<String, String> to)
	{
		this.to = to;
	}

	public HashMap<String, String> getReplyTo()
	{
		return replyTo;
	}
	
	public void setReplyTo(HashMap<String, String> replyTo)
	{
		this.replyTo = replyTo;
	}

	public HashMap<String, String> getCc()
	{
		return cc;
	}
	
	public void setCc(HashMap<String, String> cc)
	{
		this.cc = cc;
	}

	public HashMap<String, String> getBcc()
	{
		return bcc;
	}
	
	public void setBcc(HashMap<String, String> bcc)
	{
		this.bcc = bcc;
	}

	public String getFromEmail()
	{
		return fromEmail;
	}
	
	public void setFromEmail(String fromEmail)
	{
		this.fromEmail = fromEmail;
	}

	public String getFrom()
	{
		return from;
	}
	
	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getSubject()
	{
		return subject;
	}
	
	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getHtml()
	{
		return html;
	}
	
	public void setHtml(String html)
	{
		this.html = html;
	}

	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
}

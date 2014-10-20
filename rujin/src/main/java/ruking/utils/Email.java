package ruking.utils;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class Email
{
	public static void send(EmailParam param) throws SQLException, EmailException, IOException
	{
		HtmlEmail email = new HtmlEmail();
		// email account setting
		email.setCharset("UTF-8");
		Conf conf=new Conf();
		email.setHostName(conf.getSmtpHost());
		email.setAuthentication(conf.getSmtpUser(), conf.getSmtpPassword());
		email.setDebug(true);

		// email content
		for (String key : param.getTo().keySet())
		{
			email.addTo(key, param.getTo().get(key));
		}

		for (String key : param.getReplyTo().keySet())
		{
			email.addReplyTo(key, param.getReplyTo().get(key));
		}
		
		for (String key : param.getCc().keySet())
		{
			email.addCc(key, param.getCc().get(key));
		}

		for (String key : param.getBcc().keySet())
		{
			email.addBcc(key, param.getBcc().get(key));
		}

		if (param.getFromEmail() != null && !param.getFromEmail().equals(""))
		{
			email.setFrom(param.getFromEmail(), param.getFrom());
		}
		
		email.setSubject(param.getSubject());
		
		if (param.getHtml() != null && !param.getHtml().equals(""))
		{
			email.setHtmlMsg(param.getHtml());
		}

		if (param.getText() != null && !param.getText().equals(""))
		{
			email.setTextMsg(param.getText());
		}
		email.setFrom(param.getFrom());
		// send the email
		email.send();
	}
}
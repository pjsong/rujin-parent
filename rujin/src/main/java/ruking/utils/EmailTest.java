package ruking.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.mail.EmailException;

public class EmailTest {
	public static void main(String args[]) throws SQLException, EmailException, IOException{
		EmailParam ep = new EmailParam();
		ep.setHtml("<p style='color:red'>xxx</p>");
		ep.setSubject("test Email");
		ep.setFrom("postmaster@ruking-cn.com");
		HashMap hmTo = new HashMap<String,String>();
		hmTo.put("pjsong3101@163.com","jeson");
		ep.setTo(hmTo);
		Email.send(ep);
	}
}

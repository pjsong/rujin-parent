package ruking.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SessionLogger
{
	protected static SimpleDateFormat fileNameFormatter = new SimpleDateFormat("yyyy_MM_dd");
	private static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyMMddHHmmss");
	private static String logDir = "sessionlog";

	static
	{
	    new File(logDir).mkdir();
	}

	public static synchronized void log(String sessionid, Date timestamp, String url, String ip, String referralUrl)
	{
		String logFile = String.format("%s.log", fileNameFormatter.format(new Date()));
	    try
	    {
	        PrintWriter out = new PrintWriter(new FileWriter(new File(logDir, logFile), true));
	        String tmpl = "%s\t%s\t%s\t%s\t%s";
	        String line = String.format(tmpl, sessionid, dateTimeFormatter.format(timestamp), url, ip, referralUrl);
	        out.println(line);
	        out.flush();
	        out.close();
	    }
	    catch (IOException e)
	    {
	        System.out.println("I am in the Logger and we are in trouble " + e);
	    }
	}   
}

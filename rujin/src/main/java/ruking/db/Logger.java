package ruking.db;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger
{
   protected static SimpleDateFormat fileNameFormatter = new SimpleDateFormat("yyyy_MM_dd");
   protected static SimpleDateFormat entryDateFormatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
   private static String logDir = "log";
  
   private static boolean stdout = true;
   
   static
   {
	   new File(logDir).mkdir();
   }
   
   public static void debug(Object message)
   {	  
      log("DEBUG", message);
   }

   public static void info(Object message)
   {
      log("INFO", message);
   }

   public static void warn(Object message)
   {
      log("WARN", message);
   }

   public static void error(Object message)
   {
      log("ERROR", message);
   }

   public static void fatal(Object message)
   {
      log("FATAL", message);
   }

   protected static synchronized void log(String prefix, Object message)
   {
	  String className = new Throwable().getStackTrace()[2].getClassName();
	  String logFile = String.format("%s.log", fileNameFormatter.format(new Date()));
      try
      {
         PrintWriter out = new PrintWriter(new FileWriter(new File(logDir, logFile), true));
         String tmpl = "[%s] %s:%s %s";
         String line = String.format(tmpl, entryDateFormatter.format(new Date()), prefix, className, message);
         if (stdout)
         {
        	 System.out.println(line);
        	 System.out.flush();
         }
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
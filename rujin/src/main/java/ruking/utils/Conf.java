package ruking.utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Conf {
	   private Properties prp = null;
	   private synchronized void checkAndLoadPrp()
	   {

			InputStream is;
			try {
				is = getClass().getResourceAsStream("/conf/dbconfig.propertie");
				prp = new Properties();
				prp.load(is);
			}catch(IOException e){}
			catch(Exception e){
	            is = getClass().getResourceAsStream(
                "dbconfig.propertie");
			}
	   }
	   public String getHostName() throws IOException{
		   if(prp==null)checkAndLoadPrp();
		   return prp.getProperty("host");
		   }
	   public String getDbName() throws IOException{
		   if(prp==null)checkAndLoadPrp();
		   return prp.getProperty("dbname");
		   }
	   public String getDbUser() throws IOException{
		   if(prp==null)checkAndLoadPrp();
		   return prp.getProperty("dbuser");
		   }
	   public String getDbPassword() throws IOException{
		   if(prp==null)checkAndLoadPrp();
		   return prp.getProperty("dbpassword");
		   }
	   public String getSmtpHost() throws IOException{
		   if(prp==null)checkAndLoadPrp();
		   return prp.getProperty("smtpHost");
		   }
	   public String getSmtpUser() throws IOException{
		   if(prp==null)checkAndLoadPrp();
		   return prp.getProperty("smtpUser");
		   }
	   public String getSmtpPassword() throws IOException{
		   if(prp==null)checkAndLoadPrp();
		   return prp.getProperty("smtpPassword");
		   }
}

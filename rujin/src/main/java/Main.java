//import java.io.File;
//
//import org.mortbay.jetty.Connector;
//import org.mortbay.jetty.NCSARequestLog;
//import org.mortbay.jetty.Server;
//import org.mortbay.jetty.handler.RequestLogHandler;
//import org.mortbay.jetty.nio.SelectChannelConnector;
//import org.mortbay.jetty.security.SslSocketConnector;
//import org.mortbay.jetty.webapp.WebAppContext;
////http://download.oracle.com/javaee/1.4/tutorial/doc/Security6.html
//public class Main
//{
//   public static void main(String[] args) throws Exception
//   {
//	   Server server = new Server();
//	  
//	   Connector connector = new SelectChannelConnector();
//	   connector.setPort(8080);
//	   server.addConnector(connector);
//
//	   SslSocketConnector sslConnector = new SslSocketConnector();
//	   sslConnector.setPort(8480);
//	   sslConnector.setKeystore("keystore.key");
//	   sslConnector.setKeyPassword("Your Password");
//	   server.addConnector(sslConnector);
//	   
//	   WebAppContext wac = new WebAppContext();
//	   wac.setContextPath("/");
//	   wac.setWar("./war");    // this is path to .war OR TO expanded, existing webapp; WILL FIND web.xml and parse it
//	   wac.setDefaultsDescriptor("./lib/webdefault.xml");
//	   server.addHandler(wac);
//
//	   // configure the request log
//	   new File("log").mkdir();
//	   RequestLogHandler requestLogHandler = new RequestLogHandler();
//	   NCSARequestLog reqLog = new NCSARequestLog("./log/yyyy_mm_dd.request.log");
//	   reqLog.setRetainDays(10);
//	   reqLog.setAppend(true);
//	   reqLog.setExtended(false);
//	   reqLog.setLogTimeZone("Canada/Pacific");
//	   requestLogHandler.setRequestLog(reqLog);
//	   server.addHandler(requestLogHandler);	   
//
//	   server.start();
//   }
//}
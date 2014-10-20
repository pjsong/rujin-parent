package ruking.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.springframework.web.servlet.DispatcherServlet;

import ruking.db.Logger;
import ruking.velocity.VelocityParserFactory;


public class TheDispatcherServlet extends DispatcherServlet
{
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		try
		{
			doIt(request, response);
		}
		catch (Exception e)
		{
			// silently ignore the tomcat ClientAbortException
			if (e.getClass().getName().equals("org.apache.catalina.connector.ClientAbortException"))
			{
				return;
			}			
			VelocityContext ctx = new VelocityContext();
			response.setStatus(500);
			ctx.put("stackTrace",e.getMessage());
			Logger.error(e.getMessage());
			VelocityParserFactory.getVP().render("err_500", ctx, request, response);
		}
    }
    
    private void doIt(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		// set the request/response encoding once and for all
		request.setCharacterEncoding("UTF-8");

//	     
//      // set the no cache control header, HTTP 1.1 standard
//      //resp.setHeader("Cache-Control", "no-cache");
//      // this is to allow user to go back to previous page after a post operation in IE
//      // this fixes the error:
//      //  Error Message: Warning: Page Has Expired: The Page You Requested...
//      response.setHeader("Cache-Control", "public");
//
//      // set the no cache header, HTTP 1.0 standard
//      response.setHeader("Pragma", "no-cache");
//
//      // for user-agents that ignore the above settings
//      response.setHeader("Expires", "Thu, 01 Jan 1970 00.00.00 GMT");

//		String redirectUrl = Redirect301.getRedirect(relativeUrl);
//		if(redirectUrl != null)
//		{
//			response.setStatus(301);
//			response.setHeader("Location", redirectUrl);
//			response.setHeader("Connection", "close");
//		}
//		else
//		{
			super.doService(request, response);
//		}
    }
}
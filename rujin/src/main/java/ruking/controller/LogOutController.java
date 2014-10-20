package ruking.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import ruking.ba.GlobalVariablesBA;
import ruking.db.DataSourceFactory;
import ruking.db.MDTMySQLRowMapper;
import ruking.session.SessionName;
import ruking.session.SessionUtil;

public class LogOutController extends BaseController {
	    public void process(HttpServletRequest request, HttpServletResponse response) throws Exception
	    {
	    	VelocityContext vc = new VelocityContext();
	    	new GlobalVariablesBA().setCommonVariables(request, vc);
	        // get session
	    	SessionUtil sessUtil = new SessionUtil(DataSourceFactory.getDataSource((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbName"),(String)vc.get("dbPWD")), new MDTMySQLRowMapper());
	    	Map<String, Object> sessData =(Map<String, Object>) request.getAttribute(SessionUtil.SESS_DATA);
	        sessData.remove(SessionName.customerDTO);
	        sessUtil.write(request, sessData);
	        
	        
	        // set the no cache control header, HTTP 1.1 standard
	        //resp.setHeader("Cache-Control", "no-cache");
	        // this is to allow user to go back to previous page after a post operation in IE
	        // this fixes the error:
	        //  Error Message: Warning: Page Has Expired: The Page You Requested...
	        response.setHeader("Cache-Control", "public");

	        // set the no cache header, HTTP 1.0 standard
	        response.setHeader("Pragma", "no-cache");

	        // for user-agents that ignore the above settings
	        response.setHeader("Expires", "Thu, 01 Jan 1970 00.00.00 GMT");

	        response.sendRedirect("/");
	    }
}

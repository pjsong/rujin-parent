package ruking.velocity;
//
//import java.io.File;
//import java.io.OutputStreamWriter;
//import java.io.StringWriter;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
//import org.apache.velocity.runtime.RuntimeConstants;


public class VelocityParser
{
	private VelocityEngine ve;
	
	public VelocityParser() {
		super();
        ve = new VelocityEngine();
		ve.setProperty("input.encoding", "utf-8");
		ve.setProperty("output.encoding", "utf-8");
		// setting up file resource loader
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, getClass().getResource("/template").getPath());

		// Jian: this is very important, otherwise, there will be out of memory error when a template
		// with #foreach loop is called and the #foreach loop iterates through million of times
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
		// ve.setProperty("file.resource.loader.modificationCheckInterval", "5");
	
		// velocimacro configuration
		ve.setProperty(RuntimeConstants.VM_LIBRARY,"spring.vm");
		
		// this setting is such that changing inline velocity macro will take effect
		// WITHOUT restarting the web server!
		ve.setProperty(RuntimeConstants.VM_PERM_ALLOW_INLINE_REPLACE_GLOBAL, "true");

		ve.setProperty(RuntimeConstants.VM_LIBRARY_AUTORELOAD, "true");

		ve.setProperty(RuntimeConstants.COUNTER_INITIAL_VALUE, "0");
		ve.setProperty(RuntimeConstants.PARSER_POOL_SIZE, "1");

		// comment this out if we want to enable logging
		ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogSystem");

		ve.setProperty(RuntimeConstants.RUNTIME_LOG_REFERENCE_LOG_INVALID, "true");

		ve.setProperty(RuntimeConstants.PARSE_DIRECTIVE_MAXDEPTH, "10");
//        ve.init(getClass().getResource("/ve.properties").getPath());
	}

	public void render(String viewName, Context ctx, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		response.setContentType("text/html; charset=utf-8");

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
//
//        response.getWriter().println(getClass().getResource("/template/index.vm").getPath());
         OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "utf-8");
         if(out==null || ctx==null || getClass().getResource("/template/index.vm").getPath()==null){
        	 PrintWriter pw = new PrintWriter(new FileOutputStream("xxx.txt"));
        	 pw.println("out is null");
        	 pw.close();
        	 return;
         }
         ve.mergeTemplate(viewName+".vm", "utf-8", ctx, out);
		 out.flush();
	}

	public VelocityEngine getVe() {
		return ve;
	}

	public void setVe(VelocityEngine ve) {
		this.ve = ve;
	}	
	
}
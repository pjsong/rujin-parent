package ruking.controller.big;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import ruking.ba.GlobalVariablesBA;
import ruking.controller.BaseController;
import ruking.utils.Util;
import ruking.velocity.VelocityParserFactory;

public class AboutController extends BaseController{

	public void process(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
        VelocityParserFactory.getVP().render(urlTemplate(request,vc), vc, request, response);
		
	}
    private String urlTemplate(HttpServletRequest request, VelocityContext vc)
    {
		String relativeURL = request.getRequestURI();
    	String id =  Util.getNoNull(request.getParameter("id"));
    	vc.put("id", id);
        int startpos = relativeURL.lastIndexOf("about_big/")+10;
        return relativeURL.substring(startpos, relativeURL.length()-6);
    }
}

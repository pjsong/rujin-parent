package ruking.controller.manage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.velocity.VelocityContext;

import ruking.ba.GlobalVariablesBA;
import ruking.controller.BaseController;
import ruking.dao.AttributeDAO;
import ruking.utils.Util;
import ruking.velocity.VelocityParserFactory;

public class ListAttributesController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
    	AttributeDAO aDAO = new AttributeDAO();
		String act= Util.getNoNull(request.getParameter("act"));
		if(act.equals("del")){
			String id = Util.getNoNull(request.getParameter("id"));
			if(NumberUtils.isDigits(id) && aDAO.getAttributeByID(id,"")!=null){
				aDAO.deleteProduct(id);
			}
			response.sendRedirect("/listattributes.jhtml");
			return;
		}
   		vc.put("currentTab", "attribute");
   		List<Map> attributes = null;
   		String pid = Util.getNoNull(request.getParameter("pid"));
   		if(pid.equals("")){
   			attributes = aDAO.getAllAttributes("");
   		}else{
   			attributes = aDAO.getAttributesByProductId(pid,"");
   		}
   		vc.put("attributes", attributes);
   		VelocityParserFactory.getVP().render("listattributes", vc, request, response);
	}
}
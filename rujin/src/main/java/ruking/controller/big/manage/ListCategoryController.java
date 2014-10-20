package ruking.controller.big.manage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.velocity.VelocityContext;

import ruking.ba.GlobalVariablesBA;
import ruking.controller.BaseController;
import ruking.dao.CategoryDAO;
import ruking.utils.Util;
import ruking.velocity.VelocityParserFactory;

public class ListCategoryController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
    	CategoryDAO cDAO = new CategoryDAO();
		String act= Util.getNoNull(request.getParameter("act"));
		if(act.equals("del")){
			String id = Util.getNoNull(request.getParameter("cid"));
			if(NumberUtils.isDigits(id) && cDAO.getCategoryByID(id,"")!=null){
				cDAO.deleteCategory(id,"big");
			}
			response.sendRedirect("/listcategories_big.jhtml");
			return;
		}
   		vc.put("currentTab", "category_big");
   		List<Map> categories = cDAO.getAllCategories("big");
   		vc.put("categories", categories);
   		VelocityParserFactory.getVP().render("listcategories_big", vc, request, response);
	}
}

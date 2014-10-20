package ruking.controller.eng.manage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.velocity.VelocityContext;

import ruking.ba.GlobalVariablesBA;
import ruking.controller.BaseController;
import ruking.dao.ProductDAO;
import ruking.db.DataSourceFactory;
import ruking.db.MDTMySQLRowMapper;
import ruking.session.SessionUtil;
import ruking.utils.Util;
import ruking.velocity.VelocityParserFactory;

public class ListProductsController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
    	ProductDAO pDAO = new ProductDAO();
		String act= Util.getNoNull(request.getParameter("act"));
		if(act.equals("del")){
			String id = Util.getNoNull(request.getParameter("pid"));
			if(NumberUtils.isDigits(id) && pDAO.getProductByID(id,"eng")!=null){
				pDAO.deleteProduct(id,"eng");
			}
			response.sendRedirect("/listproducts_eng.jhtml");
			return;
		}
   		vc.put("currentTab", "product_eng");
   		List<Map> products = pDAO.getAllProducts("eng");
   		vc.put("products", products);
   		VelocityParserFactory.getVP().render("listproducts_eng", vc, request, response);
	}
}

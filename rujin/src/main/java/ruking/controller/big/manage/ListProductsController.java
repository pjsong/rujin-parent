package ruking.controller.big.manage;

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
   		vc.put("currentTab", "product_big");
        new GlobalVariablesBA().setCommonVariables(request, vc);
    	ProductDAO pDAO = new ProductDAO();
		String act= Util.getNoNull(request.getParameter("act"));
		if(act.equals("del")){
			String id = Util.getNoNull(request.getParameter("pid"));
			if(NumberUtils.isDigits(id) && pDAO.getProductByID(id,"big")!=null){
				pDAO.deleteProduct(id,"big");
			}
			response.sendRedirect("/listproducts_big.jhtml");
			return;
		}
   		List<Map> products = pDAO.getAllProducts("big");
   		vc.put("products", products);
   		VelocityParserFactory.getVP().render("listproducts_big", vc, request, response);
	}
}

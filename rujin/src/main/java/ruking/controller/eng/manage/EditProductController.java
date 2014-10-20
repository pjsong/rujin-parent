package ruking.controller.eng.manage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.web.bind.ServletRequestDataBinder;

import ruking.ba.GlobalVariablesBA;
import ruking.controller.BaseController;
import ruking.dao.ProductDAO;
import ruking.dto.ProductDTO;
import ruking.utils.Util;
import ruking.velocity.VelocityParserFactory;

public class EditProductController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String act= Util.getNoNull(request.getParameter("act"));
		VelocityContext vc=new VelocityContext();
    	ProductDAO pDAO = new ProductDAO();
    	vc.put("currentTab", "product_eng");
    	if(act.equals("")){
            new GlobalVariablesBA().setCommonVariables(request, vc);
        	vc.put("act", "add");
            VelocityParserFactory.getVP().render("editproduct_eng", vc, request, response);
            return;
        }
        if(act.equals("add")){
        	add(request,response);
        	return;
        }
        if(act.equals("edit")){
            new GlobalVariablesBA().setCommonVariables(request, vc);
        	vc.put("act", "update");
            String id= Util.getNoNull(request.getParameter("pid"));
        	vc.put("id", id);
	    	ProductDTO pDTO = pDAO.getProductByID(id,"eng");
        	vc.put("product", pDTO);
            VelocityParserFactory.getVP().render("editproduct_eng", vc, request, response);
            return;
        }
        if(act.equals("update")){
            update(request,response);
            return;
        }
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
    	ProductDTO product = new ProductDTO();  
		ServletRequestDataBinder binder = new ServletRequestDataBinder(product, "product");
		binder.bind(request);
    	ProductDAO pDAO = new ProductDAO();
		Map<String,String> error = check(product,pDAO);
		if(error.size()>0){
			vc.put("error", error);
			vc.put("act", "add");
			vc.put("product", product);
			VelocityParserFactory.getVP().render("editproduct_eng", vc, request, response);
			return;
		}else{
			product = pDAO.insertProduct(product,"eng");
	    	response.sendRedirect("/listproducts_eng.jhtml");
		}
	}
	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
        String id= Util.getNoNull(request.getParameter("pid"));
    	ProductDAO pDAO = new ProductDAO();
    	ProductDTO product = pDAO.getProductByID(id,"eng");
    	String oldName = product.getTitle();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(product, "product");
		binder.bind(request);
		Map<String,String> error = updateCheck(product,pDAO,oldName,id);
		if(error.size()>0){
			vc.put("error", error);
			vc.put("act", "update");
			vc.put("id", id);
			vc.put("product", product);
			VelocityParserFactory.getVP().render("editproduct_eng", vc, request, response);
			return;
		}else{
	    	pDAO.updateProduct(product,id,"eng");
	    	response.sendRedirect("/listproducts_eng.jhtml");
		}
	}
	
	private Map<String,String> check(ProductDTO p,ProductDAO pDAO) throws SQLException{
		Map<String,String> error = new HashMap<String,String>();
		ProductDTO pDTO = pDAO.getProductByID(p.getId(), "eng");
		if(pDTO!=null)error.put("idError", "id 已存在");
		if(!NumberUtils.isDigits(p.getId()))error.put("idValueError", "ID必须为数字");
		if(Util.getNoNull(p.getTitle()).length()<1)error.put("titleEmptyError", "输入产品名称");
		if(pDAO.productTitleExits(p.getTitle(),"eng"))error.put("titleValueError", "产品名称已存在");
		if(p.getTitle().length()>98)error.put("titleLengthError", "产品名称太长");
		return error;
	}
	private Map<String,String> updateCheck(ProductDTO p,ProductDAO pDAO,String oldName,String oldId) throws SQLException{
		Map<String,String> error = new HashMap<String,String>();
		if(!p.getId().equals(oldId))
		{
			ProductDTO pDTO = pDAO.getProductByID(p.getId(), "eng");
			if(pDTO!=null)error.put("idError", "id 已存在");
		}
		if(Util.getNoNull(p.getTitle()).length()<1)error.put("titleEmptyError", "输入产品名称");
		if(!p.getTitle().equals(oldName) && pDAO.productTitleExits(p.getTitle(),"eng"))error.put("titleValueError", "产品名称已存在");
		if(p.getTitle().length()>98)error.put("titleLengthError", "产品名称太长");
		if("".equals(p.getCatID()))error.put("catIDEmptyError", "输入类别ID");
		if(!Pattern.matches("(,[\\d]+){1,},", p.getCatID()))error.put("catIDFormatError", "类别ID格式必须为\",数字1,数字2,...\"");
		return error;
	}
}
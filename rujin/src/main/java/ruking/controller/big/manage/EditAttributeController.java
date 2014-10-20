package ruking.controller.big.manage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.web.bind.ServletRequestDataBinder;

import ruking.ba.GlobalVariablesBA;
import ruking.controller.BaseController;
import ruking.dao.AttributeDAO;
import ruking.dao.ProductDAO;
import ruking.dto.AttributeDTO;
import ruking.utils.Util;
import ruking.velocity.VelocityParserFactory;

public class EditAttributeController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String act= Util.getNoNull(request.getParameter("act"));
		VelocityContext vc=new VelocityContext();
   		vc.put("currentTab", "attribute_big");
   		AttributeDAO pDAO = new AttributeDAO();
       if(act.equals("")){
            new GlobalVariablesBA().setCommonVariables(request, vc);
        	vc.put("act", "add");
            VelocityParserFactory.getVP().render("editattribute_big", vc, request, response);
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
	    	AttributeDTO attribute = pDAO.getAttributeByID(id,"big");
        	vc.put("attribute", attribute);
            VelocityParserFactory.getVP().render("editattribute_big", vc, request, response);
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
    	AttributeDTO attribute = new AttributeDTO();  
		ServletRequestDataBinder binder = new ServletRequestDataBinder(attribute, "attribute");
		binder.bind(request);
    	AttributeDAO aDAO = new AttributeDAO();
    	ProductDAO productDAO = new ProductDAO();
		Map<String,String> error = check(attribute,aDAO,productDAO);
		if(error.size()>0){
			vc.put("error", error);
			vc.put("act", "add");
			vc.put("attribute", attribute);
			VelocityParserFactory.getVP().render("editattribute_big", vc, request, response);
			return;
		}else{
			attribute = aDAO.insertProduct(attribute,"big");
	    	response.sendRedirect("/listattributes_big.jhtml");
		}
	}
	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
        String id= Util.getNoNull(request.getParameter("pid"));
    	AttributeDAO aDAO = new AttributeDAO();
    	ProductDAO productDAO = new ProductDAO();
    	AttributeDTO attribute = aDAO.getAttributeByID(id,"big");
		ServletRequestDataBinder binder = new ServletRequestDataBinder(attribute, "product");
		binder.bind(request);
		Map<String,String> error = check(attribute,aDAO,productDAO);
		if(error.size()>0){
			vc.put("error", error);
			vc.put("act", "update");
			vc.put("attribute", attribute);
			VelocityParserFactory.getVP().render("editattribute_big", vc, request, response);
			return;
		}else{
	    	aDAO.updateAttribute_big(attribute);
	    	response.sendRedirect("/listattributes_big.jhtml");
		}
	}
	
	private Map<String,String> check(AttributeDTO p,AttributeDAO aDAO,ProductDAO productDAO) throws SQLException{
		Map<String,String> error = new HashMap<String,String>();
		if(Util.getNoNull(p.getProductId()).length()<1)error.put("productIdEmptyError", "輸入產品名稱");
		if(!NumberUtils.isDigits(Util.getNoNull(p.getProductId())))error.put("productIdFormatError", "產品ID只能是數字");
		if(productDAO.getProductByID(p.getProductId(),"big")==null)error.put("productIdNotExistsError", "產品ID不存在");
		if(Util.getNoNull(p.getAttrName()).length()<1)error.put("attrNameEmptyError", "輸入屬性名稱");
		if(p.getAttrValue().length()<1)error.put("attrValueEmptyError", "輸入屬性值");
		if(p.getDisplayOrder().length()<1)error.put("attrDisplayOrderError", "輸入顯示序值");
		if(!NumberUtils.isDigits(Util.getNoNull(p.getDisplayOrder())))error.put("attrDisplayOrderError", "顯示序值只能是數字");
		return error;
	}

}
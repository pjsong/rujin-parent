package ruking.controller.eng.manage;

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
   		vc.put("currentTab", "attribute_eng");
    	AttributeDAO pDAO = new AttributeDAO();
       if(act.equals("")){
            new GlobalVariablesBA().setCommonVariables(request, vc);
        	vc.put("act", "add");
            VelocityParserFactory.getVP().render("editattribute_eng", vc, request, response);
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
	    	AttributeDTO attribute = pDAO.getAttributeByID(id,"eng");
        	vc.put("attribute", attribute);
            VelocityParserFactory.getVP().render("editattribute_eng", vc, request, response);
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
			VelocityParserFactory.getVP().render("editattribute_eng", vc, request, response);
			return;
		}else{
			attribute = aDAO.insertProduct(attribute,"eng");
	    	response.sendRedirect("/listattributes_eng.jhtml");
		}
	}
	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
        String id= Util.getNoNull(request.getParameter("pid"));
    	AttributeDAO aDAO = new AttributeDAO();
    	ProductDAO productDAO = new ProductDAO();
    	AttributeDTO attribute = aDAO.getAttributeByID(id,"eng");
		ServletRequestDataBinder binder = new ServletRequestDataBinder(attribute, "product");
		binder.bind(request);
		Map<String,String> error = check(attribute,aDAO,productDAO);
		if(error.size()>0){
			vc.put("error", error);
			vc.put("act", "update");
			vc.put("attribute", attribute);
			VelocityParserFactory.getVP().render("editattribute_eng", vc, request, response);
			return;
		}else{
	    	aDAO.updateAttribute_eng(attribute);
	    	response.sendRedirect("/listattributes_eng.jhtml");
		}
	}
	
	private Map<String,String> check(AttributeDTO p,AttributeDAO aDAO,ProductDAO productDAO) throws SQLException{
		Map<String,String> error = new HashMap<String,String>();
		if(Util.getNoNull(p.getProductId()).length()<1)error.put("productIdEmptyError", "输入产品名称");
		if(!NumberUtils.isDigits(Util.getNoNull(p.getProductId())))error.put("productIdFormatError", "产品ID只能是数字");
		if(productDAO.getProductByID(p.getProductId(),"eng")==null)error.put("productIdNotExistsError", "产品ID不存在");
		if(Util.getNoNull(p.getAttrName()).length()<1)error.put("attrNameEmptyError", "输入属性名称");
		if(p.getAttrValue().length()<1)error.put("attrValueEmptyError", "输入属性值");
		if(p.getDisplayOrder().length()<1)error.put("attrDisplayOrderError", "输入属性值");
		if(!NumberUtils.isDigits(Util.getNoNull(p.getDisplayOrder())))error.put("attrDisplayOrderError", "显示序值只能是数字");
		return error;
	}

}
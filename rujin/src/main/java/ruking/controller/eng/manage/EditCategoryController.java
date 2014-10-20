package ruking.controller.eng.manage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.springframework.web.bind.ServletRequestDataBinder;

import ruking.ba.GlobalVariablesBA;
import ruking.controller.BaseController;
import ruking.dao.CategoryDAO;
import ruking.dto.CategoryDTO;
import ruking.utils.Util;
import ruking.velocity.VelocityParserFactory;

public class EditCategoryController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String act= Util.getNoNull(request.getParameter("act"));
		VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
   		vc.put("currentTab", "category_eng");
    	CategoryDAO pDAO = new CategoryDAO();
       if(act.equals("")){
        	vc.put("act", "add");
            VelocityParserFactory.getVP().render("editcategory_eng", vc, request, response);
            return;
        }
        if(act.equals("add")){
        	add(request,response);
        	return;
        }
        if(act.equals("edit")){
            new GlobalVariablesBA().setCommonVariables(request, vc);
        	vc.put("act", "update");
            String id= Util.getNoNull(request.getParameter("cid"));
	    	CategoryDTO pDTO = pDAO.getCategoryByID(id,"eng");
        	vc.put("category", pDTO);
            VelocityParserFactory.getVP().render("editcategory_eng", vc, request, response);
            return;
        }
        if(act.equals("update")){
            update(request,response);
            return;
        }
        if(act.equals("del")){
            delete(request,response);
            return;
        }
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
   		CategoryDTO category = new CategoryDTO();  
		ServletRequestDataBinder binder = new ServletRequestDataBinder(category, "category");
		binder.bind(request);
		CategoryDAO cDAO = new CategoryDAO();
		Map<String,String> error = check(category,cDAO);
		if(error.size()>0){
			vc.put("error", error);
			vc.put("act", "add");
			vc.put("category", category);
			VelocityParserFactory.getVP().render("editcategory_eng", vc, request, response);
			return;
		}else{
			category = cDAO.insertCategory(category,"eng");
	    	response.sendRedirect("/listcategories_eng.jhtml");
		}
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
        String id= Util.getNoNull(request.getParameter("cid"));
    	CategoryDAO pDAO = new CategoryDAO();
    	pDAO.deleteCategory(id,"eng");
    	response.sendRedirect("/listcategories_eng.jhtml");
	}
	
	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
        String id= Util.getNoNull(request.getParameter("cid"));
    	CategoryDAO pDAO = new CategoryDAO();
    	CategoryDTO category = pDAO.getCategoryByID(id,"eng");
		ServletRequestDataBinder binder = new ServletRequestDataBinder(category, "category");
		binder.bind(request);
		Map<String,String> error = updateCheck(category,pDAO,id);
		if(error.size()>0){
			vc.put("error", error);
			vc.put("act", "update");
			vc.put("category", category);
			VelocityParserFactory.getVP().render("editcategory_eng", vc, request, response);
			return;
		}else{
			pDAO.updateCategory(category,id,"eng");
	    	response.sendRedirect("/listcategories_eng.jhtml");
		}
	}
	
	private Map<String,String> check(CategoryDTO p,CategoryDAO pDAO) throws SQLException{
		Map<String,String> error = new HashMap<String,String>();
		if(Util.getNoNull(p.getCategory()).length()<1)error.put("categoryLengthError", "输入类别名称");
		if(p.getCategory().length()>98)error.put("categoryLengthError", "类别太长");
		if(p.getSubcategory().length()>98)error.put("subcategoryLengthError", "子类太长");
		return error;
	}
	private Map<String,String> updateCheck(CategoryDTO p,CategoryDAO pDAO,String oldId) throws SQLException{
		Map<String,String> error = new HashMap<String,String>();
		if(!p.getId().equals(oldId))
		{
			CategoryDTO cDTO = pDAO.getCategoryByID(p.getId(), "eng");
			if(cDTO!=null)error.put("idError", "id 错误");
		}
		if(Util.getNoNull(p.getCategory()).length()<1)error.put("categoryLengthError", "输入类别名称");
		if(p.getCategory().length()>98)error.put("categoryLengthError", "类别太长");
		if(p.getSubcategory().length()>98)error.put("subcategoryLengthError", "子类太长");
		return error;
	}
}
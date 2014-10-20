//package ruking.controller.manage;
//
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.velocity.VelocityContext;
//import org.springframework.web.bind.ServletRequestDataBinder;
//
//import ruking.ba.GlobalVariablesBA;
//import ruking.controller.BaseController;
//import ruking.dao.GlobalCatDAO;
//import ruking.dao.ProductDAO;
//import ruking.dto.GlobalCatDTO;
//import ruking.utils.Util;
//import ruking.velocity.VelocityParserFactory;
//
//public class EditGlobalCatController extends BaseController {
//	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        String act= Util.getNoNull(request.getParameter("act"));
//		VelocityContext vc=new VelocityContext();
//    	GlobalCatDAO pDAO = new GlobalCatDAO((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbUser"),(String)vc.get("dbPWD"));
//       if(act.equals("")){
//            new GlobalVariablesBA().setCommonVariables(request, vc);
//        	vc.put("act", "add");
//            VelocityParserFactory.getVP().render("editglobalcat", vc, request, response);
//            return;
//        }
//        if(act.equals("add")){
//        	add(request,response);
//        	return;
//        }
//        if(act.equals("edit")){
//            new GlobalVariablesBA().setCommonVariables(request, vc);
//        	vc.put("act", "update");
//            String id= Util.getNoNull(request.getParameter("id"));
//	    	GlobalCatDTO globalcat = pDAO.getGlobalByID(id);
//        	vc.put("globalcat", globalcat);
//            VelocityParserFactory.getVP().render("editglobalcat", vc, request, response);
//            return;
//        }
//        if(act.equals("update")){
//            update(request,response);
//            return;
//        }
//	}
//	
//	private void add(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		VelocityContext vc=new VelocityContext();
//        new GlobalVariablesBA().setCommonVariables(request, vc);
//    	GlobalCatDTO globalcat = new GlobalCatDTO();  
//		ServletRequestDataBinder binder = new ServletRequestDataBinder(globalcat, "attribute");
//		binder.bind(request);
//    	GlobalCatDAO aDAO = new GlobalCatDAO((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbUser"),(String)vc.get("dbPWD"));
//    	ProductDAO productDAO = new ProductDAO((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbUser"),(String)vc.get("dbPWD"));
//		Map<String,String> error = check(globalcat,aDAO,productDAO);
//		if(error.size()>0){
//			vc.put("error", error);
//			vc.put("act", "add");
//			vc.put("globalcat", globalcat);
//			VelocityParserFactory.getVP().render("editglobalcat", vc, request, response);
//			return;
//		}else{
//			globalcat = aDAO.insertGlobalCat(globalcat);
//	    	response.sendRedirect("/listglobalcats.jhtml");
//		}
//	}
//	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		VelocityContext vc=new VelocityContext();
//        new GlobalVariablesBA().setCommonVariables(request, vc);
//        String id= Util.getNoNull(request.getParameter("id"));
//    	GlobalCatDAO aDAO = new GlobalCatDAO((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbUser"),(String)vc.get("dbPWD"));
//    	ProductDAO productDAO = new ProductDAO((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbUser"),(String)vc.get("dbPWD"));
//    	GlobalCatDTO globalcat = aDAO.getGlobalByID(id);
//		ServletRequestDataBinder binder = new ServletRequestDataBinder(globalcat, "product");
//		binder.bind(request);
//		Map<String,String> error = check(globalcat,aDAO,productDAO);
//		if(error.size()>0){
//			vc.put("error", error);
//			vc.put("act", "update");
//			vc.put("globalcat", globalcat);
//			VelocityParserFactory.getVP().render("editglobalcat", vc, request, response);
//			return;
//		}else{
//	    	aDAO.updateGlobal(globalcat);
//	    	response.sendRedirect("/listglobalcats.jhtml");
//		}
//	}
//	
//	private Map<String,String> check(GlobalCatDTO p,GlobalCatDAO aDAO,ProductDAO paDAO) throws SQLException{
//		Map<String,String> error = new HashMap<String,String>();
//		if(Util.getNoNull(p.getCatName()).length()<1)error.put("catNameEmptyError", "输入全局分类名称");
//		String productIDs = Util.getNoNull(p.getProductIDs());
//		if(productIDs.length()<1){
//			error.put("productIDsEmptyError", "输入产品ID");
//			return error;
//		}
//		if(productIDs.split(",").length<2)error.put("productIDsFormatError", "产品ID少于2个,或者逗号为全角,请输入半角','");
//		else{
//			String[] pids = productIDs.split(",");
//			for(String id:pids){
//				if(paDAO.getProductByID(id,"")==null){
//					error.put("productIDNotExistsEmptyError", "产品ID:"+id+"不存在");
//				}
//			}
//		}
//		return error;
//	}
//
//}
//package ruking.controller.manage;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.math.NumberUtils;
//import org.apache.velocity.VelocityContext;
//
//import ruking.ba.GlobalVariablesBA;
//import ruking.controller.BaseController;
//import ruking.dao.GlobalCatDAO;
//import ruking.utils.Util;
//import ruking.velocity.VelocityParserFactory;
//
//public class ListGlobalCatsController extends BaseController {
//	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		VelocityContext vc=new VelocityContext();
//        new GlobalVariablesBA().setCommonVariables(request, vc);
//    	GlobalCatDAO aDAO = new GlobalCatDAO((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbUser"),(String)vc.get("dbPWD"));
//		String act= Util.getNoNull(request.getParameter("act"));
//		if(act.equals("del")){
//			String id = Util.getNoNull(request.getParameter("id"));
//			if(NumberUtils.isDigits(id) && aDAO.getGlobalByID(id)!=null){
//				aDAO.deleteGlobal(id);
//			}
//			response.sendRedirect("/listglobalcates.jhtml");
//			return;
//		}
//   		vc.put("currentTab", "globalcat");
//   		List<Map> globalcats = null;
//   		String pid = Util.getNoNull(request.getParameter("pid"));
//   		if(pid.equals("")){
//   			globalcats = aDAO.getAllGlobals();
//   		}else{
//   			globalcats = aDAO.getGlobalsById(pid);
//   		}
//   		vc.put("globalcats", globalcats);
//   		VelocityParserFactory.getVP().render("listglobalcats", vc, request, response);
//	}
//}
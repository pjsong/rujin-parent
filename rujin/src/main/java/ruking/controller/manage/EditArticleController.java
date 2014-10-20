//package ruking.controller.manage;
//
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
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
//import ruking.dao.ArticleDAO;
//import ruking.dto.ArticleDTO;
//import ruking.dto.UserSignUpDTO;
//import ruking.utils.Util;
//import ruking.velocity.VelocityParserFactory;
//
//public class EditArticleController extends BaseController {
//	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        String act= Util.getNoNull(request.getParameter("act"));
//		VelocityContext vc=new VelocityContext();
//    	ArticleDAO aDAO = new ArticleDAO((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbUser"),(String)vc.get("dbPWD"));
//       if(act.equals("")){
//            new GlobalVariablesBA().setCommonVariables(request, vc);
//        	vc.put("act", "add");
//            VelocityParserFactory.getVP().render("editarticle", vc, request, response);
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
//	    	ArticleDTO article = aDAO.getArticleByID(id);
//        	vc.put("article", article);
//            VelocityParserFactory.getVP().render("editarticle", vc, request, response);
//            return;
//        }
//        if(act.equals("update")){
//            update(request,response);
//            return;
//        }
//	}
//	private void add(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		VelocityContext vc=new VelocityContext();
//        new GlobalVariablesBA().setCommonVariables(request, vc);
//    	ArticleDTO article = new ArticleDTO();  
//		ServletRequestDataBinder binder = new ServletRequestDataBinder(article, "article");
//		binder.bind(request);
//		article.setAuthor(((UserSignUpDTO)vc.get("customer")).getLoginName());
//		SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		article.setUpdatedTime(sd.format(new Date()));
//    	ArticleDAO aDAO = new ArticleDAO((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbUser"),(String)vc.get("dbPWD"));
//		Map<String,String> error = check(article,aDAO);
//		if(error.size()>0){
//			vc.put("error", error);
//			vc.put("act", "add");
//			vc.put("article", article);
//			VelocityParserFactory.getVP().render("editarticle", vc, request, response);
//			return;
//		}else{
//			article = aDAO.insertArticle(article);
//	    	response.sendRedirect("/listarticles.jhtml");
//		}
//	}
//	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		VelocityContext vc=new VelocityContext();
//        new GlobalVariablesBA().setCommonVariables(request, vc);
//        String id= Util.getNoNull(request.getParameter("id"));
//    	ArticleDAO aDAO = new ArticleDAO((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbUser"),(String)vc.get("dbPWD"));
//    	ArticleDTO article = aDAO.getArticleByID(id);
//		ServletRequestDataBinder binder = new ServletRequestDataBinder(article, "product");
//		binder.bind(request);
//		Map<String,String> error = check(article,aDAO);
//		if(error.size()>0){
//			vc.put("error", error);
//			vc.put("act", "update");
//			vc.put("article", article);
//			VelocityParserFactory.getVP().render("editarticle", vc, request, response);
//			return;
//		}else{
//	    	aDAO.updateArticle(article);
//	    	response.sendRedirect("/listarticles.jhtml");
//		}
//	}
//	
//	private Map<String,String> check(ArticleDTO p,ArticleDAO aDAO) throws SQLException{
//		Map<String,String> error = new HashMap<String,String>();
//		if(Util.getNoNull(p.getType()).length()<1)error.put("typeError", "输入类型");
//		if(Util.getNoNull(p.getHeader()).length()<1)error.put("headerEmptyError", "输入标题");
//		if(p.getContent().length()<1)error.put("ContentEmptyError", "输入内容");
//		return error;
//	}
//}
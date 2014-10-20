//package ruking.controller;
//
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import ruking.ba.GlobalVariablesBA;
//import ruking.dao.ArticleDAO;
//import ruking.dto.ArticleDTO;
//import ruking.utils.Util;
//import ruking.velocity.VelocityParserFactory;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//public class SolutionsController extends BaseController {
//	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        VelocityContext vc=new VelocityContext();
//        new GlobalVariablesBA().setCommonVariables(request, vc);
//        String id = request.getParameter("id");
//        ArticleDAO adao = new ArticleDAO((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbUser"),(String)vc.get("dbPWD"));
//        List<Map> articles = adao.getArticlesByType("2");
//        Map selectedArticle = null;
//        if(Util.getNoNull(id).equals("") && articles.size()>0)
//        	selectedArticle = articles.get(0);
//        else
//        	selectedArticle = adao.getMapByID(id);
//        vc.put("articles", articles);
//        vc.put("articleDTO", selectedArticle);
//        VelocityParserFactory.getVP().render("solutions", vc, request, response);
//	}
//}
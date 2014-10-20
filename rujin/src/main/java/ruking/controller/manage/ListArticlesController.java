package ruking.controller.manage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import ruking.ba.GlobalVariablesBA;
import ruking.controller.BaseController;
import ruking.dao.ArticleDAO;
import ruking.velocity.VelocityParserFactory;

public class ListArticlesController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
    	ArticleDAO aDAO = new ArticleDAO();
//		String act= Util.getNoNull(request.getParameter("act"));
//		if(act.equals("del")){
//			String id = Util.getNoNull(request.getParameter("id"));
//			if(NumberUtils.isDigits(id) && aDAO.getArticleByID(id)!=null){
//				aDAO.deleteArticle(id);
//			}
//			response.sendRedirect("/listarticles.jhtml");
//			return;
//		}
   		vc.put("currentTab", "article");
   		List<Map> articles = aDAO.getAllArticles();
   		vc.put("articles", articles);
   		VelocityParserFactory.getVP().render("listarticles", vc, request, response);
	}
}

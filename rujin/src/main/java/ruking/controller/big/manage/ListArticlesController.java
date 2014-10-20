package ruking.controller.big.manage;

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
   		vc.put("currentTab", "article_big");
   		List<Map> articles = aDAO.getAllArticles_big();
   		vc.put("articles", articles);
   		VelocityParserFactory.getVP().render("listarticles_big", vc, request, response);
	}
}

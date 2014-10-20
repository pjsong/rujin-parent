package ruking.server;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;

import ruking.dao.ArticleDAO;
import ruking.dto.ArticleDTO;
import ruking.utils.Conf;

import com.google.gwt.user.server.rpc.RPCServletUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


//http://blog.csdn.net/leoyunfei/article/details/3611565

public class GetArticleServiceImpl extends HttpServlet {
	   @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        String s = req.getParameter("id");
	        if(s == null || s.equals(""))
	        	s="0";
	        JSONArray ja = null;
			try {
				ja = getJA(s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        resp.setCharacterEncoding("utf-8");
	        resp.getWriter().write(ja==null?"":ja.toString());
	    }
	   
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String str = RPCServletUtils.readContent(req, null, null);
		JSONObject p = (JSONObject) JSONSerializer.toJSON(str);
		ArticleDTO a = new ArticleDTO();
		a.setContent(p.get("content").toString());
		a.setType(p.get("type").toString());
		a.setHeader(p.get("header").toString());
		a.setAuthor(p.get("author").toString());
		Conf conf=new Conf();
		ArticleDAO sd = new ArticleDAO();
		String act = req.getParameter("act");
		if("update".equals(act)){
			a.setId(p.get("id").toString());
			try {
				sd.updateArticles(a);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
		try {
			sd.insertArticle(a);
		} catch (SQLException e) {
			e.printStackTrace();
		}}
        JSONArray ja = null;
		try {
			ja = getJA("0");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write(ja==null?"":ja.toString());
	}
	   
	   private JSONArray getJA(String id) throws SQLException, IOException{
	       ArticleDAO articleDAO = new ArticleDAO();
	       JSONArray ret = new JSONArray();
		   List<Map> result=null;
		   if(NumberUtils.isDigits(id)){
		        Integer globalcat = Integer.parseInt(id);
		        	switch(globalcat){
		        	case 0:{
		        		result = articleDAO.getAllArticles();
		        		break;
		        	}
		        	default:{
		        		result = new ArrayList<Map>();
		        		result.add(articleDAO.getMapByID(id));
		        	}
		      }
			if(result.size()>0){
				for(Map m:result){
					JSONObject jo = JSONObject.fromObject(m);
					ret.add(jo);
				}
			}
		  } 
		   return ret;
	   }
}

package ruking.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ruking.dao.CategoryDAO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//http://blog.csdn.net/leoyunfei/article/details/3611565

public class GetCategoryServiceImpl extends HttpServlet {
	   @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        JSONArray ja = null;
			try {
				ja = getJA(getLang(req));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	        resp.setCharacterEncoding("utf-8");
	        resp.getWriter().write(ja==null?"":ja.toString());
	    }
	   
	   private JSONArray getJA(String lang) throws SQLException, IOException{
		 CategoryDAO caDAO = new CategoryDAO();
	       JSONArray ret = new JSONArray();
	       Map<String,List<Map>> result = caDAO.getAllCats(lang);
			if(result.size()>0){
				for(String m:result.keySet()){
					JSONObject jo = new JSONObject();
					jo.put("CatName", m);
					List<Map> arr = result.get(m);
					JSONArray catArray= new JSONArray();
//					JSONObject joCat = new JSONObject();
					for(Map map:arr){
						JSONObject joCat = JSONObject.fromObject(map);
						catArray.add(joCat);
					}
					jo.put("CatArray", catArray);
					ret.add(jo); //ret.add(formatJO(jo,m));
				}
				}
		   return ret;
	   }
	   
	   private String getLang(HttpServletRequest req){
	        String uri = req.getParameter("lang");
	        if(uri == null) uri= "";
	        return uri;
	   }
	   
}



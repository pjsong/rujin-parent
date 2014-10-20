package ruking.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;

import ruking.dao.CategoryDAO;
import ruking.dao.ProductDAO;
import ruking.utils.Conf;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


//http://blog.csdn.net/leoyunfei/article/details/3611565

public class GetDTOServiceImpl extends HttpServlet {
	   @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        String s = req.getParameter("category");
	        if(s == null || s.equals(""))
	        	s="0";
	        JSONArray ja = null;
			try {
				ja = getJA(s,getLang(req));
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        resp.setCharacterEncoding("utf-8");
	        resp.getWriter().write(ja==null?"":ja.toString());
	    }
	   
	   private JSONArray getJA(String category,String lang) throws SQLException, IOException{
			ProductDAO paDAO = new ProductDAO();
	       JSONArray ret = new JSONArray();
		   List<Map> result=null;
		   if(NumberUtils.isDigits(category)){
		        Integer globalcat = Integer.parseInt(category);
		        if(globalcat == 0){
//		        	switch(globalcat){
//		        	case 0:{
		        		result = paDAO.getAllProducts(lang);
//		        		break;
//		        	}
//		        	default:{
//		        		result = caDAO.getgetGlobalCatProducts(globalcat,lang);
//		        	}
		        }
		        else{
			    	result = paDAO.getCatProductsByCatID(globalcat.toString(),lang);
			    }  
		   }			
		   if(result!=null && result.size()>0){
				for(Map m:result){
					JSONObject jo = JSONObject.fromObject(m);
					ret.add(jo);
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

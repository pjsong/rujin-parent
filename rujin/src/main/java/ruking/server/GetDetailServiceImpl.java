package ruking.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;

import ruking.dao.AttributeDAO;
import ruking.dao.ProductDAO;
import ruking.dto.ProductDTO;
import ruking.utils.Conf;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


//http://blog.csdn.net/leoyunfei/article/details/3611565

public class GetDetailServiceImpl extends HttpServlet {
	   @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        String s = req.getParameter("id");
	        if(s == null || s.equals(""))
	        	s="1";
	        JSONArray ja = null;
			try {
				ja = getJA(s,getLang(req));
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        resp.setCharacterEncoding("utf-8");
	        resp.getWriter().write(ja==null?"":ja.toString());
	    }
	   
	   private JSONArray getJA(String id,String lang) throws SQLException, IOException{
		   Conf conf=new Conf();
		   ProductDAO paDAO = new ProductDAO();
	       AttributeDAO attrDAO = new AttributeDAO();
	       JSONArray ret = new JSONArray();
		   List<Map> result=null;
		   if(NumberUtils.isDigits(id)){
		        result = attrDAO.getAttributesByProductId(id, lang);
		        if(result==null)return ret;
		    	ProductDTO pDTO = paDAO.getProductByID(id.toString(),lang);
		    	Map map = new HashMap();
		    	map.put("AttrName", "Title");
		    	map.put("AttrValue",pDTO.getTitle());
		    	result.add(map);
		    	Map map1 = new HashMap();
		    	String description = "描述";
		    	if("eng".equals(lang))description = "Description";
		    	map1.put("AttrName", description);
		    	map1.put("AttrValue",pDTO.getDescription());
		    	result.add(map1);
		    	Map map2 = new HashMap();
		    	map2.put("AttrName", "ID");
		    	map2.put("AttrValue",id);
		    	result.add(map2);
		    	if(result.size()>0){
		    		for(Map m:result){
		    			JSONObject jo = JSONObject.fromObject(m);
		    			ret.add(jo);
		    		}
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

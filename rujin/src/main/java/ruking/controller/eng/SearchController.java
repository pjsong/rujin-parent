package ruking.controller.eng;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.velocity.VelocityContext;

import ruking.ba.GlobalVariablesBA;
import ruking.controller.BaseController;
import ruking.dao.ProductDAO;
import ruking.search.SearchTool;
import ruking.utils.Util;
import ruking.velocity.VelocityParserFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SearchController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
        VelocityContext vc=new VelocityContext();
        String searchKeyword = Util.getNoNull(request.getParameter("searchword"));
        if(!searchKeyword.equals("")){
        	String ja = getJA(vc,searchKeyword,"eng");
        	if(!ja.equals(""))vc.put("searchResult", ja);
        	else vc.put("noResultFound", "Sorry, No match product found, Please change your search keyword, or choose the category on the left.");
        	vc.put("searchKeyword", searchKeyword);
        }
        new GlobalVariablesBA().setCommonVariables(request, vc);
        VelocityParserFactory.getVP().render("search_eng", vc, request, response);
	}
	   private String getJA(VelocityContext vc,String keywords,String lang) throws SQLException, CorruptIndexException, IOException, ParseException{
	       ProductDAO productDAO = new ProductDAO();
	       JSONArray ret = new JSONArray();
		   List<Map> result= SearchTool.getResultLM(keywords,lang);
			if(result.size()>0){
				for(Map m:result){
					JSONObject jo = JSONObject.fromObject(m);
					ret.add(jo);
				}
				return ret.toString();
			}
			else return "";
	   }
}
package ruking.controller;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.velocity.VelocityContext;

import ruking.ba.GlobalVariablesBA;
import ruking.dao.ProductDAO;
import ruking.dto.ProductDTO;
import ruking.utils.FileFilterT;
import ruking.velocity.VelocityParserFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class VideoController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
        VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
        getCurrentPage(request,vc);
        VelocityParserFactory.getVP().render("videos", vc, request, response);
	}

	
	private void getCurrentPage(HttpServletRequest request,VelocityContext vc) throws IOException, SQLException{
		ArrayList<String> vl = getVideoList();
		int totalpage = getTotalPage(vl.size());
		vc.put("size", vl.size());
        vc.put("totalPage", totalpage);
		String id = request.getParameter("pageid");
		int currPage = 1;
		if(NumberUtils.isDigits(id))
			currPage = Integer.parseInt(id);
		vc.put("currPage", currPage);
		vc.put("videolist", getListByPage(vl,currPage));

		if(totalpage > currPage)
        vc.put("nextPage", currPage+1);
        if(currPage>1)vc.put("lastPage", currPage-1);
	}
	
	private ArrayList<HashMap<String,String>> getListByPage(ArrayList<String> all,int id) throws IOException, SQLException{
		ArrayList<String> temp = new ArrayList<String>();
		if(id==0)id=1;
		id=id-1;
		int pageEndId = all.size() < (id + 1) * 6 ? all.size() : (id + 1) * 6;
		for (int i = id * 6; i < pageEndId; i++) {
			temp.add(all.get(i));
		}
		return getPVMap(temp);
	}
	private int getTotalPage(int size){
		if(size%6==0)return size/6;
		else return size/6+1;
	}
	private ArrayList<String> getVideoList(){
		ArrayList<String> ret = new ArrayList<String>();
		File dir = new File("./htdocs/static/video");
//		if(dir.isDirectory())
	    File[] files = dir.listFiles(new FileFilterT());//
	    for (File f : files)
	    {
//	      System.out.println("file: " + f.getName());
	    	String name = f.getName();
	    	if(name.indexOf(".")!=-1)
	    		name = name.substring(0,name.indexOf("."));
	    	ret.add(name);
	    }
		return ret;
	}
	
	private ArrayList<HashMap<String,String>> getPVMap(ArrayList<String> ids) throws IOException, SQLException{
		ArrayList<HashMap<String,String>> ret  = new ArrayList<HashMap<String,String>>();
		ProductDAO pDAO = new ProductDAO();
		for(String id:ids){
			HashMap hm = new HashMap<String,String>();
			hm.put("id", id);
			ProductDTO pDTO = pDAO.getProductByID(id, "");
			if(pDTO!=null && !pDTO.getTitle().equals(""))
				hm.put("title", pDTO.getTitle());
			ret.add(hm);
		}
		return ret;
	}
}


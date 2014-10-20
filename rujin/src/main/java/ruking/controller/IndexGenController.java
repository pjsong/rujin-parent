package ruking.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import ruking.ba.GlobalVariablesBA;
import ruking.ba.SiteMapGen;
import ruking.index.Indexer;
import ruking.velocity.VelocityParserFactory;

public class IndexGenController extends BaseController{

	public void process(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
        int ret = index();
        SiteMapGen.main(null);
        vc.put("ret", ret);
        VelocityParserFactory.getVP().render("indexGen", vc, request, response);
		
	}

    private int index() throws IOException, SQLException
    {
		return Indexer.startIndexBuilding();
    }
    private void writeTestFile() throws FileNotFoundException{
    	FileOutputStream fos = new FileOutputStream(new File("test.txt"));
    	PrintWriter pw = new PrintWriter(fos);
    	pw.println("write success");
    	pw.close();
    }
}

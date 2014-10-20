package ruking.controller;

import org.apache.velocity.VelocityContext;

import ruking.ba.GlobalVariablesBA;
import ruking.velocity.VelocityParserFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ProductsController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
        VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
        VelocityParserFactory.getVP().render("products", vc, request, response);
	}

}
package ruking.controller;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import ruking.db.DataSourceFactory;
import ruking.db.MDTMySQLRowMapper;

import ruking.ba.GlobalVariablesBA;
import ruking.dao.UserSignUpDAO;
import ruking.dto.UserSignUpDTO;
import ruking.session.SessionName;
import ruking.session.SessionUtil;
import ruking.velocity.VelocityParserFactory;

//import ruking.velocity.VelocityParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String doit = request.getParameter("doit");
		if(doit == null){
			VelocityContext vc=new VelocityContext();
	        display(request,response,vc);
		}else{
			submit(request,response);
		}
	}
	protected void display(HttpServletRequest request, HttpServletResponse response,VelocityContext vc) throws Exception{
    	new GlobalVariablesBA().setCommonVariables(request, vc);
        VelocityParserFactory.getVP().render("login", vc, request, response);
	}
	protected void submit(HttpServletRequest request, HttpServletResponse response) throws Exception{
        VelocityContext vc=new VelocityContext();
        new GlobalVariablesBA().setCommonVariables(request, vc);
        String username = request.getParameter("loginName");
        String password = request.getParameter("loginPassword");
        UserSignUpDAO uDAO = new UserSignUpDAO();
        UserSignUpDTO u = uDAO.login(username, password);
        if(u==null){
        	vc.put("loginError", "用户名/密码错误");
        	display(request,response,vc);
        	return;
        }
		SessionUtil sessUtil = new SessionUtil(DataSourceFactory.getDataSource((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbName"),(String)vc.get("dbPWD")), new MDTMySQLRowMapper());
    	Map<String, Object> sessData = (Map<String, Object>) request.getAttribute(SessionUtil.SESS_DATA);
    	sessUtil.putAndWrite(request, sessData,SessionName.customerDTO, u);
    	new GlobalVariablesBA().setCommonVariables(request, vc);
        VelocityParserFactory.getVP().render("index", vc, request, response);
	}
}

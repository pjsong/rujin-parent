package ruking.controller.big;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.springframework.web.bind.ServletRequestDataBinder;

import ruking.ba.GlobalVariablesBA;
import ruking.controller.BaseController;
import ruking.dao.UserSignUpDAO;
import ruking.db.DataSourceFactory;
import ruking.db.MDTMySQLRowMapper;
import ruking.dto.UserSignUpDTO;
import ruking.session.SessionName;
import ruking.session.SessionUtil;
import ruking.utils.RegExp;
import ruking.utils.Util;
import ruking.velocity.VelocityParserFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String act=Util.getNoNull(request.getParameter("act"));
		if(act.equals("")){
			VelocityContext vc=new VelocityContext();
			new GlobalVariablesBA().setCommonVariables(request, vc);
			VelocityParserFactory.getVP().render("register_big", vc, request, response);
			return;
		}
		if(act.equals("regyes")){
			VelocityContext vc=new VelocityContext();
			new GlobalVariablesBA().setCommonVariables(request, vc);
	        VelocityParserFactory.getVP().render("registerYes_big", vc, request, response);
	        return;
		}
		if(act.equals("regact")){
	        registerUser(request,response);
	        return;
		}
		if(act.equals("updateact")){
	        updateUser(request,response);
	        return;
		}
		if(act.equals("showact")){
	        showUser(request,response);
	        return;
		}
	}
	protected void registerUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
		new GlobalVariablesBA().setCommonVariables(request, vc);
		UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(userSignUpDTO, "userSignUpDTO");
		binder.bind(request);
		UserSignUpDAO uDAO = new UserSignUpDAO();
		Map<String,String> error=check(userSignUpDTO,uDAO);
		if(error.size()>0){
			vc.put("error", error);
			vc.put("userSignUpDTO", userSignUpDTO);
			VelocityParserFactory.getVP().render("registerYes", vc, request, response);
			return;
		}else{
			userSignUpDTO = uDAO.insertUser(userSignUpDTO);
			vc.put("userSignUpDTO", userSignUpDTO);
			SessionUtil sessUtil = new SessionUtil(DataSourceFactory.getDataSource((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbName"),(String)vc.get("dbPWD")), new MDTMySQLRowMapper());
	    	Map<String, Object> sessData = (Map<String, Object>) request.getAttribute(SessionUtil.SESS_DATA);
	    	sessUtil.putAndWrite(request, sessData,SessionName.customerDTO, userSignUpDTO);
	    	new GlobalVariablesBA().setCommonVariables(request, vc);
	    	vc.put("register", true);
			VelocityParserFactory.getVP().render("registerDone_big", vc, request, response);
		}
	}
	protected void showUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	VelocityContext vc=new VelocityContext();
		new GlobalVariablesBA().setCommonVariables(request, vc);
		SessionUtil sessUtil = new SessionUtil(DataSourceFactory.getDataSource((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbName"),(String)vc.get("dbPWD")), new MDTMySQLRowMapper());
    	Map<String, Object> sessData = (Map<String, Object>) request.getAttribute(SessionUtil.SESS_DATA);
    	UserSignUpDTO userSignUpDTO = (UserSignUpDTO) sessData.get(SessionName.customerDTO);
		vc.put("userSignUpDTO", userSignUpDTO);
	    vc.put("updateNoError", true);
		VelocityParserFactory.getVP().render("registerDone_big", vc, request, response);
	}
	
	protected void updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
		new GlobalVariablesBA().setCommonVariables(request, vc);
		SessionUtil sessUtil = new SessionUtil(DataSourceFactory.getDataSource((String)vc.get("hostName"),(String)vc.get("dbName"),(String)vc.get("dbName"),(String)vc.get("dbPWD")), new MDTMySQLRowMapper());
    	Map<String, Object> sessData = (Map<String, Object>) request.getAttribute(SessionUtil.SESS_DATA);
    	UserSignUpDTO userSignUpDTO = ((UserSignUpDTO)sessData.get(SessionName.customerDTO));
		String oldLoginName = userSignUpDTO.getLoginName();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(userSignUpDTO, "userSignUpDTO");
		binder.bind(request);
		UserSignUpDAO uDAO = new UserSignUpDAO();
		Map<String,String> error=updateCheck(userSignUpDTO,uDAO,oldLoginName);
		if(error.size()>0){
			vc.put("error", error);
			userSignUpDTO.setLoginName(oldLoginName);
			VelocityParserFactory.getVP().render("registerYes_big", vc, request, response);
			return;
		}else{
			vc.put("userSignUpDTO", userSignUpDTO);
			uDAO.updateUser(userSignUpDTO);
	    	sessUtil.putAndWrite(request, sessData,SessionName.customerDTO, userSignUpDTO);
	    	new GlobalVariablesBA().setCommonVariables(request, vc);
	    	vc.put("updateNoError", true);
			VelocityParserFactory.getVP().render("registerDone_big", vc, request, response);
		}
	}
	
	
	private Map<String,String> check(UserSignUpDTO u,UserSignUpDAO uDAO) throws SQLException{
		Map<String,String> ret = new HashMap<String,String>();
		String loginName = Util.getNoNull(u.getLoginName()).trim();
		if(loginName.equals(""))ret.put("loginNameError", "請填寫登錄名");
		if(!loginName.equals("") && uDAO.loginNameExists(loginName))ret.put("loginNameExistsError", loginName+"已被佔用，請另外填寫登錄名");
		if(Util.getNoNull(u.getPassword()).trim().equals(""))ret.put("passwordError", "請填寫密碼");
		if(Util.getNoNull(u.getPassword()).trim().length()<6)ret.put("passwordLenthError", "密碼需要大於6位");
		if(Util.getNoNull(u.getPasswordV()).trim().equals(""))ret.put("passwordVError", "請填寫確認密碼");
		if(!Util.getNoNull(u.getPassword()).equals(Util.getNoNull(u.getPasswordV())))ret.put("passwordAndVError", "密碼確認有誤，請重新填寫密碼並確認");
		if(Util.getNoNull(u.getQuestion()).trim().equals("-1"))ret.put("questionError", "請選擇用於找回密碼的提示問題");
		if(Util.getNoNull(u.getLoginName()).trim().equals(""))ret.put("answerError", "請填寫問題答案");
		if(Util.getNoNull(u.getQuestion()).trim().equals("birthdayOfMom") && !Util.getNoNull(u.getAnswer()).matches("\\d\\d\\d\\d-\\d\\d-\\d\\d"))ret.put("birthdayError", "生日輸入格式有誤");
		if(Util.getNoNull(u.getEmail()).trim().equals(""))ret.put("emailEmptyError", "請填寫郵箱");
		if(!Util.getNoNull(u.getEmail()).trim().matches(RegExp.emailRegExp))ret.put("emailFormatError", "請填寫郵箱正確格式");
		if(Util.getNoNull(u.getCompanyaddress()).trim().equals(""))ret.put("companyAddressError", "請填寫聯系地址");
		if(Util.getNoNull(u.getMobile()).trim().equals(""))ret.put("mobileError", "請填寫聯系電話");
		return ret;
	}
	private Map<String,String> updateCheck(UserSignUpDTO u,UserSignUpDAO uDAO,String oldLoginName) throws SQLException{
		Map<String,String> ret = new HashMap<String,String>();
		String loginName = Util.getNoNull(u.getLoginName()).trim();
		if(loginName.equals(""))ret.put("loginNameError", "請填寫登錄名");
		if(!loginName.equals(oldLoginName) && uDAO.loginNameExists(loginName))ret.put("loginNameExistsError", loginName+"已被佔用，請另外填寫登錄名");
		if(Util.getNoNull(u.getPassword()).trim().equals(""))ret.put("passwordError", "請填寫密碼");
		if(Util.getNoNull(u.getPassword()).trim().length()<6)ret.put("passwordLenthError", "密碼需要大於6位");
		if(Util.getNoNull(u.getPasswordV()).trim().equals(""))ret.put("passwordVError", "請填寫確認密碼");
		if(!Util.getNoNull(u.getPassword()).equals(Util.getNoNull(u.getPasswordV())))ret.put("passwordAndVError", "密碼確認有誤，請重新填寫密碼並確認");
		if(Util.getNoNull(u.getQuestion()).trim().equals("-1"))ret.put("questionError", "請選擇用於找回密碼的提示問題");
		if(Util.getNoNull(u.getLoginName()).trim().equals(""))ret.put("answerError", "請填寫問題答案");
		if(Util.getNoNull(u.getQuestion()).trim().equals("birthdayOfMom") && !Util.getNoNull(u.getAnswer()).matches("\\d\\d\\d\\d-\\d\\d-\\d\\d"))ret.put("birthdayError", "生日輸入格式有誤");
		if(Util.getNoNull(u.getEmail()).trim().equals(""))ret.put("emailEmptyError", "請填寫郵箱");
		if(!Util.getNoNull(u.getEmail()).trim().matches(RegExp.emailRegExp))ret.put("emailFormatError", "請填寫郵箱正確格式");
		if(Util.getNoNull(u.getCompanyaddress()).trim().equals(""))ret.put("companyAddressError", "請填寫聯系地址");
		if(Util.getNoNull(u.getMobile()).trim().equals(""))ret.put("mobileError", "請填寫聯系電話");
		return ret;
	}
}
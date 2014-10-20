package ruking.controller.manage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class EditUserController extends BaseController {
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String act = request.getParameter("act");
		if(Util.getNoNull(act).equals("update")){
			updateUser(request,response);
		}
		else{
			VelocityContext vc=new VelocityContext();
			new GlobalVariablesBA().setCommonVariables(request, vc);
			String id = request.getParameter("id");
			if(Util.getNoNull(id).equals(""))
				response.sendRedirect("/listusers.jhtml");
	    	UserSignUpDAO uDAO = new UserSignUpDAO();
	    	UserSignUpDTO userSignUpDTO = uDAO.getUserByID(id);  
	    	vc.put("user", userSignUpDTO);
	    	VelocityParserFactory.getVP().render("edituser", vc, request, response);
	    	return;
		}
	}
	
	protected void updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		VelocityContext vc=new VelocityContext();
		new GlobalVariablesBA().setCommonVariables(request, vc);
		String id = request.getParameter("id");
    	UserSignUpDAO uDAO = new UserSignUpDAO();
    	UserSignUpDTO userSignUpDTO = uDAO.getUserByID(id);  
		String oldLoginName = userSignUpDTO.getLoginName();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(userSignUpDTO, "userSignUpDTO");
		binder.bind(request);
		Map<String,String> error=updateCheck(userSignUpDTO,uDAO,oldLoginName);
		vc.put("user", userSignUpDTO);
		if(error.size()>0){
			vc.put("error", error);
			userSignUpDTO.setLoginName(oldLoginName);
			VelocityParserFactory.getVP().render("edituser", vc, request, response);
			return;
		}else{
			uDAO.updateUser(userSignUpDTO);
			response.sendRedirect("/listusers.jhtml");
		}
	}
	
	private Map<String,String> updateCheck(UserSignUpDTO u,UserSignUpDAO uDAO,String oldLoginName) throws SQLException{
		Map<String,String> ret = new HashMap<String,String>();
		String loginName = Util.getNoNull(u.getLoginName()).trim();
		if(loginName.equals(""))ret.put("loginNameError", "请填写登录名");
		if(!loginName.equals(oldLoginName) && uDAO.loginNameExists(loginName))ret.put("loginNameExistsError", loginName+"已被占用，请另外填写登录名");
		if(Util.getNoNull(u.getPassword()).trim().equals(""))ret.put("passwordError", "请填写密码");
		if(Util.getNoNull(u.getPassword()).trim().length()<6)ret.put("passwordLenthError", "密码需要大于6位");
		if(Util.getNoNull(u.getPasswordV()).trim().equals(""))ret.put("passwordVError", "请填写确认密码");
		if(!Util.getNoNull(u.getPassword()).equals(Util.getNoNull(u.getPasswordV())))ret.put("passwordAndVError", "密码确认有误，请重新填写密码并确认");
		if(Util.getNoNull(u.getQuestion()).trim().equals("-1"))ret.put("questionError", "请选择用于找回密码的提示问题");
		if(Util.getNoNull(u.getLoginName()).trim().equals(""))ret.put("answerError", "请填写问题答案");
		if(Util.getNoNull(u.getQuestion()).trim().equals("birthdayOfMom") && !Util.getNoNull(u.getAnswer()).matches("\\d\\d\\d\\d-\\d\\d-\\d\\d"))ret.put("birthdayError", "生日输入格式有误");
		if(Util.getNoNull(u.getEmail()).trim().equals(""))ret.put("emailEmptyError", "请填写邮箱");
		if(!Util.getNoNull(u.getEmail()).trim().matches(RegExp.emailRegExp))ret.put("emailFormatError", "请填写邮箱正确格式");
		if(Util.getNoNull(u.getCompanyaddress()).trim().equals(""))ret.put("companyAddressError", "请填写联系地址");
		if(Util.getNoNull(u.getMobile()).trim().equals(""))ret.put("mobileError", "请填写联系电话");
		return ret;
	}
}

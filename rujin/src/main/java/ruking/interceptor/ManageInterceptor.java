package ruking.interceptor;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ruking.db.DataSourceFactory;
import ruking.db.MDTMySQLRowMapper;
import ruking.dto.SessionDTO;
import ruking.log.SessionLogger;
import ruking.session.SessionName;
import ruking.session.SessionUtil;
import ruking.utils.Conf;
import ruking.utils.EscapeTool;
import ruking.utils.Util;

public class ManageInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
		Conf conf = new Conf();
		// check session, if not available, create one
		String dbName = conf.getDbName();
		String dbPWD = conf.getDbPassword();
		String hostName = conf.getHostName();
		String dbUser = conf.getDbUser();
		SessionUtil sessUtil = new SessionUtil(DataSourceFactory.getDataSource(hostName,dbName,dbUser,dbPWD), new MDTMySQLRowMapper());
		Map<String, Object> sessData = sessUtil.read(request);
		if (sessData == null || sessData.size() == 0)
		{
//			String url = request.getRequestURI();
//			Map<String,String> map = request.getParameterMap();
//			if(map.size()>0){
//				StringBuffer param = new StringBuffer("?");
//				for(String key:map.keySet()){
//					String value = Util.getNoNull(request.getParameter(key));
//					param.append(key + (value.equals("")?"":("="+value))+"&");
//				}
//				param.deleteCharAt(param.lastIndexOf("&"));
//				Util.redirect302(response,"login.jhtml?uri=" + new EscapeTool().url(url+param.toString()));
//			}else{
//				Util.redirect302(response,"login.jhtml?uri=" + new EscapeTool().url(url));
//			}
			return false;
		}
		else
		{
	    	String sessId = sessUtil.getSessIdFromCookie(request);
			sessUtil.updateLastUpdatedField(sessId);
	    	request.setAttribute(SessionUtil.SESS_ID, sessId);
	    	request.setAttribute(SessionUtil.SESS_DATA, sessData);
			return true;
		}
	}
}

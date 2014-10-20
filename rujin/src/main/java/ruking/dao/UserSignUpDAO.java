package ruking.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ruking.db.DataSourceFactory;
import ruking.db.DbUtil;
import ruking.db.MDTMySQLRowMapper;
import ruking.db.QueryRunner;
import ruking.db.TransRunner;
import ruking.dto.UserSignUpDTO;
import ruking.utils.Conf;
import ruking.utils.Util;

public class UserSignUpDAO {
	public String hostName;
	public String dbName ;//= "zkm0m1_db";
	public String password ;//= "pjsong";
	public String dbUser;

	public UserSignUpDAO() throws IOException {
		super();
		Conf conf=new Conf();
		this.hostName = conf.getHostName();
		this.dbName = conf.getDbName();
		this.dbUser = conf.getDbUser();
		this.password = conf.getDbPassword();
	}
	
	public List<Map> getAllUsers()throws SQLException{
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM users";
		return runner.query(sql);
	}
	public  UserSignUpDTO getUserByLoginName(String loginName) throws SQLException{
		UserSignUpDTO u=new UserSignUpDTO();
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM users WHERE LoginName = " + DbUtil.escSql(loginName);
		Map m=runner.queryForMap(sql);
		if(m==null)return null;
		else{
			u.setAnswer((String)m.get("Answer"));
			u.setQuestion((String)m.get("Question"));
			u.setCompanyaddress((String)m.get("CompanyAddress"));
			u.setCompanyname((String)m.get("CompanyName"));
			u.setCompanywebsite((String)m.get("CompanyWebSite"));
			u.setEmail((String)m.get("Email"));
			u.setPhoneFax((String)m.get("PhoneFax"));
			u.setLoginName((String)m.get("LoginName"));
			u.setMsnNumber((String)m.get("MsnNumber"));
			u.setQqNumber((String)m.get("QQNumber"));
			u.setName((String)m.get("RealName"));
			u.setMobile((String)m.get("Mobile"));
			u.setId((Integer)m.get("ID"));
			u.setAuthority((Integer)m.get("Authority"));
			u.setNewsLetter(Boolean.parseBoolean((String)m.get("NewsLetter")));
		}
		return u;
	}
	
	public  UserSignUpDTO getUserByID(String id) throws SQLException{
		UserSignUpDTO u=new UserSignUpDTO();
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM users WHERE ID = " + DbUtil.escSql(id);
		Map m=runner.queryForMap(sql);
		if(m==null)return null;
		else{
			u.setAnswer((String)m.get("Answer"));
			u.setQuestion((String)m.get("Question"));
			u.setCompanyaddress((String)m.get("CompanyAddress"));
			u.setCompanyname((String)m.get("CompanyName"));
			u.setCompanywebsite((String)m.get("CompanyWebSite"));
			u.setEmail((String)m.get("Email"));
			u.setPhoneFax((String)m.get("PhoneFax"));
			u.setLoginName((String)m.get("LoginName"));
			u.setMsnNumber((String)m.get("MsnNumber"));
			u.setQqNumber((String)m.get("QQNumber"));
			u.setName((String)m.get("RealName"));
			u.setMobile((String)m.get("Mobile"));
			u.setId((Integer)m.get("ID"));
			u.setAuthority((Integer)m.get("Authority"));
			u.setNewsLetter(Boolean.parseBoolean((String)m.get("NewsLetter")));
		}
		return u;
	}
	
	public  boolean loginNameExists(String loginName) throws SQLException{
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM users WHERE LoginName = '" + loginName+"';";
		Map m=runner.queryForMap(sql);
		if(m==null)return false;
		return true;
	}
	public  UserSignUpDTO forgetPassword(String email,String question,String answer) throws SQLException{
		UserSignUpDTO u=new UserSignUpDTO();
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM users WHERE Email = " + DbUtil.escSql(email)+" and Question="+DbUtil.escSql(question)+" and Answer="+DbUtil.escSql(answer);
		Map m=runner.queryForMap(sql);
		if(m==null)return null;
		return getUserByLoginName((String)m.get("LoginName"));
	}
	public  UserSignUpDTO insertUser(UserSignUpDTO u) throws SQLException{
		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql="insert into users(LoginName,Password,Question,Sex,Answer,Email,CompanyAddress,Mobile,NewsLetter";
		if(!Util.getNoNull(u.getCompanywebsite()).trim().equals(""))sql+=",CompanyWebSite";
		if(!Util.getNoNull(u.getCompanyname()).trim().equals(""))sql+=",CompanyName";
		if(!Util.getNoNull(u.getName()).trim().equals(""))sql+=",RealName";
		if(!Util.getNoNull(u.getQqNumber()).trim().equals(""))sql+=",QQNumber";
		if(!Util.getNoNull(u.getMsnNumber()).trim().equals(""))sql+=",MsnNumber";
		if(!Util.getNoNull(u.getPhoneFax()).trim().equals(""))sql+=",PhoneFax";
		sql = sql+") values ("+DbUtil.escSql(u.getLoginName().trim())+","+DbUtil.escSql(u.getPassword().trim())+","+DbUtil.escSql(u.getQuestion())+","+DbUtil.escSql(u.getSex())+","+DbUtil.escSql(u.getAnswer())+","+DbUtil.escSql(u.getEmail())+","+DbUtil.escSql(u.getCompanyaddress())+","+DbUtil.escSql(u.getMobile())+","+DbUtil.escSql(u.getNewsLetter());
		if(!Util.getNoNull(u.getCompanywebsite()).equals(""))sql+=","+DbUtil.escSql(u.getCompanywebsite().trim());
		if(!Util.getNoNull(u.getCompanyname()).equals(""))sql+=","+DbUtil.escSql(u.getCompanyname().trim());
		if(!Util.getNoNull(u.getName()).equals(""))sql+=","+DbUtil.escSql(u.getName().trim());
		if(!Util.getNoNull(u.getQqNumber()).equals(""))sql+=","+DbUtil.escSql(u.getQqNumber().trim());
		if(!Util.getNoNull(u.getMsnNumber()).equals(""))sql+=","+DbUtil.escSql(u.getMsnNumber().trim());
		if(!Util.getNoNull(u.getPhoneFax()).equals(""))sql+=","+DbUtil.escSql(u.getPhoneFax().trim());
		sql=sql+");";
		runner.update(sql);
		Map m= runner.queryForMap("select ID from users where LoginName="+DbUtil.escSql(u.getLoginName()));
		u.setId((Integer)m.get("ID"));
		return u;
	}
	public  void updateUser(UserSignUpDTO u) throws SQLException{
		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql="update users set LoginName="+DbUtil.escSql(u.getLoginName())+",Password="+DbUtil.escSql(u.getPassword());
		sql+=",Question="+DbUtil.escSql(u.getQuestion())+",Sex="+DbUtil.escSql(u.getSex());
		sql+=",Answer="+DbUtil.escSql(u.getAnswer())+",Email="+DbUtil.escSql(u.getEmail());
		sql+=",CompanyAddress="+DbUtil.escSql(u.getCompanyaddress())+",Mobile="+DbUtil.escSql(u.getMobile());
		sql+=",NewsLetter="+DbUtil.escSql(u.getNewsLetter())+",CompanyWebSite="+DbUtil.escSql(u.getCompanywebsite());
		sql+=",CompanyName="+DbUtil.escSql(u.getCompanyname())+",RealName="+DbUtil.escSql(u.getName());
		sql+=",QQNumber="+DbUtil.escSql(u.getQqNumber())+",MsnNumber="+DbUtil.escSql(u.getMsnNumber());
		sql+=",PhoneFax="+DbUtil.escSql(u.getPhoneFax())+" where ID="+DbUtil.escSql(u.getId())+";";

		runner.update(sql);
	}
	public UserSignUpDTO login(String username,String password) throws SQLException{
		UserSignUpDTO u=new UserSignUpDTO();
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM users WHERE LoginName = " + DbUtil.escSql(username)+" and Password="+DbUtil.escSql(password);
		Map m=runner.queryForMap(sql);
		if(m==null)return null;
		return getUserByLoginName((String)m.get("LoginName"));
	}
}

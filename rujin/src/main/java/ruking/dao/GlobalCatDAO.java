//package ruking.dao;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import ruking.db.DataSourceFactory;
//import ruking.db.DbUtil;
//import ruking.db.MDTMySQLRowMapper;
//import ruking.db.QueryRunner;
//import ruking.db.TransRunner;
//import ruking.dto.GlobalCatDTO;
//import ruking.utils.Util;
//
//public class GlobalCatDAO {
//	public String hostName;
//	public String dbName;// = "zkm0m1_db";
//	public String password;// = "pjsong";
//	public String dbUser;
//
//	public GlobalCatDAO(String hostName, String dbName, String dbUser,
//			String password) {
//		super();
//		this.hostName = hostName;
//		this.dbName = dbName;
//		this.dbUser = dbUser;
//		this.password = password;
//	}
//
//	public List<Map> getAllGlobals() throws SQLException {
//		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(
//				hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
//		String sql = "SELECT * FROM globalcat";
//		return runner.query(sql);
//	}
//
//	public GlobalCatDTO getGlobalByID(String id) throws SQLException {
//		GlobalCatDTO u = new GlobalCatDTO();
//		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
//		String sql = "SELECT * FROM globalcat WHERE ID = " + DbUtil.escSql(id);
//		Map m = runner.queryForMap(sql);
//		if (m == null)
//			return null;
//		else {
//			u.setId(((Integer) m.get("ID")));
//			u.setCatName((String) m.get("CatName"));
//			u.setProductIDs((String) m.get("ProductIDs"));
//		}
//		return u;
//	}
//	
//	public List<Map> getGlobalsById(String id)	throws SQLException {
//		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
//		String sql = "SELECT * FROM globalcat WHERE ID = " + DbUtil.escSql(id);
//		List<Map> m = runner.query(sql);
//		if (m == null || m.size() == 0)
//			return null;
//		return m;
//	}
//	public GlobalCatDTO insertGlobalCat(GlobalCatDTO p) throws SQLException {
//		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(
//				hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
//		String sql = "insert into globalcat(CatName,ProductIDs) values (";
//		sql = sql + DbUtil.escSql(p.getCatName().trim()) + ","+ DbUtil.escSql(p.getProductIDs().trim())+");";
//		runner.update(sql);
//		Map m = runner.queryForMap("select max(ID) from globalcat");
//		p.setId(((Integer) m.get("ID")));
//		return p;
//	}
//
//	public void updateGlobal(GlobalCatDTO p) throws SQLException {
//		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
//		String sql = "update globalcat set CatName="
//				+ DbUtil.escSql(p.getCatName()) + ",ProductIDs=" + DbUtil.escSql(p.getProductIDs());
//		sql += " where ID="+DbUtil.escSql(p.getId());
//		runner.update(sql);
//	}
//
//	public void deleteGlobal(String id) throws SQLException {
//		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(
//				hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
//		String sql = "delete FROM globalcat WHERE ID = " + DbUtil.escSql(id);
//		runner.update(sql);
//	}
//}

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
import ruking.dto.AttributeDTO;
import ruking.utils.Conf;
import ruking.utils.Util;

public class AttributeDAO {
	public String hostName;
	public String dbName;// = "zkm0m1_db";
	public String password;// = "pjsong";
	public String dbUser;

	public AttributeDAO() throws IOException {
		super();
		Conf conf=new Conf();
		this.hostName = conf.getHostName();
		this.dbName = conf.getDbName();
		this.dbUser = conf.getDbUser();
		this.password = conf.getDbPassword();
	}

	public List<Map> getAllAttributes(String lang) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(
				hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM attributes";
		if("eng".equals(lang))sql = "SELECT * FROM attributes_eng";
		if("big".equals(lang))sql = "SELECT * FROM attributes_big";
		return runner.query(sql);
	}

	public List<Map> getAttributesByProductId(String id,String lang)
			throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(
				hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM attributes WHERE productID = "+ DbUtil.escSql(id) + " order by ProductID,DisplayOrder";
		if("eng".equals(lang))sql = "SELECT * FROM attributes_eng WHERE productID = "+ DbUtil.escSql(id) + " order by ProductID,DisplayOrder";
		if("big".equals(lang))sql = "SELECT * FROM attributes_big WHERE productID = "+ DbUtil.escSql(id) + " order by ProductID,DisplayOrder";
		List<Map> m = runner.query(sql);
		if (m == null || m.size() == 0)
			return null;
		return m;
	}

	public AttributeDTO getAttributeByID(String id,String lang) throws SQLException {
		AttributeDTO u = new AttributeDTO();
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM attributes WHERE ID = " + DbUtil.escSql(id);
		if("eng".equals(lang))sql = "SELECT * FROM attributes_eng WHERE ID = " + DbUtil.escSql(id);
		if("big".equals(lang))sql = "SELECT * FROM attributes_big WHERE ID = " + DbUtil.escSql(id);
		Map m = runner.queryForMap(sql);
		if (m == null)
			return null;
		else {
			u.setId(((Integer) m.get("ID")).toString());
			u.setProductId((String) m.get("ProductID"));
			u.setAttrName((String) m.get("AttrName"));
			u.setAttrValue((String) m.get("AttrValue"));
			u.setDisplayOrder(((Integer)m.get("DisplayOrder")).toString());
		}
		return u;
	}
	

	public AttributeDTO insertProduct(AttributeDTO p,String lang) throws SQLException {
		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(
				hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
		String sql = "insert into attributes(ProductID,AttrName,AttrValue,DisplayOrder";
		sql = sql + ") values (" + DbUtil.escSql(p.getProductId().trim()) + ","
				+ DbUtil.escSql(p.getAttrName().trim()) + ","
				+ DbUtil.escSql(p.getAttrValue())+","+DbUtil.escSql(p.getDisplayOrder());
		sql = sql + ");";
		
		if("eng".equals(lang)){
			sql = "insert into attributes_eng(ProductID,AttrName,AttrValue,DisplayOrder";
			sql = sql + ") values (" + DbUtil.escSql(p.getProductId().trim()) + ","
					+ DbUtil.escSql(p.getAttrName().trim()) + ","
					+ DbUtil.escSql(p.getAttrValue())+","+DbUtil.escSql(p.getDisplayOrder());
			sql = sql + ");";
		}
		if("big".equals(lang)){
			sql = "insert into attributes_big(ProductID,AttrName,AttrValue,DisplayOrder";
			sql = sql + ") values ("+ DbUtil.escSql(p.getProductId().trim()) + ","
					+ DbUtil.escSql(p.getAttrName().trim()) + ","
					+ DbUtil.escSql(p.getAttrValue())+","+DbUtil.escSql(p.getDisplayOrder());
			sql = sql + ");";
		}

		runner.update(sql);
		Map m = runner.queryForMap("select ID from attributes where ProductID="
				+ DbUtil.escSql(p.getProductId()));
		p.setId(((Integer) m.get("ID")).toString());
		return p;
	}

	public void updateAttribute(AttributeDTO p) throws SQLException {
		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
		String sql = "update attributes set ProductID="
				+ DbUtil.escSql(p.getProductId()) + ",AttrName="
				+ DbUtil.escSql(p.getAttrName());
		sql += ",AttrValue=" + DbUtil.escSql(p.getAttrValue())+",DisplayOrder="+DbUtil.escSql(p.getDisplayOrder())+ " where ID="+DbUtil.escSql(p.getId());
		runner.update(sql);
	}

	public void deleteProduct(String id) throws SQLException {
		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(
				hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
		String sql = "delete FROM attributes WHERE ID = " + DbUtil.escSql(id);
		runner.update(sql);
	}

	public List<Map> getAllAttributes_big() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(
				hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM attributes_big";
		return runner.query(sql);
	}

	public List<Map> getAllAttributes_eng() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(
				hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM attributes_eng";
		return runner.query(sql);
	}

	public void updateAttribute_eng(AttributeDTO p) throws SQLException {
		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
		String sql = "update attributes_eng set ProductID="
				+ DbUtil.escSql(p.getProductId()) + ",AttrName="
				+ DbUtil.escSql(p.getAttrName());
		sql += ",AttrValue=" + DbUtil.escSql(p.getAttrValue())+",DisplayOrder="+DbUtil.escSql(p.getDisplayOrder())+ " where ID="+DbUtil.escSql(p.getId());
		runner.update(sql);
		
	}
	public void updateAttribute_big(AttributeDTO p) throws SQLException {
		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(hostName, dbName, dbUser, password), new MDTMySQLRowMapper());
		String sql = "update attributes_big set ProductID="
				+ DbUtil.escSql(p.getProductId()) + ",AttrName="
				+ DbUtil.escSql(p.getAttrName());
		sql += ",AttrValue=" + DbUtil.escSql(p.getAttrValue())+",DisplayOrder="+DbUtil.escSql(p.getDisplayOrder())+ " where ID="+DbUtil.escSql(p.getId());
		runner.update(sql);
		
	}
	public String addModelNameToTitleByProductId(String id,String lang) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM attributes WHERE productID = "
				+ DbUtil.escSql(id) + " and AttrName="+DbUtil.escSql("型号")+" order by ProductID,DisplayOrder";
		if ("eng".equals(lang))
			sql = "SELECT * FROM attributes_eng WHERE productID = "
					+ DbUtil.escSql(id) + " and AttrName="+DbUtil.escSql("ModelNumber")+" order by ProductID,DisplayOrder";
		if ("big".equals(lang))
			sql = "SELECT * FROM attributes_big WHERE productID = "
					+ DbUtil.escSql(id) + "  and AttrName="+DbUtil.escSql("型號")+" order by ProductID,DisplayOrder";
		Map m = runner.queryForMap(sql);
		if (m == null || m.size() == 0)
			return null;
		return (String)m.get("AttrValue");
	}
}

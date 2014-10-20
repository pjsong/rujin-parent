package ruking.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ruking.db.DataSourceFactory;
import ruking.db.DbUtil;
import ruking.db.MDTMySQLRowMapper;
import ruking.db.QueryRunner;
import ruking.db.TransRunner;
import ruking.dto.ProductDTO;
import ruking.utils.Conf;

public class ProductDAO {
	public String hostName;
	public String dbName ;//= "zkm0m1_db";
	public String password ;//= "pjsong";
	public String dbUser;
    Conf conf=new Conf();

	public ProductDAO() throws IOException {
		super();
		this.hostName = conf.getHostName();
		this.dbName = conf.getDbName();
		this.dbUser = conf.getDbUser();
		this.password = conf.getDbPassword();
	}
	
//	public List<Map> getGlobalCatProducts(int id,String lang)throws SQLException, IOException{
//		List<Map> ret = new ArrayList<Map>();
//		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
//		String sql = "SELECT ProductIDs FROM globalcat where ID="+DbUtil.escSql(id);
//		Map map = runner.queryForMap(sql);
//		if(map == null)return ret;
//		String str = (String)map.get("ProductIDs");
//		if(str==null || str.equals(""))return null;
//		String[] ids = str.split(",");
//		for(String pid:ids){
//			Map p = getMapByID(pid,lang);
//			ret.add(p);
//		}
//		return formatProductMap(ret,lang);
//	}

	public List<Map> getCatProductsByCatID(String id,String lang)throws SQLException, IOException{
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM product where CatID like '%,"+id+",%'";
		if("eng".equals(lang))sql="SELECT * FROM product_eng where CatID like '%,"+id+",%'";
		if("big".equals(lang))sql="SELECT * FROM product_big where CatID like '%,"+id+",%'";
		return formatProductMap(runner.query(sql),lang);
	}
	
	public List<Map> getAllProducts(String lang)throws SQLException, IOException{
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM product";
		if("eng".equals(lang))sql = "SELECT * FROM product_eng";
		if("big".equals(lang))sql = "SELECT * FROM product_big";
		return formatProductMap(runner.query(sql),lang);
	}
	public  ProductDTO getProductByTitle(String title) throws SQLException{
		ProductDTO u=new ProductDTO();
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM product WHERE Title = " + DbUtil.escSql(title);
		Map m=runner.queryForMap(sql);
		if(m==null)return null;
		else{
			u.setId((String)m.get("ID"));
			u.setTitle((String)m.get("Title"));
			u.setDescription((String)m.get("Description"));
		}
		return u;
	}
	public  boolean productTitleExits(String title,String lang) throws SQLException{
		ProductDTO u=new ProductDTO();
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM product WHERE Title = " + DbUtil.escSql(title);
		if("eng".equals(lang))
			sql = "SELECT * FROM product_eng WHERE Title = " + DbUtil.escSql(title);
		if("big".equals(lang))
			sql = "SELECT * FROM product_big WHERE Title = " + DbUtil.escSql(title);
		Map m=runner.queryForMap(sql);
		if(m==null)return false;
		return true;
	}

	public  ProductDTO getProductByID(String id,String lang) throws SQLException{
		ProductDTO u=new ProductDTO();
		Map m=getMapByID(id,lang);
		if(m==null)return null;
		else{
			u.setId(((Integer)m.get("ID")).toString());
			u.setTitle((String)m.get("Title"));
			u.setDescription((String)m.get("Description"));
			u.setCatID((String)m.get("CatID"));
		}
		return u;
	}
	
	private  Map getMapByID(String id,String lang) throws SQLException{
		ProductDTO u=new ProductDTO();
		QueryRunner runner = new QueryRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "SELECT * FROM product WHERE ID = " + DbUtil.escSql(id);
		if("eng".equals(lang))
			sql = "SELECT * FROM product_eng WHERE ID = " + DbUtil.escSql(id);
		if("big".equals(lang))
			sql = "SELECT * FROM product_big WHERE ID = " + DbUtil.escSql(id);
		return runner.queryForMap(sql);
	}
	
	public  ProductDTO insertProduct(ProductDTO p,String lang) throws SQLException{
		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql="insert into product(ID,Title,Description,CatID";
		sql = sql+") values ("+DbUtil.escSql(p.getId().trim())+","+DbUtil.escSql(p.getTitle().trim())+","+DbUtil.escSql(p.getDescription().trim())+"," +DbUtil.escSql(p.getCatID());
		sql=sql+");";
		if("eng".equals(lang)){
			sql="insert into product_eng(ID,Title,Description,CatID";
			sql = sql+") values ("+DbUtil.escSql(p.getId().trim())+","+DbUtil.escSql(p.getTitle().trim())+","+DbUtil.escSql(p.getDescription().trim())+","+DbUtil.escSql(p.getCatID());
			sql=sql+");";
		}
		if("big".equals(lang)){
			sql="insert into product_big(ID,Title,Description,CatID";
			sql = sql+") values ("+DbUtil.escSql(p.getId().trim())+","+DbUtil.escSql(p.getTitle().trim())+","+DbUtil.escSql(p.getDescription().trim())+","+DbUtil.escSql(p.getCatID());
			sql=sql+");";
		}
		runner.update(sql);
		Map m= runner.queryForMap("select ID from product where Title="+DbUtil.escSql(p.getTitle()));
		if(m!=null)p.setId(((Integer)m.get("ID")).toString());
		return p;
	}

	public  void updateProduct(ProductDTO p,String id,String lang) throws SQLException{
		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql="update product set ID="+DbUtil.escSql(p.getId())+", Title="+DbUtil.escSql(p.getTitle())+",Description="+DbUtil.escSql(p.getDescription());
		sql+=",CatID="+DbUtil.escSql(p.getCatID())+" where ID="+DbUtil.escSql(id);
		if("eng".equals(lang)){
			sql="update product_eng set ID="+DbUtil.escSql(p.getId())+", Title="+DbUtil.escSql(p.getTitle())+",Description="+DbUtil.escSql(p.getDescription());
			sql+=",CatID="+DbUtil.escSql(p.getCatID())+" where ID="+DbUtil.escSql(id);
		}
		if("big".equals(lang)){
			sql="update product_big set ID="+DbUtil.escSql(p.getId())+", Title="+DbUtil.escSql(p.getTitle())+",Description="+DbUtil.escSql(p.getDescription());
			sql+=",CatID="+DbUtil.escSql(p.getCatID())+" where ID="+DbUtil.escSql(id);
		}
		runner.update(sql);
	}
	
	public void deleteProduct(String id,String lang) throws SQLException{
		if(lang!=null && !lang.equals("")){
			lang = "_"+ lang;
		}
		TransRunner runner = new TransRunner(DataSourceFactory.getDataSource(hostName,dbName,dbUser,password), new MDTMySQLRowMapper());
		String sql = "delete FROM product"+lang+" WHERE ID = " + DbUtil.escSql(id);
		runner.update(sql);
	}

	private List<Map> formatProductMap(List<Map> lm,String lang) throws SQLException, IOException{
		for(Map m:lm){
			Integer id = (Integer)m.get("ID");
			AttributeDAO attrDAO = new AttributeDAO();
			String value = attrDAO.addModelNameToTitleByProductId(id.toString(),lang);
			if(value!=null && value.length() > 1)
			m.put("Title", ((String)m.get("Title"))+"_"+value);
		}
		return lm;
	}
}

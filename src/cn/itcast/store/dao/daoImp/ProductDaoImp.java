package cn.itcast.store.dao.daoImp;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.utils.JDBCUtils;

public class ProductDaoImp implements ProductDao {

	@Override
	public Product findProductByPid(String pid) throws Exception {

		String sql = "select * from product where pid =?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	@Override
	public List<Product> findHots() throws Exception {
		
		String sql ="SELECT * FROM product WHERE pflag =0 ORDER BY pdate DESC LIMIT 0,9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql,new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findNews() throws Exception {

		String sql ="SELECT * FROM product WHERE pflag =0 AND is_hot =0 ORDER BY pdate DESC LIMIT 0,9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql,new BeanListHandler<Product>(Product.class));
	}


	@Override
	public int findTotalRecords(String cid) throws Exception {
		
		String sql ="SELECT count(*) FROM product WHERE cid = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)qr.query(sql, new ScalarHandler(),cid);
		
		return num.intValue();
	}

	@Override
	public List findProductsWithCidAndPage(String cid, int startIndex, int pageSize) throws Exception {

		String sql = "select * from product where cid =? limit ?,?";
		
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		return qr.query(sql, new BeanListHandler<Product>(Product.class) ,cid ,startIndex ,pageSize);
		
	}

}

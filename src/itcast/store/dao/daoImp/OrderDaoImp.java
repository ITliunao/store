package cn.itcast.store.dao.daoImp;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.store.dao.OrderDao;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.Product;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;

public class OrderDaoImp implements OrderDao {

	@Override
	public void saveOrder(Connection conn, Order order) throws Exception {
		
		String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { order.getOid() ,order.getOrderTime() ,order.getTotal() ,order.getState() ,order.getAddress(),
				order.getName(),order.getTelephone(),order.getUser().getUid()};
		qr.update(conn, sql, params);
		
	}

	@Override
	public void saveOrderItem(Connection conn,OrderItem item) throws Exception {
		
		String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
		System.out.println(sql);
		
		QueryRunner qr = new QueryRunner();
		Object[] params = { item.getItemid(),item.getQuantity(),item.getTotal() ,item.getProduct().getPid(),item.getOrder().getOid() };
		qr.update(conn, sql, params);
		
	}

	@Override
	public int getTotalRecords(User user) throws Exception {

		String sql = "select count(*) from orders where uid =?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)qr.query(sql, new ScalarHandler(),user.getUid());
		
		return num.intValue();
	}

	@Override
	public List findMyOrdersWithPages(User user, int startIndex, int pageSize) throws Exception {

		String sql = "select * from  orders where uid =? limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),user.getUid(),startIndex,pageSize);
		
		//遍历lsit 将订单项查询出来
		for (Order order : list) {
			//获取 所有订单的oid ，并将每笔订单下的订单项，及对应的商品信息查询出来
			String oid = order.getOid();
			sql = "select * from orderItem o ,product p where o.pid = p.pid and oid=?";
			
			List<Map<String, Object>> list02 = qr.query(sql, new MapListHandler(),oid);
			
			//遍历list02
			for (Map<String, Object> map : list02) {
				
				OrderItem oderItem = new OrderItem();
				Product product = new Product();
				
				//创建时间转换器
				DateConverter dt = new DateConverter();
				//设置转换格式
				dt.setPattern("yyyy-MM-dd");
				//注册转换器
				ConvertUtils.register(dt, java.util.Date.class);
				
				//将map中属于orderItem的数据自动填充到OrderItem中
				BeanUtils.populate(oderItem, map);
				//将map中属于product的数据自动填充到product中
				BeanUtils.populate(product, map);
				
				//让每个订单项与商品发生关联关系
				oderItem.setProduct(product);
				
				//让每个订单项存入到订单中
				order.getList().add(oderItem);
			}
			
		}
		
		return list;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		String sql = "select * from orders where oid =?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Order order = qr.query(sql,new BeanHandler<Order>(Order.class),oid);
		

		//获取 所有订单的oid ，并将每笔订单下的订单项，及对应的商品信息查询出来
		sql = "select * from orderItem o ,product p where o.pid = p.pid and oid=?";
		
		List<Map<String, Object>> list02 = qr.query(sql, new MapListHandler(),oid);
		
		//遍历list02
		for (Map<String, Object> map : list02) {
			
			OrderItem oderItem = new OrderItem();
			Product product = new Product();
			
			//创建时间转换器
			DateConverter dt = new DateConverter();
			//设置转换格式
			dt.setPattern("yyyy-MM-dd");
			//注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			
			//将map中属于orderItem的数据自动填充到OrderItem中
			BeanUtils.populate(oderItem, map);
			//将map中属于product的数据自动填充到product中
			BeanUtils.populate(product, map);
			
			//让每个订单项与商品发生关联关系
			oderItem.setProduct(product);
			
			//让每个订单项存入到订单中
			order.getList().add(oderItem);
		}
		
		
		return order;
	}

}

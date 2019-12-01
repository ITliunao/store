package cn.itcast.store.service.serviceImp;

import java.sql.Connection;
import java.util.List;

import cn.itcast.store.dao.OrderDao;
import cn.itcast.store.dao.daoImp.OrderDaoImp;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.utils.JDBCUtils;

public class OrderServiceImp implements OrderService {

	OrderDao orderdao = new OrderDaoImp();            
	@Override
	public void saveOrder(Order order) throws Exception  {
		
		// 保存订单与订单下所有的订单项 (同时成功不然失败)
		
		/*	第一种方式
		try {
		
			JDBCUtils.startTransaction();
			OrderDao orderdao = new OrderDaoImp();
			orderdao.saveOrder(order);
			
			for (OrderItem item : order.getList()) {
				orderdao.saveOrderItem(item);
			}
			
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			// TODO: handle exception
			JDBCUtils.rollbackAndClose();
		}*/
		
		//第二种方式
		Connection conn = null;
		
		try {
			//获取链接
			conn = JDBCUtils.getConnection();
			
			//开启事务
			conn.setAutoCommit(false);
			
			//保存订单
			orderdao.saveOrder(conn , order);
			
			for (OrderItem item : order.getList()) {
				orderdao.saveOrderItem(conn ,item);
			}
			
			//提交
			conn.commit();
			
		} catch (Exception e) {

			e.printStackTrace();
			conn.rollback();
		}finally {
			if(null != conn){
				conn.close();
				conn = null;
			}
		}
		
		
	}

	@Override
	public PageModel findMyOrdersWithPages(User user, int curNum) throws Exception {
		//创建pageModel对象 ，目的 计算并携带分页参数
		int totalRecords = orderdao.getTotalRecords(user);
		PageModel pm = new PageModel(curNum, totalRecords, 3);
		
		//关联集合
		List list = orderdao.findMyOrdersWithPages(user,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		
		//关联url
		pm.setUrl("OrderServlet?method=findOrdersByUidWithPage");
		return pm;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		
		return orderdao.findOrderByOid(oid);
	}

}

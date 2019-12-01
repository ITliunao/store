package cn.itcast.store.web.servlet;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Cart;
import cn.itcast.store.domain.CartItem;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.service.serviceImp.OrderServiceImp;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;



public class OrderServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	// 保存订单
	public String saveOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		//确定登入状态
		User user =(User)req.getSession().getAttribute("loginUser");
		if(null == user){
			
			req.setAttribute("msg", "请先登入");
			return "/jsp/info.jsp";
		}
		//获取购物车
		Cart cart = (Cart)req.getSession().getAttribute("cart");
		if(null == cart){
			
			req.setAttribute("msg", "请先登入");
			return "/jsp/info.jsp";
		}
		 
		//创建订单对象,为订单对象赋值
		Order order = new Order();
		order.setOid(UUIDUtils.getCode());
		order.setOrderTime(new Date());
		order.setTotal(cart.getTotal());
		order.setState(1);
		order.setUser(user);
		
		//遍历购物车的购物项,创建订单项
		for (CartItem cartitem : cart.getCartItems()) {
			
			OrderItem orderitem = new OrderItem();
			orderitem.setItemid(UUIDUtils.getCode());
			orderitem.setQuantity(cartitem.getNums());;
			orderitem.setTotal(cartitem.getTotal());;
			orderitem.setProduct(cartitem.getProduct());;
			orderitem.setOrder(order);;

			order.getList().add(orderitem);
			
		}
		System.out.println(order.getState());
		
		//调用业务层功能:保存订单
		OrderService service = new  OrderServiceImp();
		service.saveOrder(order);
		
		//清空购物车
		cart.clearCart();
		
		//将订单放到 request中
		req.setAttribute("order", order);
		
		//转发到 /jsp/order_info.jsp
		return "/jsp/order_info.jsp";
	}
	
	
	public String findOrdersByUidWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	
//		 获取用户信息
		User user = (User)req.getSession().getAttribute("loginUser");
		
//	       获取当前页
		int curNum = Integer.parseInt(req.getParameter("num"));
		
//	       调用业务层功能:查询当前用户订单信息,返回PageModel
		OrderService orderservice = new OrderServiceImp();
		PageModel pm = orderservice.findMyOrdersWithPages(user , curNum);
		  
//	       将PageModel放入request
		req.setAttribute("page", pm);
		
//	       转发到/jsp/order_list.jsp
		return "/jsp/order_list.jsp";
	}	
	
	public String findOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取到oid
		String oid = req.getParameter("oid");
		
		//调用业务层,查询订单的详细信息
		OrderService orderservlet = new OrderServiceImp();
		Order order = orderservlet.findOrderByOid(oid);
		
		//存放到request中
		req.setAttribute("order", order);
		
		//转发到 jsp/order_info.jsp
		return "jsp/order_info.jsp";
	}
}
 
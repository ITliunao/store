package cn.itcast.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Cart;
import cn.itcast.store.domain.CartItem;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;


public class CartServlet extends BaseServlet {

	//添加购物项 到购物车
	public String addCartItemToCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		System.out.println("qingqiudaole ");
		//从session里获取购物车
		Cart cart = (Cart)req.getSession().getAttribute("cart");
		
		if(null == cart){
			//没有购物车
			cart= new Cart();
			req.getSession().setAttribute("cart", cart);
			
		}
		
		//有购物车
		//获取参数
		String pid = req.getParameter("pid");	
		int nums = Integer.parseInt(req.getParameter("quantity"));

		ProductService productservice = new ProductServiceImp();
		Product product = productservice.findProductByPid(pid);
		
		CartItem cartitem = new CartItem();
		cartitem.setNums(nums);
		cartitem.setProduct(product);
		

		//调用购物车的方法
		cart.addCartItemToCart(pid, cartitem);
		
		//重定向到 /store/jsp/cart.jsp
		resp.sendRedirect("/store/jsp/cart.jsp");
		
		return null;
	}

	public String removeCartItem(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取删除商品的ID
		String pid = req.getParameter("id");
		//获取购物车
		Cart cart= (Cart)req.getSession().getAttribute("cart");
		//删除商品
		cart.removeCart(pid);
		//重定向
		resp.sendRedirect("/store/jsp/cart.jsp");
		
		return null;
	}
	
	public String clearCartItem(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取购物车
		Cart cart= (Cart)req.getSession().getAttribute("cart");
		//清空购物车
		cart.clearCart();
		
		//重定向
		resp.sendRedirect("/store/jsp/cart.jsp");
		
		return null;
	}

}

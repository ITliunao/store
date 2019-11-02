package cn.itcast.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

/**
 * 
 * @author 123
 *
 */

public class IndexServlet extends BaseServlet {
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		ProductService proservice = new ProductServiceImp();
		//查询最新的商品
		List<Product> list01 = proservice.findHots();
		List<Product> list02 = proservice.findNews();
		
		System.out.println(list01);
		System.out.println(list02);
		//将查询的商品放到request中
		req.setAttribute("news", list02);
		req.setAttribute("hots", list01);
		
		//转发到真实的首页
		return "/jsp/index.jsp";
	}
}

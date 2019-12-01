package cn.itcast.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {


	public String findProductByPid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		//获取参数
		String pid = req.getParameter("pid");
		
		//调用service层的代码
		ProductService ProductService = new ProductServiceImp();
		Product product = ProductService.findProductByPid(pid);
		
		//存放到request中
		req.setAttribute("product", product);
		
		return "/jsp/product_info.jsp";
	}
	
	
	public String findProductsWithCidAndPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		//获取参数
		String cid = req.getParameter("cid");
		int curNum = Integer.parseInt(req.getParameter("num"));
		
		//调用业务层功能:以分页的形式查询当前类别下的信息
		ProductService ProductService = new ProductServiceImp();
		PageModel pm = ProductService.findProductsWithCidAndPage(cid,curNum);
		
		//吧上平放到request中
		req.getSession().setAttribute("page", pm);
		
		//转发到 /jsp/product_list.jsp
		return "/jsp/product_list.jsp";
	}
	
	
	
}

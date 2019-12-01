package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.utils.JedisUtils;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;



/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {

	//查询所有分类
	public String findAllCates(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("AllCates");
		if(null == jsonStr || "".equals(jsonStr)){
			
			System.out.println("redis中没有数据");
			//调用业务层的得到分类的方式
			CategoryService cs= new CategoryServiceImp();
			List<Category> list= cs.getAllCates();
			
			//将lsit 集合转成Json格式
			jsonStr = JSONArray.fromObject(list).toString();
			//将数据响应到客户端
			//告诉浏览器本次响应的数据类型为json
			jedis.set("AllCates", jsonStr);
			resp.setContentType("application/json;charset=utf-8");
			resp.getWriter().write(jsonStr);
		}else{
			System.out.println("redis中有数据");
			resp.setContentType("application/json;charset=utf-8");
			resp.getWriter().write(jsonStr);
		}
		
		JedisUtils.closeJedis(jedis);
		
		return null;
	}
}

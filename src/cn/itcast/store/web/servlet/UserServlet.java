package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serviceImp.UserServiceImp;
import cn.itcast.store.utils.MailUtils;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

/**
 * 

 */
public class UserServlet extends BaseServlet {

	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		return "/jsp/register.jsp";
	}
	
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		return "/jsp/login.jsp";
	}
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//使session失效
		request.getSession().invalidate();
		//重定向到首页
		response.sendRedirect("/store/index.jsp");
		
		return null;
	}
	
	
	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收表单参数
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		MyBeanUtils.populate(user, map);
		
		user.setUid(UUIDUtils.getId());
		user.setCode(UUIDUtils.getCode());
		user.setState(0);
		
		try {
			//调用业务层注册功能
			UserService userService = new UserServiceImp();
			userService.userRegist(user);
			//向邮箱发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			//用户注册成功激活
			request.setAttribute("msg", "用户注册成功请激活");
			
		} catch (Exception e) {
			//用户注册成功失败
			request.setAttribute("msg", "用户注册失败");
		}
		return "/jsp/info.jsp";
	}
	
	
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	
		//获取到激活码
		String code  = request.getParameter("code");
		
		//调用业务层的功能
		UserService service = new UserServiceImp();
	    boolean flag =  service.userActive(code);
		
		//对激活的结果进行不同的处理
	    if(flag == true){
	    	//成功
	    	request.setAttribute("msg", "用户激活成功请登入");
	    	
	    	return "/jsp/login.jsp";
	    }else{
	    	//失败
	    	request.setAttribute("msg", "用户激活失败,请重新注册");
	    	
	    	return "/jsp/info.jsp";
	    }
		
	}
	
	
	public String userlogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//得到用户名和密码
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		
		//进行校验
		UserService service = new UserServiceImp();
		User user02 = null;
		
		try {
			//进行判断用户是否存在
			user02 = service.userLogin(user); 
			
			//成功
			request.getSession().setAttribute("loginUser", user02);
			response.sendRedirect("/store/index.jsp");

			return null;
		} catch (Exception e) {
			//失败
			String msg = e.getMessage();
			request.getSession().setAttribute("msg", msg);
			
			return "/jsp/login.jsp";
			
		}
		
	}
}

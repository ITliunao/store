package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.dao.daoImp.UserDaoImp;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;

public class UserServiceImp implements UserService {

	UserDao dao = new UserDaoImp();
	@Override
	public void userRegist(User user) throws SQLException {
		
		dao.userRegist(user);
	}

	@Override
	public boolean userActive(String code)throws SQLException {

		//调用数据库进行实现查询
		User user = dao.userActive(code);
		
		if(null != user){
			//用户存在
			user.setCode(null);
			user.setState(1);
			
			dao.updateUser(user);
			
			return true;
		}else{
			//不存在用户存在
			
			return false;
		}
		
	}

	@Override
	public User userLogin(User user)  throws SQLException {

		User uu = dao.userLogin(user);
		
		if( uu == null){
			 throw new RuntimeException("用户不存在");
		}else if(uu.getState() == 0){
			
			throw new RuntimeException("用户未激活");
		}else{
			
			return uu;
		}
		
	}

}

package cn.itcast.store.dao.daoImp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;

public class UserDaoImp implements UserDao{

	@Override
	public void userRegist(User user) throws SQLException {
		String sql = "INSERT INTO USER VALUE (?,?,?,?,?,?,?,?,?,?)";
		
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		Object[] params = {user.getUid() ,user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
 		
		qr.update(sql, params);
		
	}

	@Override
	public User userActive(String code) throws SQLException {

		String sql = "SELECT * FROM USER WHERE CODE = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		return 	qr.query(sql,new BeanHandler<User>(User.class),code);
		
	}

	@Override
	public void updateUser(User user) throws SQLException {
		
		String sql="  UPDATE USER SET username= ? ,PASSWORD=? ,NAME =? ,email =? ,telephone =? , birthday =?  ,sex =? ,state= ? , CODE = ? WHERE uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		Object[] params = {user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
	
		qr.update(sql, params);
	}

	@Override
	public User userLogin(User user) throws SQLException {

		String sql ="SELECT * FROM USER WHERE USERNAME =? and PASSWORD =?"; 
		
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		return 	qr.query(sql,new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
		
	}

}

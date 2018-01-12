package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.Constant;

public class UserDaoImpl implements UserDao {
	
	@Override
	/**
	 * 
	 */
	public void regist(User user) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {
			user.getUid(),user.getUsername(),user.getPassword(),user.getName(),
			user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),
			user.getCode()
		};
		qr.update(C3P0Utils.getConnection(), sql, params);
	}

	@Override
	/**
	 * 根据激活码查找用户
	 */
	public User findUserByCode(String code) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where code = ?";
		return qr.query(sql, new BeanHandler<>(User.class),code);
	}

	@Override
	/**
	 * 根据用户id激活用户
	 */
	public void active(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update user set state = ?, code = ? where uid = ?";
		qr.update(sql, Constant.IS_ACTIVE,null,user.getUid());
	}

	@Override
	/**
	 * 用户登录
	 */
	public User login(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		return qr.query(sql, new BeanHandler<>(User.class),username,password);
	}

}

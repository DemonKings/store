package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;

public class UserDaoImpl implements UserDao {

	@Override
	public void login(User user) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {
			user.getUid(),user.getUsername(),user.getPassword(),user.getName(),
			user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),
			user.getCode()
		};
		qr.update(C3P0Utils.getConnection(), sql, params);
	}

}

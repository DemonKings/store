package com.itheima.dao;

import java.sql.SQLException;

import com.itheima.domain.User;

public interface UserDao {

	void regist(User user) throws SQLException;

	User findUserByCode(String code) throws SQLException;

	void active(User user) throws SQLException;

	User login(String username, String password) throws SQLException;

}

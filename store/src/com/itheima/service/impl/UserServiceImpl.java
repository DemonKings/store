package com.itheima.service.impl;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.Constant;
import com.itheima.utils.MailUtils;
import com.itheima.utils.UUIDUtils;

public class UserServiceImpl implements UserService {

	@Override
	/**
	 * 用户注册
	 */
	public void regist(User user) throws Exception {
		try {
			//开启事务
			C3P0Utils.startTransaction();
			//添加uid
			user.setUid(UUIDUtils.getId());
			//添加状态
			user.setState(Constant.NOT_ACTIVE);
			//添加激活码
			user.setCode(UUIDUtils.getCode());
			//调用dao
			UserDao dao = new UserDaoImpl();
			dao.regist(user);
			//发送邮件
			String mailMsg = "<a href='http://localhost/store/user?method=active&code="+user.getCode()+"'>点此激活</a>";
			MailUtils.sendMail(user.getEmail(), "用户激活", mailMsg);
			//提交事务
			C3P0Utils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			//回滚事务
			C3P0Utils.rollbackAndClose();
			//抛异常
			throw e;
		}
	}

	@Override
	/**
	 * 账号激活
	 */
	public User active(String code) throws Exception {
		//根据激活码查找用户
		UserDao dao = new UserDaoImpl();
		User user = dao.findUserByCode(code);
		//判断用户是否存在
		if(user!=null){
			//用户存在,激活用户
			dao.active(user);
		}
		return user;
	}

	@Override
	/**
	 * 用户登录
	 */
	public User login(String username, String password) throws Exception {
		//调用dao完成登录
		UserDao dao = new UserDaoImpl();
		return dao.login(username,password);
	}

}

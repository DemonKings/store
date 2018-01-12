package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;

public class UserServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取method参数
		String method = request.getParameter("method");
		//判断method参数
		if("regist".equals(method)){
			regist(request,response);
		}
	}
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取用户输入
			Map<String, String[]> map = request.getParameterMap();
			//创建User对象
			User user = new User();
			//封装用户信息
			BeanUtils.populate(user, map);
			//执行用户注册逻辑
			UserService service = new UserServiceImpl();
			service.regist(user);
			//注册成功
			request.setAttribute("msg", "注册成功,请移步邮箱激活账号~~~");
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			//注册失败
			request.setAttribute("msg", "注册失败");
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

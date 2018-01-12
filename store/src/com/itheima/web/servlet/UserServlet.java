package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.Constant;

public class UserServlet extends BaseServlet {
	
	/**
	 * 账号退出
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//删除session
		request.getSession().invalidate();
		//重定向到首页
		response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取用户名和密码
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			//调用service执行登录逻辑
			UserService service = new UserServiceImpl();
			User user = service.login(username,password);
			//判断user是否存在
			if(user!=null){
				if(user.getState()==Constant.IS_ACTIVE){
					//登录成功
					//保存用户
					request.getSession().setAttribute("user", user);
					//重定向到首页
					response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
				}else{
					//账号未激活
					request.setAttribute("msg", "账号未激活");
					request.getRequestDispatcher("/msg.jsp").forward(request, response);
				}
			}else{
				//用户名或密码错误
				request.setAttribute("msg", "用户名或密码错误");
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "登录失败");
			request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
	}
	
	/**
	 * 账号激活
	 * @param request
	 * @param response
	 */
	public void active(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取激活码
			String code = request.getParameter("code");
			//执行service激活逻辑
			UserService service = new UserServiceImpl();
			User user = service.active(code);
			//判断用户是否存在
			if(user==null){
				//激活失败
				request.setAttribute("msg", "激活失败");
				request.getRequestDispatcher("/msg.jsp").forward(request, response);
			}else{
				//激活成功
				request.setAttribute("msg", "激活成功");
				request.getRequestDispatcher("/msg.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}

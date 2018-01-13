package com.itheima.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
		
		//获取所有cookie
		Cookie[] cookies = request.getCookies();
		//遍历
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				//将usernameAndPwd这个cookie删除
				if("usernameAndPwd".equals(cookie.getName())){
					cookie.setMaxAge(0);
					cookie.setPath(request.getContextPath()+"/");
					response.addCookie(cookie);
				}
			}
		}
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
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取用户名和密码
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			//获取用户输入的验证码
			String inputCode = request.getParameter("inputCode");
			//获取session中的验证码字符串
			String randomCode = (String) request.getSession().getAttribute("randomCode");
			//判断用户名是否输入
			if(username==null||username.trim().length()<=0){
				request.setAttribute("msg", "请输入用户名");
				return "/jsp/login.jsp";
			}
			//判断密码是否输入
			if(password==null||password.trim().length()<=0){
				request.setAttribute("msg", "请输入密码");
				return "/jsp/login.jsp";
			}
			//判断验证码是否输入
			if(inputCode==null||inputCode.trim().length()<=0){
				request.setAttribute("msg", "请输入验证码");
				return "/jsp/login.jsp";
			}
			//判断验证码是否正确
			if(!inputCode.equalsIgnoreCase(randomCode)){
				request.setAttribute("msg", "验证码错误");
				return "/jsp/login.jsp";
			}
			
			//调用service执行登录逻辑
			UserService service = new UserServiceImpl();
			User user = service.login(username,password);
			//判断user是否存在
			if(user!=null){
				if(user.getState()==Constant.IS_ACTIVE){
					//登录成功
					
					//判断是否勾选自动登录
					String auto = request.getParameter("auto");
					//如果勾选
					if(auto!=null&&"ok".equals(auto)){
						//将username和password拼接成字符串
						String usernameAndPwd = username+","+password;
						//创建usernameAndPwd这个cookie
						Cookie cookie = new Cookie("usernameAndPwd", usernameAndPwd);
						//设置超时时间为7天
						cookie.setMaxAge(7*24*3600);
						//设置有效路径
						cookie.setPath(request.getContextPath()+"/");
						//添加cookie
						response.addCookie(cookie);
					}
					
					//判断是否勾选记住用户名
					String remember = request.getParameter("remember");
					//如果勾选
					if(remember!=null&&"ok".equals(remember)){
						//创建rememberName这个cookie
						Cookie cookie = new Cookie("rememberName", username);
						//设置超时时间为7天
						cookie.setMaxAge(7*24*3600);
						//设置有效路径
						cookie.setPath(request.getContextPath()+"/");
						//添加cookie
						response.addCookie(cookie);
					}else{
						//不勾选
						Cookie[] cookies = request.getCookies();
						//遍历所有cookie,找到rememberName
						if(cookies!=null&&cookies.length>0){
							for (Cookie cookie : cookies) {
								if("rememberName".equals(cookie.getName())){
									cookie.setMaxAge(0);
									cookie.setPath(request.getContextPath()+"/");
									response.addCookie(cookie);
									break;
								}
							}
						}
					}
					
					//保存用户
					request.getSession().setAttribute("user", user);
					//重定向到首页
					response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
					return null;
				}else{
					//账号未激活
					request.setAttribute("msg", "账号未激活");
					//request.getRequestDispatcher("/msg.jsp").forward(request, response);
				}
			}else{
				//用户名或密码错误
				request.setAttribute("msg", "用户名或密码错误");
				//request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "登录失败");
			//request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
		return "/jsp/login.jsp";
	}
	
	/**
	 * 账号激活
	 * @param request
	 * @param response
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) {
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
				//request.getRequestDispatcher("/msg.jsp").forward(request, response);
			}else{
				//激活成功
				request.setAttribute("msg", "激活成功");
				//request.getRequestDispatcher("/msg.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//激活失败
			request.setAttribute("msg", "激活失败");
			//request.getRequestDispatcher("/msg.jsp").forward(request, response);
		}
		//3秒后跳转到主页
		response.setHeader("refresh", "3;url='"+request.getContextPath()+"/jsp/index.jsp'");
		return "/msg.jsp";
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			//request.getRequestDispatcher("/msg.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			//注册失败
			request.setAttribute("msg", "注册失败");
			//request.getRequestDispatcher("/msg.jsp").forward(request, response);
		}
		//3秒后跳转到主页
		response.setHeader("refresh", "3;url='"+request.getContextPath()+"/jsp/index.jsp'");
		return "/msg.jsp";
	}
}

package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.utils.Privilege;

public class BaseServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取method参数
		String method = request.getParameter("method");
		//反射获取子类的class文件
		Class<? extends BaseServlet> clazz = this.getClass();
		//获取所有方法
		Method[] methods = clazz.getMethods();
		//遍历所有方法
		boolean flag = false;
		for (Method m : methods) {
			if(method.equals(m.getName())){
				flag = true;
				break;
			}
		}
		//如果有与请求参数method同名的方法,则执行
		if(flag){
			try {
				Method m = clazz.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
				//判断方法是否有指定注解
//				if(m.isAnnotationPresent(Privilege.class)){
//					//判断用户是否登录
//					Object user = request.getSession().getAttribute("user");
//					if(user==null){
//						//未登录
//						request.setAttribute("msg", "权限不足,请先登录");
//						request.getRequestDispatcher("/msg.jsp").forward(request, response);
//						return;
//					}
//				}
				//执行方法,返回值为要请求转发的路径
				String path = (String) m.invoke(this, request,response);
				//判断返回值
				if(path!=null){
					request.getRequestDispatcher(path).forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

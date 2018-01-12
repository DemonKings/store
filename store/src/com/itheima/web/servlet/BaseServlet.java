package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取method参数
		String method = request.getParameter("method");
		//反射获取子类的class文件
		Class clazz = this.getClass();
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
		if(flag){
			try {
				Method m = clazz.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
				m.invoke(this, request,response);
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

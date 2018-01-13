package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.dao.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;

public class CategoryServlet extends BaseServlet {
	/**
	 * 查询所有商品分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//调用service查询逻辑
			CategoryService service = new CategoryServiceImpl();
			String json = service.findAll();
			//将json返回给浏览器
			response.getWriter().print(json);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			//查询失败
			request.setAttribute("msg", "查询所有商品分类失败");
			return "/msg.jsp";
		}
	}

}

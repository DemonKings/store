package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;

public class ProductServlet extends BaseServlet {
	/**
	 * 查询最新和热门商品,跳转到主页
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String index(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//调用service方法 查询最新和热门商品
			ProductService service = new ProductServiceImpl();
			List<Product> hotPro = service.findHotPro();
			List<Product> newPro = service.findNewPro();
			//放入request域中
			request.setAttribute("hotPro", hotPro);
			request.setAttribute("newPro", newPro);
			//请求转发到首页
			return "/jsp/index.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			//查询商品失败
			request.setAttribute("msg", "查询商品失败");
			return "/msg.jsp";
		}
	}

}

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
import com.itheima.utils.Constant;
import com.itheima.utils.PageBean;

public class ProductServlet extends BaseServlet {
	
	
	/**
	 * 分页查询导航栏分类下的商品信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//获取cid
			String cid = request.getParameter("cid");
			int pageNumber = 1;
			try {
				//获取当前页
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (Exception e) {
				e.printStackTrace();
				pageNumber = 1;
			}
			//设置每页显示的条数
			int pageSize = Constant.PRODUCT_PAGESIZE;
			//调用service层执行查询逻辑
			ProductService service = new ProductServiceImpl();
			PageBean<Product> pb = service.findByPage(cid,pageNumber,pageSize);
			//将pb放入request域中
			request.setAttribute("pb", pb);
			//将cid放入request域
			request.setAttribute("cid", cid);
			//请求转发到product_list.jsp
			return "/jsp/product_list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			//分页查询失败
			request.setAttribute("msg", "分页查询失败");
			return "/msg.jsp";
		}
	}
	
	
	/**
	 * 通过pid查询商品,请求转发到product_info展示商品详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//获取pid
			String pid = request.getParameter("pid");
			//调用service执行查询逻辑
			ProductService service = new ProductServiceImpl();
			Product pro = service.findByPid(pid);
			//将商品放入request域
			request.setAttribute("pro", pro);
			//请求转发到product_info.jsp
			return "/jsp/product_info.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			//根据pid查询商品失败
			request.setAttribute("msg", "根据pid查询商品失败");
			return "/msg.jsp";
		}
	}
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

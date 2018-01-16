package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.service.impl.OrderServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.Constant;
import com.itheima.utils.PageBean;
import com.itheima.utils.UUIDUtils;

public class OrderServlet extends BaseServlet {
	
	
	/**
	 * 分页查询订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findOrderByPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//获取当前页参数
			int pageNumber = 1;
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (Exception e) {
				pageNumber = 1;
			}
			//设置每页条数
			int pageSize = Constant.ORDER_PAGESIZE;
			//获取用户id
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			String uid = user.getUid();
			//调用service查询订单
			OrderService service = (OrderService)BeanFactory.getBean("OrderService");
			PageBean<Orders> pb = service.findOrderByPage(pageNumber,pageSize,uid);
			//将pb放入request域
			request.setAttribute("pb", pb);
			return "/jsp/order_list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查询我的订单失败");
			return "/msg.jsp";
		}
	}
	
	/**
	 * 提交订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String commitOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//判断用户是否登录
			User user = (User) request.getSession().getAttribute("user");
			if(user==null){
				request.setAttribute("msg", "请先登录!");
				return "/msg.jsp";
			}
			//创建订单对象
			Orders order = new Orders();
			//设置订单id
			order.setOid(UUIDUtils.getId());
			//设置生成时间
			order.setOrdertime(new Date());
			//获取购物车对象
			HttpSession session = request.getSession();
			Cart cart = (Cart) session.getAttribute("cart");
			//设置总金额
			order.setTotal(cart.getTotal());
			//设置订单状态
			order.setState(Constant.ORDER_WEIZHIFU);
			//设置所属用户
			order.setUser(user);
			//设置所包含的订单项
			//获取购物车中的所有购物项
			Collection<CartItem> cartItemList = cart.getCartItemList();
			for (CartItem cartItem : cartItemList) {
				//创建订单项
				OrderItem orderItem = new OrderItem();
				//设置订单项id
				orderItem.setItemid(UUIDUtils.getId());
				//设置商品数量
				orderItem.setCount(cartItem.getCount());
				//设置小计
				orderItem.setSubtotal(cartItem.getSubtotal());
				//设置商品信息
				orderItem.setProduct(cartItem.getProduct());
				//设置所属订单
				orderItem.setOrder(order);
				//将订单项添加到订单中
				List<OrderItem> listItem = order.getListItem();
				listItem.add(orderItem);
			}
			//调用service执行保存订单逻辑
			OrderService service = new OrderServiceImpl();
			service.commitOrder(order);
			//请求转发到order_info.jsp
			request.setAttribute("order", order);
			return "/jsp/order_info.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "提交订单失败");
			return "/msg.jsp";
		}
	}
}

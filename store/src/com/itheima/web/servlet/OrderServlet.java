package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
import com.itheima.utils.PaymentUtil;
import com.itheima.utils.UUIDUtils;

public class OrderServlet extends BaseServlet {
	
	/**
	 * 支付完成回调方法
	 * @param request
	 * @param response
	 * @return
	 */
	public String callback(HttpServletRequest request,HttpServletResponse response){
		try {
			String p1_MerId = request.getParameter("p1_MerId");
			String r0_Cmd = request.getParameter("r0_Cmd");
			String r1_Code = request.getParameter("r1_Code");
			String r2_TrxId = request.getParameter("r2_TrxId");
			String r3_Amt = request.getParameter("r3_Amt");
			String r4_Cur = request.getParameter("r4_Cur");
			String r5_Pid = request.getParameter("r5_Pid");
			String r6_Order = request.getParameter("r6_Order");
			String r7_Uid = request.getParameter("r7_Uid");
			String r8_MP = request.getParameter("r8_MP");
			String r9_BType = request.getParameter("r9_BType");
			String rb_BankId = request.getParameter("rb_BankId");
			String ro_BankOrderId = request.getParameter("ro_BankOrderId");
			String rp_PayDate = request.getParameter("rp_PayDate");
			String rq_CardNo = request.getParameter("rq_CardNo");
			String ru_Trxtime = request.getParameter("ru_Trxtime");
			// 身份校验 --- 判断是不是支付公司通知你
			String hmac = request.getParameter("hmac");
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
					"keyValue");
			// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
			boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
					r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
					r8_MP, r9_BType, keyValue);
			if (isValid) {
				// 响应数据有效
				if (r9_BType.equals("1")) {
					// 浏览器重定向
					System.out.println("111");
					request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
				} else if (r9_BType.equals("2")) {
					// 服务器点对点 --- 支付公司通知你
					System.out.println("付款成功！222");
					// 修改订单状态 为已付款
					// 回复支付公司
					response.getWriter().print("success");
				}
				//修改订单状态
				OrderService service=(OrderService) BeanFactory.getBean("OrderService");
				Orders order = service.findOrderByOid(r6_Order);
				order.setState(Constant.ORDER_WEIFAHUO);
				service.updateOrder(order);
			} else {
				// 数据无效
				System.out.println("数据被篡改！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/msg.jsp";
	}
	
	
	/**
	 * 支付订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String pay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//获取参数
			String oid = request.getParameter("oid");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String telephone = request.getParameter("telephone");
			String pd_FrpId = request.getParameter("pd_FrpId");
			//调用service查询该订单
			OrderService service = (OrderService)BeanFactory.getBean("OrderService");
			Orders order = service.findOrderByOid(oid);
			//更新数据
			order.setName(name);
			order.setAddress(address);
			order.setTelephone(telephone);
			//调用service将数据写回数据库
			service.updateOrder(order);
			//拼接第三方所需要的参数
			// 组织发送支付公司需要哪些数据
			String p0_Cmd = "Buy";
			String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
			String p2_Order = oid;
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
			// 第三方支付可以访问网址
			String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			// 加密hmac 需要密钥
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
					p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
					pd_FrpId, pr_NeedResponse, keyValue);
		
			
			//发送给第三方
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);
			
			//重定向到第三方网站
			response.sendRedirect(sb.toString());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "支付失败");
			return "/msg.jsp";
		}
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//获取oid
			String oid = request.getParameter("oid");
			//调用service根据订单编号查询订单
			OrderService service = (OrderService)BeanFactory.getBean("OrderService");
			Orders order = service.findOrderByOid(oid);
			//将order放入request域中
			request.setAttribute("order", order);
			return "/jsp/order_info.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查询订单详细信息失败");
			return "/msg.jsp";
		}
	}
	
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

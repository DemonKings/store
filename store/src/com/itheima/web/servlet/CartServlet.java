package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;

public class CartServlet extends BaseServlet {
	
	public String clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从session中获取购物车
		Cart cart = getCart(request);
		//清空购物车
		cart.clearCart();
		return "/jsp/cart.jsp";
	}
	
	/**
	 * 从购物车删除购物项
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String removeCartItemFromCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取pid
		String pid = request.getParameter("pid");
		//从session中获取购物车
		Cart cart = getCart(request);
		//删除该购物项
		cart.removeCartItemFromCart(pid);
		return "/jsp/cart.jsp";
	}
	
	
	/**
	 * 添加购物项到购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//获取参数
			String pid = request.getParameter("pid");
			int count = Integer.parseInt(request.getParameter("count"));
			//通过pid查询商品信息
			ProductService service = new ProductServiceImpl();
			Product product = service.findByPid(pid);
			//创建购物项对象
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCount(count);
			//从session中获取购物车
			Cart cart = getCart(request);
			//将购物项添加到购物车
			cart.addCartItemToCart(cartItem);
			return "/jsp/cart.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "添加购物车失败");
			return "/msg.jsp";
		}
	}


	public Cart getCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		//判断cart
		if(cart==null){
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}


}

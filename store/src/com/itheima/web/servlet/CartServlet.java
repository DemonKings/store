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
			//将购物项添加到购物车
			//从session中获取购物车
			HttpSession session = request.getSession();
			Cart cart = (Cart) session.getAttribute("cart");
			//判断cart
			if(cart==null){
				cart = new Cart();
				session.setAttribute("cart", cart);
			}
			cart.addCartItemToCart(cartItem);
			return "/jsp/cart.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "添加购物车失败");
			return "/msg.jsp";
		}
	}


}

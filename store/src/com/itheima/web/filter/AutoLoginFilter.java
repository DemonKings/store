package com.itheima.web.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;

/**
 * Servlet Filter implementation class AutoLoginServlet
 */
public class AutoLoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AutoLoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		//先获取session
		Object obj = req.getSession().getAttribute("user");
		//如果session中有user直接放行
		if(obj!=null){
			chain.doFilter(request, response);
			return;
		}
		//获取所有的cookie
		Cookie cookie = null;
		Cookie[] cookies = req.getCookies();
		//遍历cookie
		if(cookies!=null){
			for (Cookie c : cookies) {
				if("usernameAndPwd".equals(c.getName())){
					cookie = c;
					break;
				}
			}
		}
		//有usernameAndPwd这个cookie
		if(cookie!=null){
			try {
				String value = cookie.getValue();
				//将username和password切割开
				String username = value.split(",")[0];
				String password = value.split(",")[1];
				//调用service的login,通过username和password获取User对象
				UserService service = new UserServiceImpl();
				User user = service.login(username, password);
				if(user!=null){
					//获取成功,放入session域中
					req.getSession().setAttribute("user", user);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

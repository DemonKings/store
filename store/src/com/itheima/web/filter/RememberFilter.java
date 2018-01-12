package com.itheima.web.filter;

import java.io.IOException;
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
 * Servlet Filter implementation class RememberFilter
 */
public class RememberFilter implements Filter {

    /**
     * Default constructor. 
     */
    public RememberFilter() {
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
		//获取所有的cookie
		Cookie cookie = null;
		Cookie[] cookies = req.getCookies();
		//遍历cookie
		if(cookies!=null){
			for (Cookie c : cookies) {
				if("rememberName".equals(c.getName())){
					cookie = c;
					break;
				}
			}
		}
		//有usernameAndPwd这个cookie
		if(cookie!=null){
			String value = cookie.getValue();
			if(value!=null){
				req.getSession().setAttribute("rememberName", value);
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

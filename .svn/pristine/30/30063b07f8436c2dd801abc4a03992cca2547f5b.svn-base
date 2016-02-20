package com.xidian.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "authority", urlPatterns = { "*.htm","*.action" }, initParams = {
		@WebInitParam(name = "encoding", value = "UTF-8"),
		@WebInitParam(name = "loginPage", value = "bbk.htm"),
		@WebInitParam(name = "registerPage", value = "bbf.htm"),
		@WebInitParam(name = "indexPage", value = "mainPage.htm") })
public class AccessFilter implements Filter {
	// FilterConfig可用于访问Filter的配置信�?
	private FilterConfig config;

	// 实现初始化方�?
	public void init(FilterConfig config) {
		this.config = config;
	}

	// 实现�?毁方�?
	public void destroy() {
		this.config = null;
	}

	// 执行过滤的核心方�?
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 获取该Filter的配置参�?
		String encoding = config.getInitParameter("encoding");
		String loginPage = config.getInitParameter("loginPage");
		String indexPage = config.getInitParameter("indexPage");
		String registerPage = config.getInitParameter("registerPage");
		// 设置request编码用的字符�?
		request.setCharacterEncoding(encoding);
		// �?
		HttpServletRequest requ = (HttpServletRequest) request;
		HttpSession session = requ.getSession(true);
		// 获取客户请求的页�?
		String requestPath = requ.getServletPath();
		// 如果session范围的user为null，即表明没有登录
		// 且用户请求的既不是登录页面，也不是处理登录的页面
		if (session.getAttribute("user")==null) {
			if (requestPath.endsWith(loginPage) || requestPath.endsWith(registerPage) || 
					requestPath.contains("stu") ||requestPath.contains("query")) {
				chain.doFilter(request, response);
				System.out.println("no");
			} else {
			    ((HttpServletResponse)response).sendRedirect(null);
			}
		} else if (session.getAttribute("user") != null
				&& (requestPath.endsWith(loginPage)||requestPath.endsWith(registerPage))) {
		    ((HttpServletResponse)response).sendRedirect(indexPage);
		} else if (!requestPath.endsWith(".jsp")||requestPath.endsWith(indexPage)) {
			// "放行"请求
			chain.doFilter(request, response);
			System.out.println("no");
		}
	}
}
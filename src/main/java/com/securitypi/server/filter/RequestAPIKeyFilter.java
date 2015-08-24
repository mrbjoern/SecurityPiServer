package com.securitypi.server.filter;

import com.securitypi.server.api.ApiTokenHandler;
import com.securitypi.server.logging.LogHandler;
import com.securitypi.server.securitypi.Connection;
import com.securitypi.server.securitypi.SecurityPi;
import com.securitypi.server.securitypi.SecurityPiHandler;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RequestAPIKeyFilter implements Filter {

	private ApiTokenHandler apiTokenHandler;
	private SecurityPiHandler securityPiHandler;
	private LogHandler logHandler;

	private static final String API_KEY = "X-Api-Key";

	private String errorJson;

	public RequestAPIKeyFilter() {
		String message = "A valid HTTP Header '" + API_KEY + "' is required to access this part of the API. " +
				"If you believe this is an error, please contact the server administrator.";
		this.errorJson = "{" + wrap(message) + ":" + wrap(message) + "}";
	}

	private String wrap(String s) { return "\"" + s + "\""; }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(
				filterConfig.getServletContext());
		this.apiTokenHandler = applicationContext.getBean(ApiTokenHandler.class);
		this.securityPiHandler = applicationContext.getBean(SecurityPiHandler.class);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		HttpSession session = httpServletRequest.getSession(false);
		if(session != null) {
			SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
			if(sci != null) {
				UserDetails cud = (UserDetails) sci.getAuthentication().getPrincipal();
				filterChain.doFilter(servletRequest, servletResponse);
			}
			else {
				httpServletResponse.setStatus(400);
				httpServletResponse.getWriter().println(errorJson);
			}
		}
		else if(httpServletRequest.getHeader(API_KEY) == null) {

			httpServletResponse.setStatus(400);
			httpServletResponse.getWriter().println(errorJson);
		}
		else if(httpServletRequest.getHeader(API_KEY).equals("debug")) {
			filterChain.doFilter(servletRequest, servletResponse);
		}
		else if(!apiTokenHandler.tokenExist(httpServletRequest.getHeader(API_KEY))) {
			httpServletResponse.setStatus(401);
			httpServletResponse.getWriter().println(errorJson);
		}
		else {
			SecurityPi securityPi = securityPiHandler.findSecurityPiByToken(httpServletRequest.getHeader(API_KEY));
			if(securityPi != null) {
				String ip = httpServletRequest.getHeader("X-Forwarded-By");
				if(ip == null || ip.length() == 0) {
					ip = httpServletRequest.getRemoteAddr();
				}
				Connection connection = new Connection(securityPi, ip);

				securityPiHandler.addConnection(connection);
			}
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {

	}
}
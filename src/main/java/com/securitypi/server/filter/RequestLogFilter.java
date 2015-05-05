package com.securitypi.server.filter;

import com.securitypi.server.logging.LogHandler;
import com.securitypi.server.logging.RequestLog;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RequestLogFilter implements Filter {

	private LogHandler logHandler;
	private List<String> excludePatterns;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(
				filterConfig.getServletContext());

		this.logHandler = applicationContext.getBean(LogHandler.class);

		excludePatterns = new LinkedList<>();
		excludePatterns.add("/webjars/");
		excludePatterns.add("/css/");
		excludePatterns.add("/favicon.ico");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpSession session = httpServletRequest.getSession(false);

		SecurityContextImpl sci = null;

		if(session != null) {
			sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
		}

		if(matchExcludePatterns(httpServletRequest.getRequestURI())) {
			filterChain.doFilter(servletRequest, servletResponse);
		}
		else {
			RequestLog logEntry = new RequestLog();

			logEntry.setRequestUrl(httpServletRequest.getRequestURI());

			String requestAddress = httpServletRequest.getHeader("X-Forwarded-By");
			if(requestAddress == null || requestAddress.length() == 0) {
				requestAddress = httpServletRequest.getRemoteAddr();
			}

			logEntry.setRequestAddress(requestAddress);

			logEntry.setUserAgent(httpServletRequest.getHeader("User-Agent"));

			logEntry.setToken(httpServletRequest.getHeader("X-Api-Key"));

			if (sci != null) {
				UserDetails cud = (UserDetails) sci.getAuthentication().getPrincipal();
				logEntry.setUsername(cud.getUsername());

			}

			logHandler.addRequestLog(logEntry);

			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {

	}

	private boolean matchExcludePatterns(String requestUri) {

		boolean patternMatch = false;

		for(String pattern : excludePatterns) {

			if(pattern.length() <= requestUri.length()) {
				for(int i = 0; i < pattern.length(); i++) {
					patternMatch = true;
					if(pattern.charAt(i) != requestUri.charAt(i)) {
						patternMatch = false;
						break;
					}
				}
			}

			if(patternMatch) {
				break;
			}
		}

		return patternMatch;
	}
}

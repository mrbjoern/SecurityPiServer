package com.securitypi.server.filter;

import com.securitypi.server.api.ApiTokenHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestAPIKeyFilter implements Filter {

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

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		if(httpServletRequest.getHeader(API_KEY) == null) {
			httpServletResponse.setStatus(400);
			httpServletResponse.getWriter().println(errorJson);
		}
		else if(!ApiTokenHandler.tokenExist(httpServletRequest.getHeader(API_KEY))) {
			httpServletResponse.setStatus(401);
			httpServletResponse.getWriter().println(errorJson);
		}
		else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {

	}
}

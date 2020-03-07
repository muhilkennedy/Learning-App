package com.miniproject.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.miniproject.util.LogUtil;

/**
 * @author MuhilKennedy
 *
 */
@Component
@Order(1) // AOP used for filter order precedence.
public class SecurityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		LogUtil.getLogger(SecurityFilter.class).info("Logging Request : " + req.getRequestURI());
		// sample check
		try {
			if (request != null) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Request.");
			}
		} catch (Exception ex) {
			LogUtil.getLogger(SecurityFilter.class).error("Unknown exception in RestSecurityFilter", ex);
			throw new ServletException("Unknown exception in RestSecurityFilter", ex);
		}
		LogUtil.getLogger(SecurityFilter.class)
				.info("Logging Response : " + res.getStatus() + " | " + res.getContentType());

	}

}

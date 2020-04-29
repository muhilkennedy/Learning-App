package com.miniproject.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.miniproject.util.ConfigUtil;
import com.miniproject.util.JWTUtil;
import com.miniproject.util.LogUtil;

import io.jsonwebtoken.SignatureException;

/**
 * @author Muhil Kennedy
 * Performs additional check for user scope.
 */
@Component
@Order(2)
public class AdminFilter implements Filter {

	@Autowired
	private ConfigUtil configUtil;

	@Autowired
	private JWTUtil jwtToken;

	private static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		LogUtil.getLogger(SecurityFilter.class).info("doFilter :: Logging Request(ADMIN FILTER) : " + req.getRequestURI());
		//JWT check is done only if deployed in prod mode.
		if (configUtil.isProdDeploymentMode()) {
			try {
				if (req.getHeader(HttpHeaders.AUTHORIZATION) != null) {
					logger.info("doFilter :: validating JWT");
					if (!jwtToken.validateToken(req.getHeader(HttpHeaders.AUTHORIZATION))) {
						((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
								"Unauthorized Request");
						return;
					}
					if(!jwtToken.isAdminScope(req.getHeader(HttpHeaders.AUTHORIZATION))) {
						((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
								"Only Admins can Access this Endpoint");
						return;
					}
					chain.doFilter(request, response);
				} else {
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Authorization Header is missing.");
					return;
				}
			} catch (SignatureException ex) {
				LogUtil.getLogger(SecurityFilter.class).error("doFilter :: SignatureException in RestSecurityFilter",
						ex);
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Token does not exist");
			} catch (Exception ex) {
				LogUtil.getLogger(SecurityFilter.class).error("doFilter :: Unknown exception in RestSecurityFilter",
						ex);
				throw new ServletException("Unknown exception in RestSecurityFilter", ex);
			}
		} else {
			logger.info("doFilter :: dev mode - Skipping token verification");
			chain.doFilter(request, response);
		}
		LogUtil.getLogger(SecurityFilter.class)
				.info("doFilter :: Logging Response : " + res.getStatus() + " | " + res.getContentType());

		
	}

}

package com.ndh.interceptor;

import java.io.IOException;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ndh.rest.Memory;
import com.ndh.util.ThreadTokenHolder;

public class AuthFilter implements ContainerRequestFilter {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Memory memory;
	
	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		
		// 应用描述文件  application.wadl, 如果需要开放查看, 则在这里判断path是application.wadl, 则return
		
		String path = requestContext.getUriInfo().getPath();
		if (path.startsWith("auth") || path.startsWith("/auth")) {
			return; // 申请token时,直接返回进入rest/auth
		}
		
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
			logger.debug("Authorization header must be provided");
			throw new NotAuthorizedException("Authorization header must be provided");
		}

		String token = authorizationHeader.substring("Basic".length()).trim();
		
		try {
			
			// 验证令牌是否有效
			validateToken(token, requestContext);
			
		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private void validateToken(String token, ContainerRequestContext requestContext) throws Exception {
		if (!memory.checkLoginInfo(token)) {
			logger.debug("会话过期,请重新登录");
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("会话过期,请重新登录").build());
		} else {
			// 保存当前token，用于Controller层获取登录用户信息
			logger.debug("保存当前token，用于Controller层获取登录用户信息");
			ThreadTokenHolder.setToken(token);
		}
	}
}

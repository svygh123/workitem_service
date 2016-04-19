package com.ndh.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ndh.model.LoginUser;

@Path("/auth")
public class Auth {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Memory memory;
	
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public Response authenticateUser(@FormParam("username") String username,
									 @FormParam("password") String password) {
		try {
			boolean flag = authenticate(username, password);
			if (flag) {
				// 验证通过, 生成令牌
				LoginUser loginUser = new LoginUser(username);
				memory.saveLoginUser(loginUser);
				return Response.ok(loginUser).build();
			} else {
				logger.debug("验证不通过");
				return Response.status(Response.Status.UNAUTHORIZED).entity("{msg:'用户名或密码错误'}").build();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("没有权限访问/第三方攻击");
			return Response.status(Response.Status.FORBIDDEN).entity("没有权限访问").build();
		}
	}

	private boolean authenticate(String username, String password) throws Exception {
		if ("ndh".equals(username) && "ndh".equals(password)) {
			logger.debug("验证通过");
			return true;
		}
		return false;
	}
}

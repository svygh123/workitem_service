package com.ndh.model;

import java.io.Serializable;

public class LoginUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String token;
	
	public LoginUser(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

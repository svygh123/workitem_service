package com.ndh;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ndh.interceptor.AuthFilter;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rest/*")
public class ApplicationResourceConfig extends ResourceConfig {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ApplicationResourceConfig() {
		logger.info("ApplicationResourceConfig of Jersey is started.");

		packages("com.ndh");

		register(JacksonFeature.class);
		register(AuthFilter.class);
	}
}
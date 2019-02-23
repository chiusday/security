package com.security.utilities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app-config")
public class AppConfig {

	private String shaAlgo;

	public String getShaAlgo() {
		return shaAlgo;
	}

	public void setShaAlgo(String shaAlgo) {
		this.shaAlgo = shaAlgo;
	}
	
}

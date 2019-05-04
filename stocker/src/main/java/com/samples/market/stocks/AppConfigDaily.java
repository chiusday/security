package com.samples.market.stocks;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app-config-daily")
public class AppConfigDaily extends AppConfig {
	@Override
	public String getUrl(String symbol) {
		StringBuilder strBuilder = new StringBuilder(super.getUrl(symbol));
		return strBuilder.append("&")
			.append(this.apiKey)
			.toString();		
	}

}

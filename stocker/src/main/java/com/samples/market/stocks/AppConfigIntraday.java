package com.samples.market.stocks;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app-config-intraday")
public class AppConfigIntraday extends AppConfig {
	private String interval;
	
	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	@Override
	public String getUrl(String symbol) {
		StringBuilder strBuilder = new StringBuilder(super.getUrl(symbol));
		return strBuilder.append("&")
			.append(this.interval)
			.append("&")
			.append(this.apiKey)
			.toString();		
	}
}

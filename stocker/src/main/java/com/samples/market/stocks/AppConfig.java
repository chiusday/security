package com.samples.market.stocks;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app-config")
public class AppConfig {
	private String sourceUrl;
	private String function;
	private String symbol;
	private String apiKey;
	
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public String getUrl(String symbol) {
		return new StringBuilder(this.sourceUrl)
				.append("?")
				.append(this.function)
				.append("&")
				.append(this.symbol)
				.append(symbol)
				.append("&")
				.append(this.apiKey)
				.toString();
	}
}

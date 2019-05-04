package com.samples.market.stocks;

public class AppConfig {
	protected String sourceUrl;
	protected String function;
	protected String symbol;
	protected String apiKey;
	
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
				.toString();
	}
}

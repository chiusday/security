package com.samples.vertx.reactive;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app-config")
public class AppConfig {
	private String addressUser;
	private String addressMarketInfo;
	private String headerContentType;
	private String headerApplicationJson;
	private String hasherUrl;
	
	public String getAddressUser() {
		return addressUser;
	}
	public void setAddressUser(String addressUser) {
		this.addressUser = addressUser;
	}
	public String getAddressMarketInfo() {
		return addressMarketInfo;
	}
	public void setAddressMarketInfo(String addressMarketInfo) {
		this.addressMarketInfo = addressMarketInfo;
	}
	public String getHeaderContentType() {
		return headerContentType;
	}
	public void setHeaderContentType(String headerContentType) {
		this.headerContentType = headerContentType;
	}
	public String getHeaderApplicationJson() {
		return headerApplicationJson;
	}
	public void setHeaderApplicationJson(String headerApplicationJson) {
		this.headerApplicationJson = headerApplicationJson;
	}
	public String getHasherUrl() {
		return hasherUrl;
	}
	public void setHasherUrl(String hasherUrl) {
		this.hasherUrl = hasherUrl;
	}
}

package com.samples.market.stocks.service;

import javax.annotation.PostConstruct;

import org.springframework.web.client.RestTemplate;

import com.samples.market.stocks.AppConfig;
import com.samples.market.stocks.interfaces.DataSource;

public abstract class CloudDataSource<T extends AppConfig> implements DataSource {
	protected T appConfig;
	
	@PostConstruct
	protected abstract void setAppConfig(); 
	
	@Override
	public String getData(String symbol) {
		String url = appConfig.getUrl(symbol);
		RestTemplate rest = new RestTemplate();
		return rest.getForObject(url, String.class);
	}

}

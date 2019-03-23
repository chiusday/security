package com.samples.market.stocks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.samples.market.stocks.AppConfig;
import com.samples.market.stocks.interfaces.DataSource;

@Service
public class CloudDataSource implements DataSource {
	@Autowired
	private AppConfig appConfig;
	
	@Override
	public String getData(String symbol) {
		String url = appConfig.getUrl(symbol);
		RestTemplate rest = new RestTemplate();
		return rest.getForObject(url, String.class);
	}

}

package com.samples.vertx.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.market.model.IntradayTicker;

@Service
public class WebIntradayTickerService extends WebMarketDataService<IntradayTicker> {
	
	@Autowired
	private IntradayTickerService intrTickService;
	
	@Override
	public void setMarketDataService() {
		this.marketDataService = intrTickService;
	}
}

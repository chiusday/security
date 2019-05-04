package com.samples.vertx.reactive.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.samples.market.model.IntradayTicker;
import com.samples.market.model.IntradayTickerList;

@Service
public class IntradayRestConsumer
		extends MarketDataRestConsumer<IntradayTicker, IntradayTickerList> {
	
	@Value("${stocker.url.intraday}")
	private String url;

	@Override
	public void setup() {
		this.sourceUrl = url;
		this.lClass = IntradayTickerList.class;
	}
}

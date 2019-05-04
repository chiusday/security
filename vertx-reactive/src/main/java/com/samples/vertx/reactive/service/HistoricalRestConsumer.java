package com.samples.vertx.reactive.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.HistoricalTickerList;

@Service
public class HistoricalRestConsumer 
		extends MarketDataRestConsumer<HistoricalTicker, HistoricalTickerList> {

	@Value("${stocker.url.historical}")
	private String url;

	@Override
	public void setup() {
		this.sourceUrl = url;
		this.lClass = HistoricalTickerList.class;
	}
}

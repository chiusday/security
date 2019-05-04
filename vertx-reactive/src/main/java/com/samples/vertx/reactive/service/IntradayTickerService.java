package com.samples.vertx.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.market.model.IntradayTicker;
import com.samples.vertx.reactive.visitor.IntradayTickersVisitor;

@Service
public class IntradayTickerService extends MarketDataService<IntradayTicker> {
	@Autowired
	private IntradayTickersVisitor intrTickVisitor;

	@Override
	protected void setTickersVisitor() {
		this.tickersVisitor = intrTickVisitor;
	}

}

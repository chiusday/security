package com.samples.vertx.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.reactive.visitor.interfaces.TickersVisitor;

@Service
public class HistoricalTickerService extends MarketDataService<HistoricalTicker> {
	@Autowired
	private TickersVisitor<HistoricalTicker> historicalTickersVisitor;

	@Override
	public void setTickersVisitor() {
		this.tickersVisitor = historicalTickersVisitor;
	}
}

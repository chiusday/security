package com.samples.vertx.reactive.visitor;

import org.springframework.stereotype.Component;

import com.samples.market.model.HistoricalTicker;

@Component
public class HistoricalTickerGetResponseVisitor 
		extends MarketDataGetResponseVisitor<HistoricalTicker> {

}

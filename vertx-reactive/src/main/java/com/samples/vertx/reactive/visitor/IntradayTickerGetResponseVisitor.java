package com.samples.vertx.reactive.visitor;

import org.springframework.stereotype.Component;

import com.samples.market.model.IntradayTicker;

@Component
public class IntradayTickerGetResponseVisitor 
		extends MarketDataGetResponseVisitor<IntradayTicker> {

}

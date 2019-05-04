package com.samples.vertx.reactive.visitor;

import org.springframework.stereotype.Component;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.reactive.visitor.interfaces.AddRxResponseVisitor;

@Component
public class HistoricalTickerAddResponseVisitor
		extends AddRxResponseVisitor<HistoricalTicker> {

	
}

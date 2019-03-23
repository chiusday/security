package com.samples.vertx.reactive.visitor;

import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.reactive.visitor.interfaces.AddRxResponseVisitor;

@Service
public class HistoricalTickerAddResponseVisitor
		extends AddRxResponseVisitor<HistoricalTicker> {

	
}

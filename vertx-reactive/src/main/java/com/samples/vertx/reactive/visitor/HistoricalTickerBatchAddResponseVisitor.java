package com.samples.vertx.reactive.visitor;

import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.reactive.visitor.interfaces.BatchAddRxResponseVisitor;

@Service
public class HistoricalTickerBatchAddResponseVisitor 
		extends BatchAddRxResponseVisitor<HistoricalTicker>{

}

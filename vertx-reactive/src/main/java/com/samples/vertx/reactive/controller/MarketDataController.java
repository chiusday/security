package com.samples.vertx.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.reactive.service.MarketDataService;
import com.samples.vertx.reactive.visitor.interfaces.IRxResponseVisitor;
import com.samples.vertx.reactive.visitor.model.RxResponse;

@RestController()
public class MarketDataController {
	@Autowired
	private MarketDataService marketDataService;
	
	@Autowired
	private IRxResponseVisitor<HistoricalTicker> marketDataGetResponseVisitor;
	
	@Autowired
	private IRxResponseVisitor<HistoricalTicker> marketDataAddResponseVisitor;

	//Post is used so this can be secured via spring oauth2
	@PostMapping("/market-data")
	public ResponseEntity<Object> addMarketData(@RequestBody HistoricalTicker ticker){
		RxResponse<HistoricalTicker> marketDataResponse = marketDataService.addMarketData(ticker);
		marketDataResponse.accept(marketDataAddResponseVisitor);
		
		return marketDataResponse.getResponseEntity();
	}

	//Post is used so this can be secured via spring oauth2
	@PostMapping("/market-data/get/{id}")
	public ResponseEntity<Object> getMarketData(@PathVariable String id){
		RxResponse<HistoricalTicker> marketDataResponse = marketDataService.getMarketData(id);
		marketDataResponse.accept(marketDataGetResponseVisitor);
		
		return marketDataResponse.getResponseEntity();
	}
}

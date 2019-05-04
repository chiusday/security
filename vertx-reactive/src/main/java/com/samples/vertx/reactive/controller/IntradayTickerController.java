package com.samples.vertx.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.common.exception.model.dto.DataNotFoundException;
import com.samples.market.model.IntradayTicker;
import com.samples.market.model.TickerRequestBySymbol;
import com.samples.vertx.reactive.interfaces.MarketdataAPIConsumer;
import com.samples.vertx.reactive.service.MarketDataService;
import com.samples.vertx.reactive.service.WebIntradayTickerService;
import com.samples.vertx.reactive.visitor.MarketDataAddResponseVisitor;
import com.samples.vertx.reactive.visitor.MarketDataGetResponseVisitor;
import com.samples.vertx.reactive.visitor.model.RxResponse;

@RestController
public class IntradayTickerController {
	@Autowired
	private MarketDataService<IntradayTicker> marketDataService;
	
	@Autowired
	private MarketDataAddResponseVisitor<IntradayTicker> addResponseVisitor;
	
	@Autowired
	private MarketDataGetResponseVisitor<IntradayTicker> getResponseVisitor;
	
	@Autowired 
	private WebIntradayTickerService webMarketDataService;
	
	@Autowired
	private MarketdataAPIConsumer<IntradayTicker> webConsumer;

	@PostMapping("/market-data/intraday/add")
	public ResponseEntity<Object> addIntradayTicker
			(@RequestBody IntradayTicker ticker) {
		
		RxResponse<IntradayTicker> marketDataResponse = 
				marketDataService.addMarketData(ticker);
		marketDataResponse.accept(addResponseVisitor);
		
		return marketDataResponse.getResponseEntity();
	}
	
	@PostMapping("market-data/intraday")
	public ResponseEntity<Object> getIntradayTicker
			(@RequestBody TickerRequestBySymbol request) {
		
		RxResponse<IntradayTicker> marketDataResponse = marketDataService
				.getMarketData(request.getSymbol(), IntradayTicker.class);
		
		try {
			marketDataResponse.accept(getResponseVisitor);
		} catch (DataNotFoundException dnfEx) {
			return webMarketDataService.postWebMarketDataAsEntity
					(request.getSymbol(), webConsumer);
		}
		
		return marketDataResponse.getResponseEntity();
	}
}

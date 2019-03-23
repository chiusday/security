package com.samples.vertx.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.common.exception.model.dto.DataNotFoundException;
import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.TickerRequestBySymbol;
import com.samples.vertx.reactive.service.MarketDataService;
import com.samples.vertx.reactive.service.RestHistoricalTickerConsumer;
import com.samples.vertx.reactive.service.WebMarketDataService;
import com.samples.vertx.reactive.visitor.HistoricalTickerAddResponseVisitor;
import com.samples.vertx.reactive.visitor.MarketDataGetResponseVisitor;
import com.samples.vertx.reactive.visitor.model.RxResponse;

@RestController()
public class HistoricalTickerController {
	@Autowired
	private MarketDataService<HistoricalTicker> marketDataService;
	
	@Autowired
	private MarketDataGetResponseVisitor<HistoricalTicker> getResponseVisitor;
	
	@Autowired
	private HistoricalTickerAddResponseVisitor addResponseVisitor;
	
	@Autowired
	private WebMarketDataService<HistoricalTicker> webMarketDataService;
	
	@Autowired
	private RestHistoricalTickerConsumer webConsumer;

	@PostMapping("/market-data/historical")
	public ResponseEntity<Object> addHistoricalTicjer
			(@RequestBody HistoricalTicker ticker){
		
		RxResponse<HistoricalTicker> marketDataResponse = marketDataService.addMarketData(ticker);
		marketDataResponse.accept(addResponseVisitor);
		
		return marketDataResponse.getResponseEntity();
	}

	//Post is used so this can be secured via spring oauth2
	@PostMapping("/market-data/historical/get")
	public ResponseEntity<Object> getHistoricalTicker
			(@RequestBody TickerRequestBySymbol request){
		
		RxResponse<HistoricalTicker> marketDataResponse = marketDataService
				.getMarketData(request.getSymbol(), HistoricalTicker.class);
		try {
			marketDataResponse.accept(getResponseVisitor);
		} catch (DataNotFoundException dnfEx) {
			return webMarketDataService.getWebMarketDataAsEntity(
					request.getSymbol(), webConsumer);
		}
		
		return marketDataResponse.getResponseEntity();
	}
}

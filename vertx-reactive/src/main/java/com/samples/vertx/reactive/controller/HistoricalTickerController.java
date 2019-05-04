package com.samples.vertx.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.common.exception.model.dto.DataNotFoundException;
import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.TickerRequestBySymbol;
import com.samples.vertx.reactive.interfaces.MarketdataAPIConsumer;
import com.samples.vertx.reactive.service.HistoricalTickerService;
import com.samples.vertx.reactive.service.WebHistoricalTickerService;
import com.samples.vertx.reactive.visitor.MarketDataAddResponseVisitor;
import com.samples.vertx.reactive.visitor.MarketDataGetResponseVisitor;
import com.samples.vertx.reactive.visitor.model.RxResponse;

@RestController()
public class HistoricalTickerController {
	@Autowired
	private HistoricalTickerService histTickService;
	
	@Autowired
	private MarketDataAddResponseVisitor<HistoricalTicker> historicalAddVisitor;
	
	@Autowired
	private MarketDataGetResponseVisitor<HistoricalTicker> historicalGetVisitor;
	
	@Autowired 
	private WebHistoricalTickerService historicalWebSourceService;
	
	@Autowired
	private MarketdataAPIConsumer<HistoricalTicker> webConsumer;

	@PostMapping("/market-data/historical")
	public ResponseEntity<Object> addHistoricalTicker
			(@RequestBody HistoricalTicker ticker) {
		
		RxResponse<HistoricalTicker> marketDataResponse = 
				(RxResponse<HistoricalTicker>) histTickService.addMarketData(ticker);
		marketDataResponse.accept(historicalAddVisitor);
		
		return marketDataResponse.getResponseEntity();
	}
	
	@PostMapping("market-data/historical/get")
	public ResponseEntity<Object> getHistoricalTicker
			(@RequestBody TickerRequestBySymbol request) {
		
		RxResponse<HistoricalTicker> marketDataResponse = 
				(RxResponse<HistoricalTicker>) histTickService
				.getMarketData(request.getSymbol(), HistoricalTicker.class);
		
		try {
			marketDataResponse.accept(historicalGetVisitor);
		} catch (DataNotFoundException dnfEx) {
			return historicalWebSourceService.getWebMarketDataAsEntity
					(request.getSymbol(), webConsumer);
		}
		
		return marketDataResponse.getResponseEntity();
	}

}

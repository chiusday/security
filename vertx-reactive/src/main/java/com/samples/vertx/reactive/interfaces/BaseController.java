package com.samples.vertx.reactive.interfaces;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.samples.common.exception.model.dto.DataNotFoundException;
import com.samples.market.model.Ticker;
import com.samples.market.model.TickerRequestBySymbol;
import com.samples.vertx.reactive.service.MarketDataService;
import com.samples.vertx.reactive.service.WebMarketDataService;
import com.samples.vertx.reactive.visitor.MarketDataAddResponseVisitor;
import com.samples.vertx.reactive.visitor.MarketDataGetResponseVisitor;
import com.samples.vertx.reactive.visitor.model.RxResponse;

public abstract class BaseController<T extends Ticker> {
	private Class<T> type;
	protected MarketDataService<T> marketDataService;
	protected MarketDataAddResponseVisitor<T> addResponseVisitor;
	protected MarketDataGetResponseVisitor<T> getResponseVisitor;
	protected WebMarketDataService<T> webMarketDataService;
	
	@Autowired
	private MarketdataAPIConsumer<T> webConsumer;

	public BaseController(Class<T> type) {
		this.type = type;
	}
	
	@PostConstruct
	public abstract void setBeans();
	
	public ResponseEntity<Object> addIntradayTicker(@RequestBody T ticker) {

		RxResponse<T> marketDataResponse = marketDataService.addMarketData(ticker);
		marketDataResponse.accept(addResponseVisitor);
		
		return marketDataResponse.getResponseEntity();
	}
	
	public ResponseEntity<Object> getIntradayTicker
		(@RequestBody TickerRequestBySymbol request) {
	
		RxResponse<T> marketDataResponse = marketDataService
				.getMarketData(request.getSymbol(), this.type);
		
		try {
			marketDataResponse.accept(getResponseVisitor);
		} catch (DataNotFoundException dnfEx) {
			return webMarketDataService.getWebMarketDataAsEntity(request.getSymbol(), webConsumer);
		}
		
		return marketDataResponse.getResponseEntity();
	}
	
}

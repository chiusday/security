package com.samples.vertx.reactive.interfaces;

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

public abstract class TickerBaseController<T extends Ticker> {
	private Class<T> type;
	@Autowired
	protected MarketDataService<T> marketDataService;
	@Autowired
	protected MarketDataAddResponseVisitor<T> addResponseVisitor;
	@Autowired
	protected MarketDataGetResponseVisitor<T> getResponseVisitor;
	@Autowired
	protected WebMarketDataService<T> webMarketDataService;
	
	@Autowired
	private MarketdataAPIConsumer<T> webConsumer;

	public TickerBaseController(Class<T> type) {
		this.type = type;
	}
	
	public ResponseEntity<Object> addTicker(@RequestBody T ticker) {

		RxResponse<T> marketDataResponse = marketDataService.addMarketData(ticker);
		marketDataResponse.accept(addResponseVisitor);
		
		return marketDataResponse.getResponseEntity();
	}
	
	public ResponseEntity<Object> getTicker
		(@RequestBody TickerRequestBySymbol request) {
	
		RxResponse<T> marketDataResponse = marketDataService
				.getMarketData(request.getSymbol(), this.type);
		
		try {
			marketDataResponse.accept(getResponseVisitor);
		} catch (DataNotFoundException dnfEx) {
			return webMarketDataService.postWebMarketDataAsEntity
					(request.getSymbol(), webConsumer);
		}
		
		return marketDataResponse.getResponseEntity();
	}
	
}

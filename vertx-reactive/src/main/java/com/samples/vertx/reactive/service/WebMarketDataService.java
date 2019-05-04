package com.samples.vertx.reactive.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samples.market.model.Ticker;
import com.samples.vertx.reactive.interfaces.MarketdataAPIConsumer;
import com.samples.vertx.reactive.visitor.AsyncMarketDataBatchAddRxResponseVisitor;
import com.samples.vertx.reactive.visitor.model.BaseVisitorModelResp;
import com.samples.vertx.reactive.visitor.model.BatchRxResponse;

@Service
public abstract class WebMarketDataService<T extends Ticker> {
//	@Autowired
	protected MarketDataService<T> marketDataService;
	
	@Autowired
	private AsyncMarketDataBatchAddRxResponseVisitor<T> asyncBatchAddRespVisitor;
	
	@PostConstruct
	protected abstract void setMarketDataService();
	
	public ResponseEntity<Object> getWebMarketDataAsEntity(String symbol,
			MarketdataAPIConsumer<T> webConsumer) {
		
		List<T> tickers = getWebMarketDataThenAdd(symbol, webConsumer);
		return new ResponseEntity<Object>(tickers, HttpStatus.OK);
	}
	
	/***
	 * Retrieve List of Market Data from web then add to DB.
	 * @param symbol - Ticker symbol
	 * @param webConsumer - WebMarketdataConsumer for this Ticker
	 * @return List of Tickers from web
	 */
	public List<T> getWebMarketDataThenAdd(String symbol, 
			MarketdataAPIConsumer<T> webConsumer) {
		
		List<T> tickers = webConsumer.getTickerList(symbol);
		//batch add the list to DB Asynchronously
		asyncBatchAddMarketData(tickers);
		//return the market data that corresponds to 'symbol' while a number of data are
		//being saved in the DB concurrently
		return tickers;		
	}
	
	public ResponseEntity<Object> postWebMarketDataAsEntity(String symbol,
			MarketdataAPIConsumer<T> webConsumer) {
		
		List<T> tickers = postMarketDataThenAdd(symbol, webConsumer);
		return new ResponseEntity<Object>(tickers, HttpStatus.OK);
	}
	
	public List<T> postMarketDataThenAdd(String symbol, 
			MarketdataAPIConsumer<T> webConsumer) {
		
		List<T> tickers = webConsumer.postForTickerList(symbol);
		//batch add the list to DB Asynchronously
		asyncBatchAddMarketData(tickers);
		//return the market data that corresponds to 'symbol' while a number of data are
		//being saved in the DB concurrently
		return tickers;		
	}
	
	private BaseVisitorModelResp<T> asyncBatchAddMarketData(List<T> tickers){
		BatchRxResponse<T> response = marketDataService.batchAddMarketData(tickers);
		response.accept(asyncBatchAddRespVisitor);
		
		return response;
	}
}

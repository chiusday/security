package com.samples.vertx.reactive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.reactive.interfaces.MarketdataAPIConsumer;

@Service
public class WebHistoricalTickerService 
		extends WebMarketDataService<HistoricalTicker> {
	
	@Autowired
	private HistoricalTickerService histTickService;
	
	@Override
	public void setMarketDataService() {
		this.marketDataService = histTickService;
	}

	public ResponseEntity<Object> getWebMarketDataAsEntity(String symbol, String priceDate,
			MarketdataAPIConsumer<HistoricalTicker> webConsumer) {
		
		List<HistoricalTicker> tickers = getWebMarketDataThenAdd(symbol, webConsumer);
		HistoricalTicker historicalTikcer = tickers.stream().filter(ticker -> 
			((HistoricalTicker)ticker).getPriceDate().equals(priceDate))
				.findFirst().get();
		
		return new ResponseEntity<Object>(historicalTikcer, HttpStatus.OK);
	}
}

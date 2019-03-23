package com.samples.vertx.reactive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.samples.market.model.GetBySymbolRequest;
import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.HistoricalTickerList;
import com.samples.vertx.reactive.interfaces.MarketdataAPIConsumer;

@Service
public class RestHistoricalTickerConsumer implements MarketdataAPIConsumer<HistoricalTicker> {

	@Value("${stocker.url}")
	private String sourceUrl;
	
	@Override
	public List<HistoricalTicker> getTickerList(String symbol) {
		String url = sourceUrl + symbol;
		RestTemplate restTemplate = new RestTemplate();
		HistoricalTickerList tickers = 
				restTemplate.getForObject(url, HistoricalTickerList.class, symbol);
		
		return tickers;
	}
	
	@Override
	public List<HistoricalTicker> postForTickerList(String symbol) {
		RestTemplate restTemplate = new RestTemplate();
		GetBySymbolRequest request = new GetBySymbolRequest();
		request.setSymbol(symbol);
		HistoricalTickerList tickers = 
				restTemplate.postForObject(sourceUrl, request, HistoricalTickerList.class);
		
		return tickers;
	}
}

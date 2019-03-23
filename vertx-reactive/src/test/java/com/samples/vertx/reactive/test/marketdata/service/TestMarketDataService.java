package com.samples.vertx.reactive.test.marketdata.service;

import static com.samples.vertx.reactive.test.helper.DataBuilder.createHistoricalQuote;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.reactive.service.MarketDataService;
import com.samples.vertx.reactive.visitor.model.RxResponse;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class TestMarketDataService {

	@Autowired
	private MarketDataService<HistoricalTicker> historicalMarketDataService;
	
	@Test
	public void testAddHistoricalTicker() {
		HistoricalTicker ticker = createHistoricalQuote();
		RxResponse<HistoricalTicker> response = 
				historicalMarketDataService.addMarketData(ticker);
		
		Assert.assertNotNull(response.getSingle());
	}
	
	@Test
	public void testGetHistoricalQuote() {
		RxResponse<HistoricalTicker> response = historicalMarketDataService
				.getMarketData("AAPL", HistoricalTicker.class);
		
		Assert.assertNotNull(response.getSingle());
	}
}

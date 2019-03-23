package com.samples.vertx.reactive.test.marketdata.controller;

import static com.samples.vertx.reactive.test.helper.DataBuilder.createHistoricalQuote;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.samples.market.model.HistoricalTicker;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
public class TestMarketDataController {
	private RestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@Before
	public void setup() {
		restTemplate = new RestTemplate();
	}
	
	@Test
	public void testAddHistoricalTicker() {
		String url = String.format
				("http://localhost:%s/vertxrx/market-data/historical", port);
		HistoricalTicker ticker = createHistoricalQuote();
		ResponseEntity<HistoricalTicker> response = restTemplate
				.postForEntity(url, ticker, HistoricalTicker.class);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assert.assertEquals(ticker.getSymbol(), response.getBody().getSymbol());
	}
	
//	@Test
//	public void testGetHistoricalQuote() {
//		String symbol = "AAPL";
//		String url = String.format
//				("http://localhost:%s/vertxrx/market-data/historical/%s", port, symbol);
//		ResponseEntity<HistoricalTicker> response = restTemplate
//				.getForEntity(url, HistoricalTicker.class);
//		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//		HistoricalTicker ticker = response.getBody();
//		Assert.assertEquals(symbol, ticker.getSymbol());
//	}
}

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
import com.samples.market.model.HistoricalTickerList;
import com.samples.market.model.IntradayTicker;
import com.samples.market.model.IntradayTickerList;
import com.samples.market.model.TickerRequestBySymbol;

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
				("http://localhost:%s/vertxrx/market-data/historical/add", port);
		HistoricalTicker ticker = createHistoricalQuote();
		ResponseEntity<HistoricalTicker> response = restTemplate
				.postForEntity(url, ticker, HistoricalTicker.class);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assert.assertEquals(ticker.getSymbol(), response.getBody().getSymbol());
	}
	
	@Test
	public void testGetHistoricalQuote() {
		String symbol = "MSFT";
		String url = String.format
				("http://localhost:%s/vertxrx/market-data/historical", port);
		TickerRequestBySymbol request = new TickerRequestBySymbol(symbol);
		ResponseEntity<HistoricalTickerList> response = restTemplate
				.postForEntity(url, request, HistoricalTickerList.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		HistoricalTickerList tickers = response.getBody();
		Assert.assertFalse(tickers.isEmpty());
	}

	@Test
	public void testAddIntradayTicker() {
		String url = String.format
				("http://localhost:%s/vertxrx/market-data/intraday/add", port);
		HistoricalTicker ticker = createHistoricalQuote();
		ResponseEntity<IntradayTicker> response = restTemplate
				.postForEntity(url, ticker, IntradayTicker.class);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assert.assertEquals(ticker.getSymbol(), response.getBody().getSymbol());
	}
	
	@Test
	public void testGetIntradayQuote() {
		String symbol = "MSFT";
		String url = String.format
				("http://localhost:%s/vertxrx/market-data/intraday", port);
		TickerRequestBySymbol request = new TickerRequestBySymbol(symbol);
		ResponseEntity<IntradayTickerList> response = restTemplate
				.postForEntity(url, request, IntradayTickerList.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		IntradayTickerList tickers = response.getBody();
		Assert.assertFalse(tickers.isEmpty());
	}
}

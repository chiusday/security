package com.samples.vertx.reactive.test.marketdata.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.IntradayTicker;
import com.samples.vertx.reactive.service.HistoricalRestConsumer;
import com.samples.vertx.reactive.service.IntradayRestConsumer;
import com.samples.vertx.reactive.service.WebHistoricalTickerService;
import com.samples.vertx.reactive.service.WebIntradayTickerService;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class TestWebMarketDataService {

	@Autowired
	private WebHistoricalTickerService webHistoricalTickerSource;
	
	@Autowired 
	private WebIntradayTickerService webIntradayTickerService;
	
	@Autowired
	private HistoricalRestConsumer historicalRestConsumer;
	
	@Autowired
	private IntradayRestConsumer intradayRestConsumer;
	
	private String symbol = "MSFT";
	
	@Test
	public void testGetWebHistoricalTicker() {
		List<HistoricalTicker> tickers = webHistoricalTickerSource
				.postMarketDataThenAdd(symbol, historicalRestConsumer);
		Assert.assertFalse(tickers.isEmpty());
	}
	
	@Test
	public void testPostWebIntradayTicker() {
		List<IntradayTicker> tickers = webIntradayTickerService
				.postMarketDataThenAdd(symbol, intradayRestConsumer);
		Assert.assertFalse(tickers.isEmpty());
	}
}

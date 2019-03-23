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
import com.samples.vertx.reactive.service.RestHistoricalTickerConsumer;
import com.samples.vertx.reactive.service.WebMarketDataService;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class TestWebMarketDataService {

	@Autowired
	private WebMarketDataService<HistoricalTicker> webHistoricalTickerSource;
	
	@Autowired
	private RestHistoricalTickerConsumer webConsumer;
	
	private String symbol = "MSFT";
	
	@Test
	public void testGetWebMarketData() {
		List<HistoricalTicker> tickers = webHistoricalTickerSource
				.getWebMarketDataThenAdd(symbol, webConsumer);
		Assert.assertFalse(tickers.isEmpty());
	}
}

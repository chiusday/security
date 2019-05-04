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

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class TestRestConsumer {
	@Autowired
	private HistoricalRestConsumer historicalRestConsumer;
	
	@Autowired
	private IntradayRestConsumer IntradayRestConsumer;
	
	@Test
	public void TestPostForHistoricalTickerList() {
		List<HistoricalTicker> tickers = historicalRestConsumer.postForTickerList("MSFT");
		Assert.assertFalse(tickers.isEmpty());
	}

	@Test
	public void TestPostForIntradayTickerList() {
		List<IntradayTicker> tickers = IntradayRestConsumer.postForTickerList("MSFT");
		Assert.assertFalse(tickers.isEmpty());
	}
}

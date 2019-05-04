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

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class TestRestHistoricalTickerConsumer {
	@Autowired
	private RestHistoricalTickerConsumer webConsumer;
	
	@Test
	public void TestPostForTickerList() {
		List<HistoricalTicker> tickers = webConsumer.postForTickerList("MSFT");
		Assert.assertFalse(tickers.isEmpty());
	}
}

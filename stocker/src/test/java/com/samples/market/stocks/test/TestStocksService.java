package com.samples.market.stocks.test;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.service.HistoricalTickerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStocksService {
	@Autowired
	private HistoricalTickerService stocksService;
	
	@Test
	public void testGetHistoricalTicker() {
		List<HistoricalTicker> tickers = stocksService.getHistoricalQuote("MSFT");
		assertFalse(tickers.isEmpty());
	}
}

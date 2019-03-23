package com.samples.market.stocks.test.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.model.HistoricalTickerList;
import com.samples.market.stocks.Statics;
import com.samples.market.stocks.converter.AlphaVantageJsonToHistoricalTicker;
import com.samples.market.stocks.interfaces.DataSource;
import com.samples.market.stocks.model.AlphaVantageHistoricalTicker;

import io.vertx.core.json.JsonObject;
import org.junit.Assert;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestConverter {
	@Autowired
	private Statics statics;

	@Autowired
	private DataSource cloudDataSource;

	@Autowired
	private AlphaVantageJsonToHistoricalTicker converter;
	
	private String symbol = "MSFT";

	@Test
	public void TestHistoricalTickerListConverter() {
		String data = cloudDataSource.getData(symbol);
		JsonObject rawData = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		
		AlphaVantageHistoricalTicker jsonQuote = new AlphaVantageHistoricalTicker(symbol, rawData);
		HistoricalTickerList tickerList = converter.convertFrom(jsonQuote);
		
		Assert.assertEquals(symbol, tickerList.getSymbol());
		Assert.assertFalse(tickerList.isEmpty());
	}
}

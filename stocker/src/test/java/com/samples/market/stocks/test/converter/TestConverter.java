package com.samples.market.stocks.test.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.model.HistoricalTickerList;
import com.samples.market.model.IntradayTickerList;
import com.samples.market.stocks.Statics;
import com.samples.market.stocks.converter.AlphaVantageToHistoricalTickerList;
import com.samples.market.stocks.converter.AlphaVantageToIntradayTickerList;
import com.samples.market.stocks.model.AlphaVantageHistoricalTicker;
import com.samples.market.stocks.model.AlphaVantageIntradayTicker;
import com.samples.market.stocks.service.HistoricalCloudData;
import com.samples.market.stocks.service.IntradayCloudData;

import io.vertx.core.json.JsonObject;
import org.junit.Assert;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestConverter {
	@Autowired
	private Statics statics;

	@Autowired
	private HistoricalCloudData historicalCloudSource;
	
	@Autowired
	private IntradayCloudData intradayCloudSource;

	@Autowired
	private AlphaVantageToHistoricalTickerList historicalConverter;
	
	@Autowired
	private AlphaVantageToIntradayTickerList intradayConverter;
	
	private String symbol = "MSFT";

	@Test
	public void TestHistoricalTickerListConverter() {
		String data = historicalCloudSource.getData(symbol);
		JsonObject rawData = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		
		AlphaVantageHistoricalTicker jsonQuote = new AlphaVantageHistoricalTicker(symbol, rawData);
		HistoricalTickerList tickerList = historicalConverter.convertFrom(jsonQuote);
		
		Assert.assertEquals(symbol, tickerList.getSymbol());
		Assert.assertFalse(tickerList.isEmpty());
	}

	@Test
	public void TestInradayTickerListConverter() {
		String data = intradayCloudSource.getData(symbol);
		JsonObject rawData = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getIntraday());
		
		AlphaVantageIntradayTicker jsonQuote = new AlphaVantageIntradayTicker(symbol, rawData);
		IntradayTickerList tickerList = intradayConverter.convertFrom(jsonQuote);
		
		Assert.assertEquals(symbol, tickerList.getSymbol());
		Assert.assertFalse(tickerList.isEmpty());
	}
}

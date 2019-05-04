package com.samples.market.stocks.test.visitor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.HistoricalTickerList;
import com.samples.market.model.IntradayTicker;
import com.samples.market.model.IntradayTickerList;
import com.samples.market.stocks.Statics;
import com.samples.market.stocks.model.AlphaVantageHistoricalTicker;
import com.samples.market.stocks.model.AlphaVantageIntradayTicker;
import com.samples.market.stocks.service.HistoricalCloudData;
import com.samples.market.stocks.service.IntradayCloudData;
import com.samples.market.stocks.visitor.HistoricalTickerListVisitor;
import com.samples.market.stocks.visitor.IntradayTickerListVisitor;
import com.samples.market.stocks.visitor.interfaces.ConvertibleJsonTicker;
import com.samples.market.stocks.visitor.model.HistoricalTickerListVisitorModel;
import com.samples.market.stocks.visitor.model.IntradayTickerListVisitorModel;

import io.vertx.core.json.JsonObject;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestVisitor {
	@Autowired
	private Statics statics;

	@Autowired
	private HistoricalCloudData historicalCloudSource;
	@Autowired
	private IntradayCloudData intradayCloudSource;
	
	@Autowired
	private HistoricalTickerListVisitor historicalListVisitor;
	@Autowired
	private IntradayTickerListVisitor intradayListVisitor;
	
	private String symbol = "MSFT";

	@Test
	public void TestHistoricalTickerListVisitor() {
		String data = historicalCloudSource.getData(symbol);
		JsonObject raw = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		ConvertibleJsonTicker<HistoricalTicker> jsonQuote = 
				new AlphaVantageHistoricalTicker(symbol, raw);
		HistoricalTickerListVisitorModel historicalTickerVisitorModel = 
				new HistoricalTickerListVisitorModel();
		historicalTickerVisitorModel.setConvertibleJsonTicker(jsonQuote);
		historicalTickerVisitorModel.accept(historicalListVisitor);
		HistoricalTickerList tickerList = (HistoricalTickerList) 
				historicalTickerVisitorModel.getResponseEntity().getBody();		
		
		
		Assert.assertFalse(historicalTickerVisitorModel.isHasError());
		Assert.assertFalse(tickerList.isEmpty());
	}
	
	@Test
	public void TestIntradayTickerListVisitor() {
		String data = intradayCloudSource.getData(symbol);
		JsonObject raw = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getIntraday());
		ConvertibleJsonTicker<IntradayTicker> jsonQuote = 
				new AlphaVantageIntradayTicker(symbol, raw);
		IntradayTickerListVisitorModel intradayTickerListVisitorModel = 
				new IntradayTickerListVisitorModel();
		intradayTickerListVisitorModel.setConvertibleJsonTicker(jsonQuote);
		intradayTickerListVisitorModel.accept(intradayListVisitor);
		IntradayTickerList tickerList = (IntradayTickerList)
				intradayTickerListVisitorModel.getResponseEntity().getBody();
		
		Assert.assertFalse(intradayTickerListVisitorModel.isHasError());
		Assert.assertFalse(tickerList.isEmpty());
	}
}

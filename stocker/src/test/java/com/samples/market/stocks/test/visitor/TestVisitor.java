package com.samples.market.stocks.test.visitor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.HistoricalTickerList;
import com.samples.market.stocks.Statics;
import com.samples.market.stocks.interfaces.DataSource;
import com.samples.market.stocks.model.AlphaVantageHistoricalTicker;
import com.samples.market.stocks.visitor.HistoricalTickerListVisitor;
import com.samples.market.stocks.visitor.interfaces.ConvertibleJsonTicker;
import com.samples.market.stocks.visitor.model.HistoricalTickerListVisitorModel;

import io.vertx.core.json.JsonObject;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestVisitor {
	@Autowired
	private Statics statics;

	@Autowired
	private DataSource cloudDataSource;
	
	@Autowired
	private HistoricalTickerListVisitor visitor;
	
	private String symbol = "MSFT";

	@Test
	public void TestHistoricalTickerListVisitor() {
		String data = cloudDataSource.getData(symbol);
		JsonObject raw = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		ConvertibleJsonTicker<HistoricalTicker> jsonQuote = new AlphaVantageHistoricalTicker(symbol, raw);
		HistoricalTickerListVisitorModel historicalTickerVisitorModel = 
				new HistoricalTickerListVisitorModel();
		historicalTickerVisitorModel.setConvertibleJsonTicker(jsonQuote);
		historicalTickerVisitorModel.accept(visitor);
		HistoricalTickerList tickerList = (HistoricalTickerList) 
				historicalTickerVisitorModel.getResponseEntity().getBody();		
		
		
		Assert.assertFalse(historicalTickerVisitorModel.isHasError());
		Assert.assertFalse(tickerList.isEmpty());
	}
}

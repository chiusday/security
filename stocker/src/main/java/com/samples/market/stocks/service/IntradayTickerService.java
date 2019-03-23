package com.samples.market.stocks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.Statics;
import com.samples.market.stocks.interfaces.DataSource;
import com.samples.market.stocks.model.AlphaVantageHistoricalTicker;
import com.samples.market.stocks.visitor.IntradayTickerListVisitor;
import com.samples.market.stocks.visitor.interfaces.ConvertibleJsonTicker;
import com.samples.market.stocks.visitor.model.IntradayTickerListVisitorModel;

import io.vertx.core.json.JsonObject;

@Service
public class IntradayTickerService {
	@Autowired
	private Statics statics;

	//online DataSource
	@Autowired
	private DataSource cloudDataSource;
	
	@Autowired
	private IntradayTickerListVisitor intradayTickerListVisitor;

	public IntradayTickerListVisitorModel getIntradayList(String id) {
		String data = cloudDataSource.getData(id);
		JsonObject raw = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		ConvertibleJsonTicker<HistoricalTicker> convertibleJsonTicker = 
				new AlphaVantageHistoricalTicker(id, raw);

		IntradayTickerListVisitorModel visitorModel = 
				new IntradayTickerListVisitorModel();
		visitorModel.setConvertibleJsonTicker(convertibleJsonTicker);
		visitorModel.accept(intradayTickerListVisitor);
		
		return visitorModel;
	}
}

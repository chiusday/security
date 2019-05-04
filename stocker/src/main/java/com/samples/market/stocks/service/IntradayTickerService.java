package com.samples.market.stocks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.market.model.IntradayTicker;
import com.samples.market.stocks.Statics;
import com.samples.market.stocks.model.AlphaVantageIntradayTicker;
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
	private IntradayCloudData cloudDataSource;
	
	@Autowired
	private IntradayTickerListVisitor intradayTickerListVisitor;

	public IntradayTickerListVisitorModel getIntradayList(String id) {
		String data = cloudDataSource.getData(id);
		JsonObject raw = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getIntraday());
		ConvertibleJsonTicker<IntradayTicker> convertibleJsonTicker = 
				new AlphaVantageIntradayTicker(id, raw);

		IntradayTickerListVisitorModel visitorModel = 
				new IntradayTickerListVisitorModel();
		visitorModel.setConvertibleJsonTicker(convertibleJsonTicker);
		visitorModel.accept(intradayTickerListVisitor);
		
		return visitorModel;
	}
}

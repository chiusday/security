package com.samples.market.stocks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.Statics;
import com.samples.market.stocks.converter.AlphaVantageToHistoricalTickerList;
import com.samples.market.stocks.model.AlphaVantageHistoricalTicker;
import com.samples.market.stocks.visitor.HistoricalTickerListVisitor;
import com.samples.market.stocks.visitor.interfaces.ConvertibleJsonTicker;
import com.samples.market.stocks.visitor.model.HistoricalTickerListVisitorModel;

import io.vertx.core.json.JsonObject;

@Service
public class HistoricalTickerService {
	@Autowired
	private Statics statics;

	//online DataSource
	@Autowired
	private HistoricalCloudData cloudDataSource;
	
	//offline DataSource
//	@Autowired
//	private DataSource staticDataSource;
	
	@Autowired
	private AlphaVantageToHistoricalTickerList converter;
	
	@Autowired
	private HistoricalTickerListVisitor visitor;

	public List<HistoricalTicker> getHistoricalQuote(String symbol) {
		String data = cloudDataSource.getData(symbol);
//		String data = staticDataSource.getData();
		JsonObject rawData = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		
		AlphaVantageHistoricalTicker convertibleJsonTicker = 
				new AlphaVantageHistoricalTicker(symbol, rawData);
		
		return converter.convertFrom(convertibleJsonTicker);
	}
	
	public HistoricalTickerListVisitorModel getHistoricalTickers(String symbol) {
		String data = cloudDataSource.getData(symbol);
		JsonObject raw = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		ConvertibleJsonTicker<HistoricalTicker> convertibleJsonTicker = 
				new AlphaVantageHistoricalTicker(symbol, raw);
		HistoricalTickerListVisitorModel hsitoricalTickerVisitorModel = 
				new HistoricalTickerListVisitorModel();
		hsitoricalTickerVisitorModel.setConvertibleJsonTicker(convertibleJsonTicker);
		hsitoricalTickerVisitorModel.accept(visitor);
		
		return hsitoricalTickerVisitorModel;
	}
		
}

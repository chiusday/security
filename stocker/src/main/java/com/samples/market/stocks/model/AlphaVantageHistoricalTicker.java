package com.samples.market.stocks.model;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.visitor.interfaces.ConvertibleJsonTicker;

import io.vertx.core.json.JsonObject;

public class AlphaVantageHistoricalTicker extends ConvertibleJsonTicker<HistoricalTicker> {

	public AlphaVantageHistoricalTicker() {
		super(HistoricalTicker.class);
		this.fields.add("open");
		this.fields.add("close");
		this.fields.add("high");
		this.fields.add("low");
	}
	
	public AlphaVantageHistoricalTicker(String symbol, JsonObject rawData) {
		this();
		this.symbol = symbol;
		this.data = rawData;
	}
}

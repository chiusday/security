package com.samples.market.stocks.model;

import com.samples.market.model.IntradayTicker;
import com.samples.market.stocks.visitor.interfaces.ConvertibleJsonTicker;

import io.vertx.core.json.JsonObject;

public class AlphaVantageIntradayTicker extends ConvertibleJsonTicker<IntradayTicker> {

	public AlphaVantageIntradayTicker() {
		super(IntradayTicker.class);
		this.fields.add("open");
		this.fields.add("close");
		this.fields.add("high");
		this.fields.add("low");
		this.fields.add("priceTime");
	}
	
	public AlphaVantageIntradayTicker(String symbol, JsonObject rawData) {
		this();
		this.symbol = symbol;
		this.data = rawData;
	}
}

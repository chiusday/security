package com.samples.vertx.reactive.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.samples.market.model.HistoricalTicker;

public class HistoricalTickerDBM extends HistoricalTicker {
	private int id;
	
	public int getId() {
		return this.id;
	}
	
	@JsonSetter("ID")
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	@JsonSetter("SYMBOL")
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	@JsonSetter("OPEN")
	public void setOpen(double open) {
		this.open = open;
	}
	
	@Override
	@JsonSetter("CLOSE")
	public void setClose(double close) {
		this.close = close;
	}
	
	@Override
	@JsonSetter("HIGH")
	public void setHigh(double high) {
		this.high = high;
	}
	
	@Override
	@JsonSetter("LOW")
	public void setLow(double low) {
		this.low = low;
	}
}

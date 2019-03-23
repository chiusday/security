package com.samples.market.stocks.visitor.interfaces;

import java.util.HashSet;
import java.util.Set;

import com.samples.market.model.Ticker;

import io.vertx.core.json.JsonObject;

public abstract class ConvertibleJsonTicker<T extends Ticker> {	
	protected Class<T> type;
	protected String symbol;
	protected JsonObject data;
	protected Set<String> fields;
	
	public ConvertibleJsonTicker(Class<T> type) {
		this.type = type;
		this.data = new JsonObject();
		this.fields = new HashSet<>();
	}
	
	public Class<T> getType() { return this.type; }
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}

	public Set<String> getFields() {
		return fields;
	}

	public void setFields(Set<String> fields) {
		this.fields = fields;
	}

}

package com.samples.vertx.reactive.visitor.model;

import java.util.List;

import com.samples.market.model.Ticker;
import com.samples.vertx.reactive.visitor.interfaces.IVisitor;
import com.samples.vertx.reactive.visitor.interfaces.IVisitorModel;

import io.vertx.core.json.JsonArray;

public class Tickers<T extends Ticker> implements IVisitorModel<T> {
	private List<T> tickers;
	private List<JsonArray> listJsonArray;
	public List<T> getTickers() {
		return tickers;
	}
	public void setTickers(List<T> tickers) {
		this.tickers = tickers;
	}
	public List<JsonArray> getListJsonArray() {
		return listJsonArray;
	}
	public void setListJsonArray(List<JsonArray> listJsonArray) {
		this.listJsonArray = listJsonArray;
	}
	
	@Override
	public void accept(IVisitor<T> visitor) {
		visitor.visit(this);
	}
}

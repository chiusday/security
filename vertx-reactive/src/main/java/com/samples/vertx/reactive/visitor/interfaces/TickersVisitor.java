package com.samples.vertx.reactive.visitor.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import com.samples.market.model.Ticker;
import com.samples.vertx.reactive.visitor.model.Tickers;

import io.vertx.core.json.JsonArray;

public abstract class TickersVisitor<T> implements IVisitor<T> {
	protected abstract <S extends Ticker> JsonArray getJsonArray(S ticker);
	
	@Override
	public <S extends Ticker> void visit(Tickers<S> tickers) {
		List<JsonArray> listJsonArray = tickers.getTickers().stream()
				.map(ticker -> getJsonArray(ticker))
				.collect(Collectors.toList());
		
		tickers.setListJsonArray(listJsonArray);
	}
}

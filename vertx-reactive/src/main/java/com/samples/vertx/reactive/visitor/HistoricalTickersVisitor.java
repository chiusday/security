package com.samples.vertx.reactive.visitor;

import org.springframework.stereotype.Component;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.Ticker;
import com.samples.vertx.reactive.visitor.interfaces.TickersVisitor;

import io.vertx.core.json.JsonArray;

@Component
public class HistoricalTickersVisitor extends TickersVisitor<HistoricalTicker> {

	@Override
	protected <T extends Ticker> JsonArray getJsonArray(T model) {
		HistoricalTicker ticker = (HistoricalTicker) model;
		return new JsonArray()
				.add(ticker.getSymbol())
				.add(ticker.getOpen())
				.add(ticker.getClose())
				.add(ticker.getHigh())
				.add(ticker.getLow())
				.add(ticker.getPriceDate());
	}
}

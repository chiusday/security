package com.samples.vertx.reactive.visitor;

import org.springframework.stereotype.Component;

import com.samples.market.model.IntradayTicker;
import com.samples.market.model.Ticker;
import com.samples.vertx.reactive.visitor.interfaces.TickersVisitor;

import io.vertx.core.json.JsonArray;

@Component
public class IntradayTickersVisitor extends TickersVisitor<IntradayTicker> {

	@Override
	protected <T extends Ticker> JsonArray getJsonArray(T model) {
		IntradayTicker ticker = (IntradayTicker) model;
		return new JsonArray()
				.add(ticker.getSymbol())
				.add(ticker.getOpen())
				.add(ticker.getClose())
				.add(ticker.getHigh())
				.add(ticker.getLow())
				.add(ticker.getPriceTime());
	}

}

package com.samples.vertx.reactive.interfaces;

import java.util.List;

import com.samples.market.model.Ticker;

public interface MarketdataAPIConsumer<T extends Ticker> {

	/***
	 * Get a Ticker from a web service based on the ticker symbol/id
	 * @param symbol - Unique ticker identifier
	 * @return The ticker that corresponds to the symbol
	 */
	default T getTicker(String symbol) {
		throw new UnsupportedOperationException("getTicker(String)");
	}
	
	/***
	 * Get Tickers from a web service based on the ticker symbol/id
	 * @param symbol - Unique ticker identifier
	 * @return List of tickers that corresponds to the symbol
	 */
	default List<T> getTickerList(String symbol) {
		throw new UnsupportedOperationException("getTickerList(String)");
	}
	
	/***
	 * Get Tickers from a web service based on the ticker symbol/id using POST method
	 * @param symbol - Unique ticker identifier
	 * @return List of tickers that corresponds to the symbol
	 */
	default List<T> postForTickerList(String symbol) {
		throw new UnsupportedOperationException("postForTickerList(String)");
	}
}

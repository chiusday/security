package com.samples.market.stocks.interfaces;

public interface DataSource {
	default String getData() {
		throw new UnsupportedOperationException("getData() is not supported.");
	}

	default String getData(String symbol) {
		throw new UnsupportedOperationException("getData(String) is not supported.");
	}
}

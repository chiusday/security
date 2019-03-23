package com.samples.vertx.reactive.test.helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.samples.market.model.HistoricalTicker;

public class DataBuilder {

	public static HistoricalTicker createHistoricalQuote() {
		return createHistoricalQuote(LocalDate.now().toString());
	}
	
	public static List<HistoricalTicker> createHistoricalQuotes() {
		return createHistoricalQuotes(600);
	}
	
	public static List<HistoricalTicker> createHistoricalQuotes(int count){
		LocalDate date = LocalDate.now();
		List<HistoricalTicker> tickers = new ArrayList<>();
		for (int i=0; i <= count; i++) {
			tickers.add(createHistoricalQuote(date.minusDays(1).toString()));
		}
		
		return tickers;
	}
	
	public static HistoricalTicker createHistoricalQuote(String date) {
		HistoricalTicker ticker = new HistoricalTicker();
		ticker.setSymbol("AAPL");
		ticker.setPriceDate(date);
		ticker.setOpen(166.11);
		ticker.setClose(166.44);
		ticker.setHigh(169);
		ticker.setLow(164.56);
		
		return ticker;
	}
}

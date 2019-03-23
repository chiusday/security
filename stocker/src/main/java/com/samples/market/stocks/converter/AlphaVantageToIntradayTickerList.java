package com.samples.market.stocks.converter;

import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.samples.market.model.IntradayTicker;
import com.samples.market.model.IntradayTickerList;
import com.samples.market.stocks.converter.interfaces.JsonToTickerList;
import com.samples.market.stocks.model.AlphaVantageIntradayTicker;

import io.vertx.core.json.JsonObject;

@Component
public class AlphaVantageToIntradayTickerList extends JsonToTickerList
		<IntradayTicker, AlphaVantageIntradayTicker, IntradayTickerList> {
	
	public AlphaVantageToIntradayTickerList() {
		super(AlphaVantageIntradayTicker.class, IntradayTickerList.class);
	}
	
	@Override
	public void additionalFields
			(String symbol, JsonObject quote, Entry<String, Object> entry) {
		
		quote.put("symbol", symbol);
		quote.put("time", entry.getKey());
	}
}

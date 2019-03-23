package com.samples.market.stocks.visitor.model;

import com.samples.market.model.IntradayTicker;
import com.samples.market.stocks.converter.AlphaVantageToIntradayTickerList;
import com.samples.market.stocks.visitor.interfaces.IVisitor;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteConvertibleResponse;

public class IntradayTickerListVisitorModel 
		extends JsonQuoteConvertibleResponse<IntradayTicker> {
	
	private AlphaVantageToIntradayTickerList converter;
	
	public IntradayTickerListVisitorModel() {
		this.converter = new AlphaVantageToIntradayTickerList();
	}
	
	@Override
	public AlphaVantageToIntradayTickerList getConverter() {
		return this.converter;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}	
}

package com.samples.market.stocks.visitor.model;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.converter.AlphaVantageToHistoricalTickerList;
import com.samples.market.stocks.visitor.interfaces.IVisitor;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteConvertibleResponse;

public class HistoricalTickerListVisitorModel 
		extends JsonQuoteConvertibleResponse<HistoricalTicker> {
	
	private AlphaVantageToHistoricalTickerList converter;
	
	public HistoricalTickerListVisitorModel() {
		this.converter = new AlphaVantageToHistoricalTickerList();
	}

	@Override
	public AlphaVantageToHistoricalTickerList getConverter(){
		return this.converter;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}

package com.samples.market.stocks.visitor.interfaces;

import com.samples.market.stocks.visitor.model.HistoricalTickerListVisitorModel;
import com.samples.market.stocks.visitor.model.IntradayTickerListVisitorModel;

public interface IVisitor<T> {

	default void visit(ConvertibleResponse<T> visitorModel) {
		throw new UnsupportedOperationException
			("visit(ConvertibleResponse) is not supported.");
	}

	default void visit(JsonQuoteConvertibleResponse<T> visitorModel) {
		throw new UnsupportedOperationException
			("visit(JsonQuoteConvertibleResponse) is not supported.");
	}
	
	default void visit(HistoricalTickerListVisitorModel visitorModel) {
		throw new UnsupportedOperationException
			("visit(HistoricalTickerListVisitorModel) is not supported.");
	}
	
	default void visit(IntradayTickerListVisitorModel visitorModel) {
		throw new UnsupportedOperationException
			("visit(IntradayTickerListVisitorModel) is not supported.");
	}
	
	
}

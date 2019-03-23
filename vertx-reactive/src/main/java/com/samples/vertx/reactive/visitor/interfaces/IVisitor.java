package com.samples.vertx.reactive.visitor.interfaces;

import com.samples.market.model.Ticker;
import com.samples.vertx.reactive.visitor.model.BatchRxResponse;
import com.samples.vertx.reactive.visitor.model.RxResponse;
import com.samples.vertx.reactive.visitor.model.Tickers;

public interface IVisitor<T> {

	default void visit(RxResponse<T> model) {
		throw new UnsupportedOperationException
				("visit(RxResponse) is not supported."); 
	}
	
	default void visit(BatchRxResponse<T> model) {
		throw new UnsupportedOperationException
				("visit(BatchRxResponse) is not supported."); 
	}
	
	
	default <S extends Ticker> void visit(Tickers<S> tickers) {
		throw new UnsupportedOperationException
				("visit(Tickers) is not supported."); 
	}
	
	
}

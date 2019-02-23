package com.samples.vertx.reactive.visitor.interfaces;

import com.samples.vertx.reactive.visitor.model.RxResponse;

public interface IVisitor<T> {

	default public void visit(RxResponse<T> model) {
		throw new UnsupportedOperationException
		("visit(RxResponse) is not supported."); 
	}
}

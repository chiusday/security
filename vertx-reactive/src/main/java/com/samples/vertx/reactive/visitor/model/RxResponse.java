package com.samples.vertx.reactive.visitor.model;

import com.samples.vertx.reactive.visitor.interfaces.IVisitor;

public class RxResponse<T> extends BaseVisitorModelRxResp<T> {
	
	@Override
	public void accept(IVisitor<T> visitor) {
		visitor.visit(this);
	}
}

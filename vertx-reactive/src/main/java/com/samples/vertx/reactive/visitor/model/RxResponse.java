package com.samples.vertx.reactive.visitor.model;

import com.samples.vertx.reactive.visitor.interfaces.IVisitor;
import com.samples.vertx.reactive.visitor.interfaces.IVisitorModel;

public class RxResponse<T> extends BaseVisitorModelRxResp<T> implements IVisitorModel<T> {
	
	@Override
	public void accept(IVisitor<T> visitor) {
		visitor.visit(this);
	}
}

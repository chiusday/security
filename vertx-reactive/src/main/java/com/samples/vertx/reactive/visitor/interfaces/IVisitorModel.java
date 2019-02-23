package com.samples.vertx.reactive.visitor.interfaces;


public interface IVisitorModel<T> {
	
	default public void accept(IVisitor<T> visitor) {
		throw new UnsupportedOperationException("accept("+visitor.getClass().getName()
				+") is not implemented.");
	}
}

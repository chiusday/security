package com.samples.vertx.reactive.visitor.interfaces;

public interface IVisitorModel<T> {
	void accept(IVisitor<T> visitor);
}

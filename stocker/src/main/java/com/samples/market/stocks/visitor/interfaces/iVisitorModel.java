package com.samples.market.stocks.visitor.interfaces;

public interface iVisitorModel<T> {

	void accept(IVisitor<T> visitor);
}

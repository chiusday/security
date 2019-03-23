package com.samples.market.stocks.converter.interfaces;

public interface IConverter<F, T> {

	T convertFrom(F from);
	F convertTo(T to);
}

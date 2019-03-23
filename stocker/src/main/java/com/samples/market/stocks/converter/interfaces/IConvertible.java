package com.samples.market.stocks.converter.interfaces;

public interface IConvertible<F, T> {

	IConverter<F, T> getConverter();
}

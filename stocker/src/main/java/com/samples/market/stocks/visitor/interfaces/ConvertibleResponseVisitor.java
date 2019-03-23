package com.samples.market.stocks.visitor.interfaces;

import com.samples.market.stocks.converter.interfaces.IConverter;

public abstract class ConvertibleResponseVisitor<T> implements IVisitor<T> {

	@Override
	@SuppressWarnings(value = "unchecked")
	public void visit(ConvertibleResponse<T> convertibleResponse) {
		@SuppressWarnings("rawtypes")
		IConverter converter = convertibleResponse.getConverter();
		convertibleResponse.setModel((T)converter.convertFrom
				(convertibleResponse.getConvertibleJsonTicker()));
		convertibleResponse.setHasError(false);
	}
}

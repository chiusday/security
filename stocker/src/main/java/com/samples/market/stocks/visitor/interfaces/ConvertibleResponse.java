package com.samples.market.stocks.visitor.interfaces;

import com.samples.market.model.Ticker;
import com.samples.market.stocks.converter.interfaces.IConvertible;

@SuppressWarnings("rawtypes")
public abstract class ConvertibleResponse<T> extends BaseVisitorModelResp<T> 
		implements IConvertible {

	protected ConvertibleJsonTicker<? extends Ticker> convertible;
	
	public ConvertibleJsonTicker<? extends Ticker> getConvertibleJsonTicker() {
		return convertible;
	}

	/**
	 * ConvertibleJsonTicker is the source to be converted to a Ticker variant.
	 * The T is defined by the implementor or the class that will set the value
	 * when this function is called.
	 * @param convertible
	 */
	public void setConvertibleJsonTicker
			(ConvertibleJsonTicker<? extends Ticker> convertible) {
		
		this.convertible = convertible;
	}

	@Override
	public void accept(IVisitor<T> visitor) {
		visitor.visit(this);
	}
}

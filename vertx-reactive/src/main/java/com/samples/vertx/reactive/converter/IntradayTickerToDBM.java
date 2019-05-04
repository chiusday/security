package com.samples.vertx.reactive.converter;

import com.samples.common.converter.interfaces.IConverter;
import com.samples.market.model.IntradayTicker;
import com.samples.vertx.reactive.model.IntradayTickerDBM;

public class IntradayTickerToDBM implements IConverter<IntradayTicker, IntradayTickerDBM> {

	@Override
	public IntradayTickerDBM convertFrom(IntradayTicker from) {
		IntradayTickerDBM reply = new IntradayTickerDBM();
		reply.setSymbol(from.getSymbol());
		reply.setOpen(from.getOpen());
		reply.setClose(from.getClose());
		reply.setHigh(from.getHigh());
		reply.setLow(from.getLow());
		reply.setPriceTime(from.getPriceTime());
		
		return reply;
	}

	@Override
	public IntradayTicker convertTo(IntradayTickerDBM to) {
		IntradayTicker ticker = new IntradayTicker();
		ticker.setSymbol(to.getSymbol());
		ticker.setOpen(to.getOpen());
		ticker.setClose(to.getClose());
		ticker.setHigh(to.getHigh());
		ticker.setLow(to.getLow());
		ticker.setPriceTime(to.getPriceTime());

		return ticker;
	}

}

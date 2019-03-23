package com.samples.vertx.reactive.converter;

import com.samples.common.converter.interfaces.IConverter;
import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.reactive.model.HistoricalTickerDBM;

public class HistoricalTickerToDBM 
		implements IConverter<HistoricalTicker, HistoricalTickerDBM> {

	@Override
	public HistoricalTickerDBM convertFrom(HistoricalTicker from) {
		HistoricalTickerDBM reply = new HistoricalTickerDBM();
		reply.setSymbol(from.getSymbol());
		reply.setOpen(from.getOpen());
		reply.setClose(from.getClose());
		reply.setHigh(from.getHigh());
		reply.setLow(from.getLow());
		reply.setPriceDate(from.getPriceDate());
		
		return reply;
	}

	@Override
	public HistoricalTicker convertTo(HistoricalTickerDBM to) {
		HistoricalTicker reply = new HistoricalTicker();
		reply.setSymbol(to.getSymbol());
		reply.setOpen(to.getOpen());
		reply.setClose(to.getClose());
		reply.setHigh(to.getHigh());
		reply.setLow(to.getLow());
		reply.setPriceDate(to.getPriceDate());
		
		return reply;
	}
	
	
}

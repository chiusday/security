package com.samples.market.stocks.converter.interfaces;

import java.lang.reflect.Constructor;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samples.market.model.Ticker;
import com.samples.market.model.TickerList;
import com.samples.market.stocks.visitor.interfaces.ConvertibleJsonTicker;

import io.vertx.core.json.JsonObject;

/**
 * Converter between a ConvertibleJsonTicker<T extends Ticker> and a TickerList of T that
 * extends Ticker
 * @author chiusday
 *
 * @param <T> - Ticker or it's subclasses
 */
public abstract class JsonToTickerList<T extends Ticker,
		F extends ConvertibleJsonTicker<T>, L extends TickerList<T>> 
		implements IConverter<F, TickerList<T>> {
	
	private Logger log = LoggerFactory.getLogger(JsonToTickerList.class);

	protected Class<F> fromType;
	protected Class<L> toType;
	
	protected abstract void additionalFields
			(String symbol, JsonObject quote, Entry<String, Object> entry);

	public JsonToTickerList(Class<F> fromType, Class<L> toType) {
		
		this.fromType = fromType;
		this.toType = toType;
	}
	
	
	@Override
	public L convertFrom(F from) {
		L tickers = (L)getToInstance(toType, from.getSymbol());
		//for each quote
		from.getData().forEach(entry -> {
			JsonObject quote = new JsonObject();
			//match each element in the quote with the ticker field. 
			//Then add to list to be returned
			JsonObject.mapFrom(entry.getValue()).getMap().entrySet().forEach(elem -> {
				for (String field : from.getFields()) {
					if (elem.getKey().contains(field)) {
						quote.put(field, elem.getValue());
						break;
					}
				}
			});
			additionalFields(from.getSymbol(), quote, entry);
			tickers.add(quote.mapTo(from.getType()));
		});	
		
		return tickers;
	}
	
	@Override
	public F convertTo(TickerList<T> to) {
		throw new UnsupportedOperationException("convertTo(List<T> is not supported");
	}
	
	/***
	 * Creates an instance with the String constructor parameter provided
	 * This is using reflection so there is considerable performance hit if 
	 * used in a loop
	 * @param toClass - class type of L
	 * @param symbol - Ticker symbol
	 * @return new Instance of L
	 */
	private L getToInstance(Class<L> toClass, String symbol) {
		try {
			Constructor<L> constructor = toClass.getConstructor(String.class);
			return constructor.newInstance(symbol);
		} catch (Exception e) {
			log.error("Error creating instance of " + toClass.getName(), e);
			throw new RuntimeException(e);
		}
	}
}

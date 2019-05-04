package com.samples.vertx.reactive.interfaces;

import com.samples.market.model.Ticker;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.DBConfig;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.Message;

public abstract class TickerVertxSQLDataAccess<T extends Ticker> 
		extends VertxSQLDataAccess<T> {

	public TickerVertxSQLDataAccess(Class<T> type, DBConfig config) {
		super(type, config);
	}

	protected abstract String getUpdateSql(String keyValue);
	
	@Override
	public void update(Message<JsonObject> message) {
		DataAccessMessage<T> msgTicker = new DataAccessMessage<>(message.body());
		msgTicker.setKey(msgTicker.getModel().getSymbol());
		String sql = getUpdateSql(msgTicker.getKey());
		update(msgTicker.getKey(), sql, msgTicker.getModel(), next -> {
			if (isTransactionFailed(next, msgTicker) == false) {
				msgTicker.setModel(next.result());
			}
			message.reply(JsonObject.mapFrom(msgTicker));
		});
	}
	
	@Override
	public void delete(Message<JsonObject> message) {
		DataAccessMessage<T> msgTicker = new DataAccessMessage<>(message.body());
		msgTicker.setCriteria("symbol="+msgTicker.getModel().getSymbol());
		
		delete(msgTicker.getCriteria(), new JsonArray().add(msgTicker.getModel().getSymbol()), 
			next -> {
				if (isTransactionFailed(next, msgTicker) == false) {
					msgTicker.setAffectedRecords(next.result());
				}
				message.reply(JsonObject.mapFrom(msgTicker));
			});
	}
}

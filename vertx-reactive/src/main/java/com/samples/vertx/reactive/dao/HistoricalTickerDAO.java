package com.samples.vertx.reactive.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.DBConfig;
import com.samples.vertx.reactive.interfaces.VertxSQLDataAccess;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.reactivex.core.eventbus.Message;

@Component
public class HistoricalTickerDAO extends VertxSQLDataAccess<HistoricalTicker> {
	private Logger log = LoggerFactory.getLogger(HistoricalTickerDAO.class);

	public HistoricalTickerDAO(DBConfig config) {
		super(HistoricalTicker.class, config);
	}

	@Override
	protected String getTableName() {
		return "HistoricalTicker";
	}
	
	@Override
	protected String getInsertSql() {
		return "INSERT INTO " + getTableName()
			+ " (symbol, open, close, high, low, price_date)"
			+ " VALUES (?, ?, ?, ?, ?, ?)";
	}

	//Since this is latency sensitive, JsonObject.mapFrom will not be used
	//because the benefit is not worth the processing speed cost.
	@Override
	public JsonArray toJsonArray(HistoricalTicker ticker) {
		return noKeyJsonArray(ticker);
	}
	
	//Since this is latency sensitive, JsonObject.mapFrom will not be used
	//because the benefit is not worth the processing speed cost.
	@Override
	public JsonArray noKeyJsonArray(HistoricalTicker ticker) {
		return new JsonArray()
				.add(ticker.getSymbol())
				.add(ticker.getOpen())
				.add(ticker.getClose())
				.add(ticker.getHigh())
				.add(ticker.getLow())
				.add(ticker.getPriceDate());
	}
	
	@Override
	public void executeCreate() {
		String s = "CREATE TABLE IF NOT EXISTS " + getTableName()
			+" (id IDENTITY NOT NULL PRIMARY KEY, symbol VARCHAR(64),"
			+" open DECIMAL(11,4), close DECIMAL(11,4), high DECIMAL(11,4),"
			+" low DECIMAL(11,4), price_date Date)";
		
		this.jdbc.rxGetConnection()
			.flatMap(conn -> {
				Single<UpdateResult> result = conn.rxUpdate(s);
				return result.doAfterTerminate(conn::close);
			})
			.subscribe(result -> {
				log.info("Create table "+getTableName()+ " successful.");
			}, error -> {
				log.error("Error creating table "+getTableName()+"!\n"
						+error.getMessage());
			});
	}
	
	@Override
	public void insert(Message<JsonObject> message) {
		DataAccessMessage<HistoricalTicker> tickerMessage = new DataAccessMessage<>(message.body());
		HistoricalTicker ticker = tickerMessage.getModel();
		insert(ticker, next -> {
			if (isTransactionFailed(next, tickerMessage) == false){
				tickerMessage.setModel(next.result());
			}
			message.reply(JsonObject.mapFrom(tickerMessage));
		});
	}
	
	@Override
	public void batchInsert(Message<JsonObject> message) {
		DataAccessMessage<HistoricalTicker> tickerMessage = new DataAccessMessage<>(message.body());
		List<JsonArray> batchParams = tickerMessage.getListJsonArray();
		batchInsert(batchParams, next -> {
			if (isTransactionFailed(next, tickerMessage) == false) {
				tickerMessage.setBatchResult(next.result());
			}
			message.reply(JsonObject.mapFrom(tickerMessage));			
		});
	}

	@Override
	public void select(Message<JsonObject> message) {
		DataAccessMessage<HistoricalTicker> tickerMessage = new DataAccessMessage<>(message.body());
		select(tickerMessage.getCriteria(), tickerMessage.getParameters(), next -> {
			if (isTransactionFailed(next, tickerMessage) == false){
				tickerMessage.setRecords(next.result());
			}
			message.reply(JsonObject.mapFrom(tickerMessage));
		});
	}

	@Override
	public void update(Message<JsonObject> message) {
		DataAccessMessage<HistoricalTicker> msgTicker = new DataAccessMessage<>(message.body());
		msgTicker.setKey(msgTicker.getModel().getSymbol());
		String sql = "UPDATE "+getTableName()+" SET "
				+ "open=?, close=?, high=?, low=? "
				+ "WHERE symbol="+msgTicker.getKey();
		update(msgTicker.getKey(), sql, msgTicker.getModel(), next -> {
			if (isTransactionFailed(next, msgTicker) == false) {
				msgTicker.setModel(next.result());
			}
			message.reply(JsonObject.mapFrom(msgTicker));
		});
	}
	
	@Override
	public void delete(Message<JsonObject> message) {
		DataAccessMessage<HistoricalTicker> msgTicker = new DataAccessMessage<>(message.body());
		msgTicker.setCriteria("symbol="+msgTicker.getModel().getSymbol());
		
		delete("id=?", new JsonArray().add(msgTicker.getModel().getSymbol()), 
			next -> {
				if (isTransactionFailed(next, msgTicker) == false) {
					msgTicker.setAffectedRecords(next.result());
				}
				message.reply(JsonObject.mapFrom(msgTicker));
			});
	}
}

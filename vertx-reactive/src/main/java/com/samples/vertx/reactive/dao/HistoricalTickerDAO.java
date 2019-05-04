package com.samples.vertx.reactive.dao;

import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.reactive.DBConfig;
import com.samples.vertx.reactive.interfaces.TickerVertxSQLDataAccess;

import io.vertx.core.json.JsonArray;

@Service
public class HistoricalTickerDAO extends TickerVertxSQLDataAccess<HistoricalTicker> {
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
	
	@Override
	protected String getCreateSql() {
		return "CREATE TABLE IF NOT EXISTS " + getTableName()
			+" (id IDENTITY NOT NULL PRIMARY KEY, symbol VARCHAR(64),"
			+" open DECIMAL(11,4), close DECIMAL(11,4), high DECIMAL(11,4),"
			+" low DECIMAL(11,4), price_date Date)";	
	}

	@Override
	protected String getUpdateSql(String keyValue) {
		return "UPDATE "+getTableName()+" SET "
				+ "open=?, close=?, high=?, low=? "
				+ "WHERE symbol="+keyValue;
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
}

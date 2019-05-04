package com.samples.vertx.reactive.service;

import static com.samples.utilities.objects.ClassUtil.getClazz;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.market.model.Ticker;
import com.samples.vertx.enums.DBOperations;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.AppConfig;
import com.samples.vertx.reactive.verticle.DataAccessInterchange;
import com.samples.vertx.reactive.visitor.interfaces.TickersVisitor;
import com.samples.vertx.reactive.visitor.model.RxResponse;
import com.samples.vertx.reactive.visitor.model.BaseVisitorModelRxResp;
import com.samples.vertx.reactive.visitor.model.BatchRxResponse;
import com.samples.vertx.reactive.visitor.model.Tickers;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.core.eventbus.Message;

@Service
public abstract class MarketDataService<T extends Ticker> {
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private DataAccessInterchange dataAccessInterchange;
	
	protected TickersVisitor<T> tickersVisitor;
	
	@PostConstruct
	protected abstract void setTickersVisitor();
	
	public RxResponse<T> addMarketData(T ticker) {
		DataAccessMessage<T> tickerMessage = new DataAccessMessage<>(getClazz(ticker));
		tickerMessage.setModel(ticker);
		tickerMessage.setOperation(DBOperations.insert);
		
		return (RxResponse<T>) processMarketData(tickerMessage, new RxResponse<T>());
	}

	public RxResponse<T> getMarketData(String id, Class<T> clazz) {
		DataAccessMessage<T> tickerMessage = new DataAccessMessage<>(clazz);
		tickerMessage.setCriteria("symbol=?");
		tickerMessage.setParameters(new JsonArray().add(id));
		tickerMessage.setOperation(DBOperations.select);
		
		return (RxResponse<T>) processMarketData(tickerMessage, new RxResponse<T>());
	}

	public RxResponse<T> getMarketData(String id, LocalDate priceDate, Class<T> clazz) {
		DataAccessMessage<T> tickerMessage = new DataAccessMessage<>(clazz);
		tickerMessage.setCriteria("symbol=? and price_date=?");
		tickerMessage.setParameters(new JsonArray().add(id).add(priceDate.toString()));
		tickerMessage.setOperation(DBOperations.select);
		
		return (RxResponse<T>) processMarketData(tickerMessage,  new RxResponse<T>());
	}
	
	public BatchRxResponse<T> batchAddMarketData(List<T> tickers) {
		DataAccessMessage<T> tickerMessage =
				new DataAccessMessage<>(getClazz(tickers.get(0)));
		tickerMessage.setListJsonArray(getBatchParameters(tickers));
		tickerMessage.setOperation(DBOperations.batchInsert);
		
		return (BatchRxResponse<T>)
				processMarketData(tickerMessage, new BatchRxResponse<>());
	}
	
	protected BaseVisitorModelRxResp<T> processMarketData(DataAccessMessage<T> tickerMsg, 
			BaseVisitorModelRxResp<T> marketDataResponse){
		
		EventBus eventBus = dataAccessInterchange.getRxVertx().eventBus();
		Single<Message<JsonObject>> response = eventBus.rxSend
				(appConfig.getAddressMarketInfo(), JsonObject.mapFrom(tickerMsg));
		marketDataResponse.setSingle(response);
		
		return marketDataResponse;
	}
	
	protected List<JsonArray> getBatchParameters(List<T> modelList) {
		Tickers<T> tickers = new Tickers<>();
		tickers.setTickers(modelList);
		tickers.accept(tickersVisitor);
		
		return tickers.getListJsonArray();
	}
}

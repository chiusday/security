package com.samples.vertx.reactive.service;

import static com.samples.utilities.objects.ClassUtil.getClazz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.market.model.Ticker;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.AppConfig;
import com.samples.vertx.reactive.verticle.DataAccessInterchange;
import com.samples.vertx.reactive.visitor.model.RxResponse;
import com.samples.vertx.enums.DBOperations;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.core.eventbus.Message;

@Service
public class MarketDataService {
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private DataAccessInterchange dataAccessInterchange;
	
	public <T extends Ticker> RxResponse<T> addMarketData(T ticker) {
		DataAccessMessage<T> tickerMessage = new DataAccessMessage<>(getClazz(ticker));
		tickerMessage.setModel(ticker);
		tickerMessage.setOperation(DBOperations.insert);
		
		return processMarketData(tickerMessage);
	}

	public <T extends Ticker> RxResponse<T> getMarketData(String id) {
		DataAccessMessage<T> tickerMessage = new DataAccessMessage<>();
		tickerMessage.setCriteria("symbol=?");
		tickerMessage.setParameters(new JsonArray().add(id));
		tickerMessage.setOperation(DBOperations.select);
		
		return processMarketData(tickerMessage);
	}

	private <T extends Ticker> RxResponse<T> processMarketData(DataAccessMessage<T> tickerMsg){
		RxResponse<T> marketDataResponse = new RxResponse<>();
		EventBus eventBus = dataAccessInterchange.getRxVertx().eventBus();
		Single<Message<JsonObject>> response = eventBus.rxSend
				(appConfig.getAddressMarketInfo(), JsonObject.mapFrom(tickerMsg));
		marketDataResponse.setSingle(response);
		
		return marketDataResponse;
	}
}

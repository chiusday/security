package com.samples.vertx.reactive.test.marketdata.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.enums.DBOperations;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.dao.HistoricalTickerDAO;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.eventbus.Message;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class TestMarketDataDao {
	private final String address = "testMarketDataInfoAddress";
	private Vertx vertx;
	private DataAccessMessage<HistoricalTicker> daMessage;
	
	@Autowired
	private HistoricalTickerDAO marketDataDao;
	
	@Before
	public void setup() {
		vertx = Vertx.vertx();
		daMessage = new DataAccessMessage<>(HistoricalTicker.class);
	}
	
	@Test
	public void testAddHistoricalTicker() {
		vertx.eventBus().<JsonObject>consumer(address).toFlowable().subscribe(msg -> {
			marketDataDao.insert(msg);
		});

		daMessage.setModel(createHistoricalTicker());
		daMessage.setOperation(DBOperations.insert);
		//use blocking for debugging convenience
		Message<JsonObject> response = vertx.eventBus().<JsonObject>rxSend
				(address, JsonObject.mapFrom(daMessage)).blockingGet();
		DataAccessMessage<HistoricalTicker> msgResp = 
				new DataAccessMessage<>(response.body());
		Assert.isNull(msgResp.getFailure(), "Failure json should be null");
	}
	
	@Test
	public void testBatchAddHistoricalTicker() {
		vertx.eventBus().<JsonObject>consumer(address).toFlowable().subscribe(msg ->{
			marketDataDao.batchInsert(msg);
		});		
		daMessage.setListJsonArray(createHistoricalTickersAsJsonArray());
		daMessage.setOperation(DBOperations.batchInsert);
		//use blocking for debugging convenience
		Message<JsonObject> response = vertx.eventBus().<JsonObject>rxSend
				(address, JsonObject.mapFrom(daMessage)).blockingGet();
		
		DataAccessMessage<HistoricalTicker> msgResp = 
				new DataAccessMessage<>(response.body());
		Assert.isNull(msgResp.getFailure(), "Failure object should be null");
	}
	
	private HistoricalTicker createHistoricalTicker() {
		return createHistoricalTicker(LocalDate.now());
	}
	
//	private List<JsonObject> createHistoricalTickersAsJson() {
//		return createHistoricalTickersAsJson(600);
//	}
	
	private List<JsonArray> createHistoricalTickersAsJsonArray(){
		return createHistoricalTickersAsJsonArray(600);
	}
	
//	private List<JsonObject> createHistoricalTickersAsJson(int count) {
//		List<JsonObject> reply = new ArrayList<>();
//		LocalDate date = LocalDate.now();
//		for (int index=0; index<count; index++) {
//			reply.add(JsonObject.mapFrom(createHistoricalTicker(date.minusDays(1))));
//		}
//		
//		return reply;
//	}
	
	private List<JsonArray> createHistoricalTickersAsJsonArray(int count){
		List<JsonArray> reply = new ArrayList<>();
		LocalDate date = LocalDate.now();
		for (int index=0; index<count; index++) {
			reply.add(historicalTickerAsJsonArray(createHistoricalTicker(date.minusDays(1))));
		}
		
		return reply;
	}

	private JsonArray historicalTickerAsJsonArray(HistoricalTicker ticker) {
		return new JsonArray()
				.add(ticker.getSymbol())
				.add(ticker.getOpen())
				.add(ticker.getClose())
				.add(ticker.getHigh())
				.add(ticker.getLow())
				.add(ticker.getPriceDate());
	}
	
	private HistoricalTicker createHistoricalTicker(LocalDate date) {
		HistoricalTicker ticker = new HistoricalTicker();
		ticker.setSymbol("AAPL");
		ticker.setOpen(174.6500);
		ticker.setClose(174.2400);
		ticker.setHigh(175.5700);
		ticker.setLow(172.8531);
		ticker.setPriceDate(date.toString());
		
		return ticker;
	}
}

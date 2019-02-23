package com.samples.vertx.reactive.verticle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.AppConfig;
import com.samples.vertx.reactive.service.DataAccessMessageRouter;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.eventbus.Message;

@Component
public class DataAccessInterchange extends AbstractVerticle {
	private Logger log = LoggerFactory.getLogger(DataAccessInterchange.class);
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private DataAccessMessageRouter messageRouter;

	@Override
	public void start() throws Exception {
		super.start();
		messageRouter.setDataConnections(vertx);
		vertx.eventBus().<JsonObject>consumer(appConfig.getAddressUser())
			.toFlowable().subscribe(this::processRequest);
		
		vertx.eventBus().<JsonObject>consumer(appConfig.getAddressMarketInfo())
			.toFlowable().subscribe(this::processRequest);
	}
	
	public Vertx getRxVertx() {
		return vertx;
	}
	
	private void processRequest(Message<JsonObject> message){
		log.debug("About to route message >> " + Json.encodePrettily(message.body()));
		try {
			messageRouter.routeMessage(message);
		} catch (Exception e) {
			log.error("Routing message failed:\n" + e);
			DataAccessMessage<?> daMessage = new DataAccessMessage<>(message.body());
			daMessage.setFailure(new JsonObject().put(DataAccessMessage.FAILURE_MESSAGE, 
					e.getMessage()));
			message.reply(JsonObject.mapFrom(message));
		}
	}
}

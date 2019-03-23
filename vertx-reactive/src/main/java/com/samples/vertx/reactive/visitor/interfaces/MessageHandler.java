package com.samples.vertx.reactive.visitor.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.visitor.model.BaseVisitorModelResp;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.Message;

public abstract class MessageHandler<T> {
	Logger log = LoggerFactory.getLogger(MessageHandler.class);
	
	protected DataAccessMessage<T> dataMessage;
	
	public abstract String getErrorText();
	
	public abstract String getResultText();
	
	public abstract ResponseEntity<Object> 
			getResponseEntity(DataAccessMessage<T> message);
	
	/**
	 * Message<JsonObject> is converted by handle(Message<JsonObject>, 
	 * BaseVisitorModelResp<T>) into DataAccessMessage<T>. Therefore, handle method
	 * has to be called first before this method can return a useful result.  
	 * @return DataAccessMessage<T> extracted from Message<JsonObject> after handle()
	 * is called. Otherwise, returns null.
	 */
	protected DataAccessMessage<T> getDAMessage() {
		return this.dataMessage;
	}
	
	/**
	 * Handles Message<JsonObject> to fill BaseVisitorModelResp model.
	 * Will also set the value that will returned by getDAMessage()
	 * @param message - Message<JsonObject> that contains the DataAccessMessage
	 * @param response - BaseVisitorModelResp that will be populated
	 */
	public void handle(Message<JsonObject> message, BaseVisitorModelResp<T> response) {
		log.debug("Message received >> \n" + Json.encodePrettily(message.body()));
		this.dataMessage = new DataAccessMessage<>(message.body());
		if (this.dataMessage.getFailure() != null && this.dataMessage.getFailure().getMap() != null){
			response.setHasError(true);
			response.setResponseEntity(getResponseEntity(this.dataMessage));
			log.error(this.dataMessage.getFailure().getString
					(DataAccessMessage.FAILURE_MESSAGE));
		} else {
			response.setHasError(false);
			response.setResponseEntity(getResponseEntity(this.dataMessage));
		}
	}
}

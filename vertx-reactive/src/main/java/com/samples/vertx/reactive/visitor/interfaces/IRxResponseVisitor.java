package com.samples.vertx.reactive.visitor.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.visitor.model.RxResponse;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.Message;

public interface IRxResponseVisitor<T> extends IVisitor<T> {
	Logger log = LoggerFactory.getLogger(IRxResponseVisitor.class);

	String getErrorText();
	String getResultText();
	ResponseEntity<Object> getResponseEntity(DataAccessMessage<T> message);
	
	default T getModel(DataAccessMessage<T> message) {
		return message.getModel();
	}
	
	@Override
	default void visit(RxResponse<T> rxResponse) {
		try {
			Message<JsonObject> message = rxResponse.getSingle().blockingGet();
			log.debug("Message recieved >> \n" + Json.encodePrettily(message.body()));
			DataAccessMessage<T> payloadMessage = new DataAccessMessage<>(message.body());
			if (payloadMessage.getFailure() != null && payloadMessage.getFailure().getMap() != null){
				rxResponse.setHasError(true);
				rxResponse.setResponseEntity(getResponseEntity(payloadMessage));
				log.error(payloadMessage.getFailure().getString
						(DataAccessMessage.FAILURE_MESSAGE));
			} else {
				rxResponse.setModel(getModel(payloadMessage));
				rxResponse.setHasError(false);
				rxResponse.setResponseEntity(getResponseEntity(payloadMessage));
				log.info(getResultText()+"\n"+ (rxResponse.getModel() == null ? "{}" :
						Json.encodePrettily(rxResponse.getModel())));
			}
		} catch (Exception e) {
			rxResponse.setHasError(true);
			rxResponse.setResponseEntity(new ResponseEntity<Object>
				(getErrorText(), HttpStatus.INTERNAL_SERVER_ERROR));
			log.error(getResultText()+"\n"+e);
		}
	}
}

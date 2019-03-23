package com.samples.vertx.reactive.visitor.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.visitor.model.RxResponse;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.Message;

public abstract class RxResponseVisitor<T> extends MessageHandler<T> 
		implements IVisitor<T> {
	
	Logger log = LoggerFactory.getLogger(RxResponseVisitor.class);

	protected T getModel(DataAccessMessage<T> message) {
		return message.getModel();
	}
	
	@Override
	public void visit(RxResponse<T> rxResponse) {
//		try { replaced by Global exception handler
			Message<JsonObject> message = rxResponse.getSingle().blockingGet();
			this.handle(message, rxResponse);
			if (!rxResponse.isHasError()) {
				rxResponse.setModel(getModel(getDAMessage()));
				log.info(getResultText()+"\n"+ (rxResponse.getModel() == null ? "{}" :
					Json.encodePrettily(rxResponse.getModel())));
			}
//		} catch (Exception e) {
//			rxResponse.setHasError(true);
//			rxResponse.setResponseEntity(new ResponseEntity<Object>
//				(getErrorText(), HttpStatus.INTERNAL_SERVER_ERROR));
//			log.error(getResultText()+"\n"+e);
//		}
	}
}

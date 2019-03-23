package com.samples.vertx.reactive.visitor.interfaces;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.visitor.model.BatchRxResponse;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.Message;

public abstract class BatchRxResponseVisitor<T> extends MessageHandler<T> 
		implements IVisitor<T> {
	private Logger log = LoggerFactory.getLogger(BatchRxResponseVisitor.class);
	
	protected abstract List<Integer> getBatchResult(DataAccessMessage<T> daMessage);

	@Override
	public void visit(BatchRxResponse<T> rxResponse) {
		try {
			Message<JsonObject> message = rxResponse.getSingle().blockingGet();
			this.handle(message, rxResponse);
			if (!rxResponse.isHasError()) {
				rxResponse.setBatchResult(getBatchResult(getDAMessage()));
				log.info(getResultText()+"\n"+ 
						(rxResponse.getBatchResult() == null 
								? "{}" 
								: Json.encodePrettily(rxResponse.getBatchResult())
						));
			}
		} catch (Exception e) {
			rxResponse.setHasError(true);
			rxResponse.setResponseEntity(new ResponseEntity<Object>
					(getErrorText(), HttpStatus.INTERNAL_SERVER_ERROR));
			log.error(getErrorText()+"\n"+e);
		}
	}
}

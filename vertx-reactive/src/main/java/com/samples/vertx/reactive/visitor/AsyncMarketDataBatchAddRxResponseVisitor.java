package com.samples.vertx.reactive.visitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.samples.market.model.Ticker;
import com.samples.vertx.reactive.visitor.interfaces.BatchAddRxResponseVisitor;
import com.samples.vertx.reactive.visitor.model.BatchRxResponse;

import io.vertx.core.json.Json;

@Component
public class AsyncMarketDataBatchAddRxResponseVisitor<T extends Ticker> 
		extends BatchAddRxResponseVisitor<T> {
	
	private Logger log = LoggerFactory.getLogger
			(AsyncMarketDataBatchAddRxResponseVisitor.class);

	@Override
	public void visit(BatchRxResponse<T> rxResponse) {
		try {
			rxResponse.getSingle().subscribe(message -> {
				this.handle(message, rxResponse);
				if (!rxResponse.isHasError()) {
					rxResponse.setBatchResult(getBatchResult(getDAMessage()));
					log.info(getResultText()+"\n"+ 
							(rxResponse.getBatchResult() == null 
									? "{}" 
									: Json.encodePrettily(rxResponse.getBatchResult())
							));
				}
			});
		} catch (Exception e) {
			rxResponse.setHasError(true);
			rxResponse.setResponseEntity(new ResponseEntity<Object>
					(getErrorText(), HttpStatus.INTERNAL_SERVER_ERROR));
			log.error(getErrorText()+"\n"+e);
		}
	}
}

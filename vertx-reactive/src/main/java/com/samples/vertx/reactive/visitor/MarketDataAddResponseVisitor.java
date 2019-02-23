package com.samples.vertx.reactive.visitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.samples.market.model.Ticker;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.visitor.interfaces.IRxResponseVisitor;

@Component
public class MarketDataAddResponseVisitor<T extends Ticker> implements IRxResponseVisitor<T> {
	@Value("${message.failed.internal-error.ins}")
	private String errorMessage;
	
	@Value("${message.success.ins}")
	private String successMessage;
	
	@Override
	public String getErrorText() {
		return this.errorMessage;
	}

	@Override
	public String getResultText() {
		return this.successMessage;
	}

	@Override
	public ResponseEntity<Object> getResponseEntity(DataAccessMessage<T> message) {
		return new ResponseEntity<>(message.getModel(), HttpStatus.CREATED);
	}
}

package com.samples.vertx.reactive.visitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samples.common.exception.model.dto.DataNotFoundException;
import com.samples.market.model.Ticker;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.visitor.interfaces.RxResponseVisitor;

@Service
public class MarketDataGetResponseVisitor<T extends Ticker> extends RxResponseVisitor<T> {

	@Value("${message.failed.internal-error.get}")
	private String errorText;
	
	@Value("${message.success.get}")
	private String successText;
	
	@Override
	public String getErrorText() {
		return this.errorText;
	}

	@Override
	public String getResultText() {
		return this.successText;
	}

	@Override
	public ResponseEntity<Object> getResponseEntity(DataAccessMessage<T> message) {
		if (message.getModel()==null)
			throw new DataNotFoundException("Ticker get", "Ticker not found");
		
		return new ResponseEntity<>(message.getModel(), HttpStatus.OK);
	}
}

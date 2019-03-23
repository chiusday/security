package com.samples.vertx.reactive.visitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.samples.market.model.Ticker;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.visitor.interfaces.RxResponseVisitor;

/***
 * Base class for market data add reactive response visitor. This can used with
 * it's default implementations or be extended for customized/specific features.
 * @author chiusday
 *
 * @param <T> Ticker or any of it's subclasses
 */
@Component
public class MarketDataAddResponseVisitor<T extends Ticker> 
		extends RxResponseVisitor<T> {

	@Value("${message.failed.internal-error.ins}")
	protected String errorMessage;
	
	@Value("${message.success.ins}")
	protected String successMessage;
	
	protected HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	protected String currentMessage = successMessage;
	
	@Override
	public String getErrorText() {
		return this.errorMessage;
	}
	
	@Override
	public String getResultText() {
		return this.currentMessage;
	}

	@Override
	public ResponseEntity<Object> getResponseEntity(DataAccessMessage<T> message) {
		return new ResponseEntity<>(message.getModel(), HttpStatus.CREATED);
	}
}

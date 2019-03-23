package com.samples.vertx.reactive.visitor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.samples.market.model.Ticker;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.visitor.interfaces.BatchRxResponseVisitor;

/***
 * Base class for market data batch add reactive response visitor. This can used with
 * it's default implementations or be extended for customized/specific features.
 * @author chiusday
 *
 * @param <T> Ticker or any of it's subclasses
 */
@Component
public class MarketDataBatchAddResponseVisitor<T extends Ticker> 
		extends BatchRxResponseVisitor<T> {
	
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
		return this.successMessage;
	}

	@Override
	public ResponseEntity<Object> getResponseEntity(DataAccessMessage<T> daMessage) {
		return new ResponseEntity<>(
					getBatchResult(daMessage).size()==0
						? getResultText()
						: daMessage.getBatchResult(),
					this.httpStatus
				);
	}

	@Override
	protected List<Integer> getBatchResult(DataAccessMessage<T> daMessage) {
		if (daMessage.getFailure() != null && daMessage.getFailure().getMap() != null) {
			this.currentMessage = this.errorMessage;
			this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ArrayList<Integer>();
		} else {
			this.currentMessage = this.successMessage;
			this.httpStatus = HttpStatus.CREATED;
			return daMessage.getBatchResult();
		}
	}
}
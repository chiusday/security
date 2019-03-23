package com.samples.vertx.reactive.visitor.interfaces;

import static com.samples.utilities.objects.ClassUtil.getNewT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.samples.market.model.Ticker;
import com.samples.vertx.model.DataAccessMessage;

public abstract class AddRxResponseVisitor<T extends Ticker> 
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
		return new ResponseEntity<>
				(getModel(message).getSymbol().isEmpty() ? getResultText() 
						: message.getModel()
						, HttpStatus.CREATED);
	}
	
	@Override
	public T getModel(DataAccessMessage<T> messageTicker) {
		if (messageTicker.getFailure() != null && 
				messageTicker.getFailure().getMap() !=null) {
			
			this.currentMessage = this.errorMessage;
			//replace this with Global Exception handler
			this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			return getNewT(messageTicker.getModel());
		} 

		this.currentMessage = this.successMessage;
		this.httpStatus = HttpStatus.CREATED;
		return messageTicker.getModel();
	}
}

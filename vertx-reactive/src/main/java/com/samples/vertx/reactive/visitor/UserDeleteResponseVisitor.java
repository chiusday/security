package com.samples.vertx.reactive.visitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.model.User;
import com.samples.vertx.reactive.visitor.interfaces.RxResponseVisitor;

@Component
public class UserDeleteResponseVisitor extends RxResponseVisitor<User> {

	@Value("${message.failed.internal-error.del}")
	private String errorMessage;
	
	@Value("${message.success.del}")
	private String successMessage;
	
	@Override
	public String getErrorText() {
		return errorMessage;
	}

	@Override
	public String getResultText() {
		return successMessage;
	}

	@Override
	public ResponseEntity<Object> getResponseEntity(DataAccessMessage<User> message) {
		if (message.getFailure() != null && message.getFailure().getMap() != null) {
			return new ResponseEntity<Object>(message.getFailure().getString
					(DataAccessMessage.FAILURE_MESSAGE), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(getResultText(), HttpStatus.OK);
	}

}

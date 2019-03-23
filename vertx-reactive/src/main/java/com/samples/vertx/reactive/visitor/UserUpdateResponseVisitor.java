package com.samples.vertx.reactive.visitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.model.User;
import com.samples.vertx.reactive.visitor.interfaces.RxResponseVisitor;

@Component
public class UserUpdateResponseVisitor extends RxResponseVisitor<User> {
	
	@Value("${message.failed.internal-error.upd}")
	private String errorMessage;
	
	@Value("${message.success.upd}")
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
		return new ResponseEntity<Object>(message.getModel(), HttpStatus.OK);
	}

}

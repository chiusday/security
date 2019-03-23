package com.samples.market.stocks.visitor.interfaces;

import org.springframework.http.ResponseEntity;

public abstract class BaseVisitorModelResp<T> extends BaseVisitorModel<T> {
	protected ResponseEntity<Object> responseEntity;

	public ResponseEntity<Object> getResponseEntity() {
		return this.responseEntity;
	}

	public void setResponseEntity(ResponseEntity<Object> responseEntity) {
		this.responseEntity = responseEntity;
	}
}

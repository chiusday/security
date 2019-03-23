package com.samples.vertx.reactive.visitor.model;

import com.samples.vertx.reactive.visitor.interfaces.IVisitorModel;

public abstract class BaseVisitorModel<T> implements IVisitorModel<T> {
//	protected Type type;
	protected T model;
	protected boolean hasError;
	protected String message;
	
	public T getModel() {
		return model;
	}
	public void setModel(T model) {
		this.model = model;
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	
}

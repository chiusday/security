package com.samples.vertx.reactive.visitor.model;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.Message;

public abstract class BaseVisitorModelRxResp<T> extends BaseVisitorModelResp<T> {
	protected Single<Message<JsonObject>> single;
	
	public Single<Message<JsonObject>> getSingle() {
		return this.single;
	}
	
	public void setSingle(Single<Message<JsonObject>> single) {
		this.single = single;
	}
}

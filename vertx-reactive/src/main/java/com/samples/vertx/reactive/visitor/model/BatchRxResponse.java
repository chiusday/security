package com.samples.vertx.reactive.visitor.model;

import java.util.List;

import com.samples.vertx.reactive.visitor.interfaces.IVisitor;
import com.samples.vertx.reactive.visitor.interfaces.IVisitorModel;

public class BatchRxResponse<T> extends BaseVisitorModelRxResp<T> implements IVisitorModel<T>{
	private List<Integer> batchResult;

	public List<Integer> getBatchResult() {
		return batchResult;
	}

	public void setBatchResult(List<Integer> batchResult) {
		this.batchResult = batchResult;
	}

	@Override
	public void accept(IVisitor<T> visitor) {
		visitor.visit(this);
	}
}

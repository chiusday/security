package com.samples.vertx.reactive.visitor.model;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.reactive.visitor.interfaces.IVisitor;
import com.samples.vertx.reactive.visitor.interfaces.IVisitorModel;

public class HistoricalTickerRxResponse extends BaseVisitorModelRxResp<HistoricalTicker> 
		implements IVisitorModel<HistoricalTicker> {

	@Override
	public void accept(IVisitor<HistoricalTicker> visitor) {
		visitor.visit(this);
	}
}

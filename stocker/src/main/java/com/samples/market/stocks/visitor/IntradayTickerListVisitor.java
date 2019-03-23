package com.samples.market.stocks.visitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samples.market.model.IntradayTicker;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteConvertibleResponse;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteConvertibleResponseVisitor;
import com.samples.market.stocks.visitor.model.IntradayTickerListVisitorModel;

@Service
public class IntradayTickerListVisitor 
		extends JsonQuoteConvertibleResponseVisitor<IntradayTicker> {
	
	private Logger log = LoggerFactory.getLogger(IntradayTickerListVisitor.class);

	@Override
	public void visit(IntradayTickerListVisitorModel visitorModel) {
		super.visit((JsonQuoteConvertibleResponse<IntradayTicker>)visitorModel);
		visitorModel.setResponseEntity
			(new ResponseEntity<>(visitorModel.getModel(), HttpStatus.OK));
		log.debug("IntradayTickerListVisitorModel successfully processed.");
	}
}

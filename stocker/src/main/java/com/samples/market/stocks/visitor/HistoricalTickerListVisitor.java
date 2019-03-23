package com.samples.market.stocks.visitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteConvertibleResponse;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteConvertibleResponseVisitor;
import com.samples.market.stocks.visitor.model.HistoricalTickerListVisitorModel;

@Service
public class HistoricalTickerListVisitor 
		extends JsonQuoteConvertibleResponseVisitor<HistoricalTicker> {
	
	private Logger log = LoggerFactory.getLogger(HistoricalTickerListVisitor.class);
	
	@Override
	public void visit(HistoricalTickerListVisitorModel visitorModel) {
		super.visit((JsonQuoteConvertibleResponse<HistoricalTicker>)visitorModel);
		visitorModel.setResponseEntity
			(new ResponseEntity<>(visitorModel.getModel(), HttpStatus.OK));
		log.debug("HistoricalTickerListVisitorModel successfully processed.");
	}
}

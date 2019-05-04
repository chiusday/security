package com.samples.vertx.reactive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.TickerRequestBySymbol;
import com.samples.vertx.reactive.interfaces.TickerBaseController;

@RestController()
public class HistoricalTickerController extends TickerBaseController<HistoricalTicker> {

	public HistoricalTickerController() {
		super(HistoricalTicker.class);
	}

	@PostMapping("/market-data/historical/add")
	public ResponseEntity<Object> addHistoricalTicker
			(@RequestBody HistoricalTicker ticker) {

		return super.addTicker(ticker);
	}
	
	@PostMapping("/market-data/historical")
	public ResponseEntity<Object> getHistoricalTicker
			(@RequestBody TickerRequestBySymbol request) {
		
		return super.getTicker(request);
	}

}

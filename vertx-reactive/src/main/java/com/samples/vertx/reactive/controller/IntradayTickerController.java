package com.samples.vertx.reactive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.market.model.IntradayTicker;
import com.samples.market.model.TickerRequestBySymbol;
import com.samples.vertx.reactive.interfaces.TickerBaseController;

@RestController
public class IntradayTickerController extends TickerBaseController<IntradayTicker>{

	public IntradayTickerController() {
		super(IntradayTicker.class);
	}

	@PostMapping("/market-data/intraday/add")
	public ResponseEntity<Object> addIntradayTicker
			(@RequestBody IntradayTicker ticker) {
		
		return addTicker(ticker);
	}
	
	@PostMapping("market-data/intraday")
	public ResponseEntity<Object> getIntradayTicker
			(@RequestBody TickerRequestBySymbol request) {

		return getTicker(request);
	}
}

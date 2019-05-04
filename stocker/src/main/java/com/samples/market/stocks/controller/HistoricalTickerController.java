package com.samples.market.stocks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.market.model.GetBySymbolRequest;
import com.samples.market.stocks.service.HistoricalTickerService;
import com.samples.market.stocks.visitor.model.HistoricalTickerListVisitorModel;

@RestController
public class HistoricalTickerController {
	@Autowired
	private HistoricalTickerService historicalTickerService;
	
	@PostMapping("/stock/historical")
	public ResponseEntity<Object> getJson(@RequestBody GetBySymbolRequest request){
		HistoricalTickerListVisitorModel historicalTickerVisitorModel = 
				historicalTickerService.getHistoricalTickers(request.getSymbol()); 
		
		return historicalTickerVisitorModel.getResponseEntity();
	}
}

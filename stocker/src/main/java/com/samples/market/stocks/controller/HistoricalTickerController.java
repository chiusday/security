package com.samples.market.stocks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/stock/list/{id}")
	public ResponseEntity<Object> getJsonList(@PathVariable String id){
		HistoricalTickerListVisitorModel historicalTickerVisitorModel = 
				historicalTickerService.getHistoricalTickers(id); 
		
		return historicalTickerVisitorModel.getResponseEntity();
	}
	
	@PostMapping("/stock/list")
	public ResponseEntity<Object> getJson(@RequestBody GetBySymbolRequest request){
		return getJsonList(request.getSymbol());
	}
}

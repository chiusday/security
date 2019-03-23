package com.samples.market.stocks.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.samples.market.stocks.service.HistoricalTickerService;
import com.samples.market.stocks.visitor.model.HistoricalTickerListVisitorModel;

import org.junit.Assert;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class TestHistoricalTickerService {
	@Autowired
	private HistoricalTickerService historicalTickerService;
	
	private String symbol = "MSFT";

	@Test
	public void testGetgetHistoricalTickers() {
		HistoricalTickerListVisitorModel visitorModel = 
				historicalTickerService.getHistoricalTickers(symbol);
		
		Assert.assertFalse(visitorModel.isHasError());
		Assert.assertEquals(HttpStatus.OK, 
				visitorModel.getResponseEntity().getStatusCode());
	}
}

package com.samples.market.stocks;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("returned-data")
public class Statics {
	private StaticsTimeSeries timeSeries;

	public StaticsTimeSeries getTimeSeries() {
		return timeSeries;
	}

	public void setTimeSeries(StaticsTimeSeries timeSeries) {
		this.timeSeries = timeSeries;
	}
}

package com.samples.market.stocks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.market.stocks.AppConfigDaily;

@Service
public class HistoricalCloudData extends CloudDataSource<AppConfigDaily> {
	@Autowired
	public AppConfigDaily appConfigDaily;

	@Override
	public void setAppConfig() {
		this.appConfig = appConfigDaily;
	}
}

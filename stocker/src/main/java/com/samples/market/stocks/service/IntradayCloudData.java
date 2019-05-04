package com.samples.market.stocks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.market.stocks.AppConfigIntraday;

@Service
public class IntradayCloudData extends CloudDataSource<AppConfigIntraday> {
	@Autowired
	private AppConfigIntraday appConfigIntra;
	
	@Override
	public void setAppConfig() {
		this.appConfig = appConfigIntra;
	}
	
}

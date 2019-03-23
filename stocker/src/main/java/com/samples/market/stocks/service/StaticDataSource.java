package com.samples.market.stocks.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.samples.market.stocks.interfaces.DataSource;

@Service
public class StaticDataSource implements DataSource {
	
	@Override
	public String getData() {
		Resource resource = new ClassPathResource("Quotes.txt");
		try {
			int len = Math.toIntExact(resource.contentLength());
			byte[] bytes = new byte[len];
			IOUtils.readFully(resource.getInputStream(), bytes);
			return new String(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

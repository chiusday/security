package com.security.utilities.hash.model;

import com.security.utilities.hash.model.HashBase;

public class HashResponse extends HashBase {
	private String hashedValue;
	
	public HashResponse() {}

	public HashResponse(String hashedValue) {
		this.hashedValue = hashedValue;
	}

	public String getHashedValue() {
		return hashedValue;
	}

	public void setHashedValue(String hashedValue) {
		this.hashedValue = hashedValue;
	}
	
}

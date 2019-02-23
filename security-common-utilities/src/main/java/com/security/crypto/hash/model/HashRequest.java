package com.security.crypto.hash.model;

import com.security.crypto.hash.model.HashBase;

public class HashRequest extends HashBase {
	public String originalText;
	
	public HashRequest() {}

	public HashRequest(String originalText) {
		this.originalText = originalText;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
	
}

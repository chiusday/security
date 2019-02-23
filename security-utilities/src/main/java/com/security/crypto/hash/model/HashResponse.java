package com.security.crypto.hash.model;

import com.security.crypto.hash.model.HashBase;

public class HashResponse extends HashBase {
	private String encryptedText;
	
	public HashResponse() {}

	public HashResponse(String encryptedText) {
		this.encryptedText = encryptedText;
	}

	public String getEncryptedText() {
		return encryptedText;
	}

	public void setEncryptedText(String encryptedText) {
		this.encryptedText = encryptedText;
	}
	
}

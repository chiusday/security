package com.security.utilities.hash.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.utilities.AppConfig;

@Service
public class HashService {
	@Autowired
	private AppConfig config;

	public String hash(String text) throws NoSuchAlgorithmException {
		return Base64.getEncoder().encodeToString(hashToBytes(text));
	}
	
	public byte[] hashToBytes(String text) throws NoSuchAlgorithmException {
		MessageDigest digester = MessageDigest.getInstance(config.getShaAlgo());
		return digester.digest(text.getBytes(StandardCharsets.UTF_8));
	}
}


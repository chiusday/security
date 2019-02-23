package com.samples.vertx.reactive;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.samples.vertx.reactive.verticle.DataAccessInterchange;

import io.vertx.core.VertxOptions;
import io.vertx.reactivex.core.Vertx;

@SpringBootApplication
public class AppVertxReactive {
	@Autowired
	private DataAccessInterchange dataAccessInterchange;

	public static void main(String... args) {
		SpringApplication.run(AppVertxReactive.class, args);
	}
	
	@PostConstruct
	public void deployVerticles() {
		VertxOptions options = new VertxOptions();
		options.setBlockedThreadCheckInterval(1000*60*60);
		final Vertx vertx = Vertx.vertx(options);
		vertx.deployVerticle(dataAccessInterchange);
	}
}

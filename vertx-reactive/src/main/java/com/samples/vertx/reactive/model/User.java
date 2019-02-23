package com.samples.vertx.reactive.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import io.vertx.core.json.JsonObject;

public class User extends com.samples.vertx.model.User {
	@JsonAlias("ID")
	protected Long id;
	@JsonAlias("NAME")
	protected String name;
	@JsonAlias("GROUPID")
	protected int groupId;
	@JsonAlias("PASSWORD")
	protected String password;
	
	public User(){}
	
	public User(JsonObject json){
		this.id = json.getLong("ID");
		this.name = json.getString("NAME");
		this.groupId = json.getInteger("GROUPID");
		this.password = json.getString("PASSWORD");
	}
}

package com.samples.vertx.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.vertx.enums.DBOperations;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.AppConfig;
import com.samples.vertx.reactive.model.User;
import com.samples.vertx.reactive.verticle.DataAccessInterchange;
import com.samples.vertx.reactive.visitor.model.RxResponse;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.core.eventbus.Message;

@Service
public class UserService {
	@Autowired
	private AppConfig appConfig;
	@Autowired
	private DataAccessInterchange dataAccessInterchange;
	
	public RxResponse<User> add(User user) {
		DataAccessMessage<User> userDAMessage = new DataAccessMessage<>(User.class);
		userDAMessage.setModel(user);
		userDAMessage.setOperation(DBOperations.insert);
		
		return processUser(userDAMessage);
	}
	
	public RxResponse<User> get(int id) {
		DataAccessMessage<User> userDAMessage = new DataAccessMessage<User>(User.class);
		userDAMessage.setCriteria("id=?");
		userDAMessage.setParameters(new JsonArray().add(id));
		userDAMessage.setOperation(DBOperations.select);
		
		return processUser(userDAMessage);
	}
	
	public RxResponse<User> update(User user) {
		DataAccessMessage<User> userDAMessage = new DataAccessMessage<>(User.class);
		userDAMessage.setModel(user);
		userDAMessage.setOperation(DBOperations.update);
		
		return processUser(userDAMessage);
	}
	
	public RxResponse<User> delete(User user) {
		DataAccessMessage<User> userDAMessage = new DataAccessMessage<>(User.class);
		userDAMessage.setModel(user);
		userDAMessage.setOperation(DBOperations.delete);
		
		return processUser(userDAMessage);
	}
	
	private RxResponse<User> processUser(DataAccessMessage<User> userDAMessage) {
		RxResponse<User> userDataResponse = new RxResponse<>();
		EventBus eventBus = dataAccessInterchange.getRxVertx().eventBus();
		Single<Message<JsonObject>> response = eventBus.<JsonObject>rxSend
				(appConfig.getAddressUser(), JsonObject.mapFrom(userDAMessage));
		userDataResponse.setSingle(response);
		
		return userDataResponse;
	}
}

package com.samples.vertx.reactive.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.DBConfig;
import com.samples.vertx.reactive.interfaces.VertxSQLDataAccess;
import com.samples.vertx.reactive.model.User;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.reactivex.core.eventbus.Message;

@Component
public class UserDataAccess extends VertxSQLDataAccess<User> {
	private Logger log = LoggerFactory.getLogger(UserDataAccess.class);

	public UserDataAccess(DBConfig config) {
		super(User.class, config);
	}

	@Override
	protected String getTableName() {
		return "User";
	}
	
	@Override
	protected String getInsertSql() {
		return "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?)";
	}
	
	@Override
	public JsonArray toJsonArray(User model) {
		JsonArray reply = new JsonArray();
		JsonObject.mapFrom(model).stream().forEach(field -> {
			if (field.getValue() != null) reply.add(field.getValue());
		});
		return reply;
	}

	@Override
	public JsonArray noKeyJsonArray(User model) {
		JsonArray array = toJsonArray(model);
		array.remove(0);
		return array;
	}

	@Override
	public void executeCreate() {
		String s = "CREATE TABLE IF NOT EXISTS "+ getTableName() 
		+" (id BIGINT IDENTITY, name VARCHAR(100), " 
		+ "groupId INTEGER, password VARCHAR(32))";
		
		this.jdbc.rxGetConnection()
			.flatMap(conn -> {
				Single<UpdateResult> result = conn.rxUpdate(s);
				return result.doAfterTerminate(conn::close);
			})
			.subscribe(result -> {
				log.info("Create table " +getTableName()+ "\nResult >> " 
						+ JsonObject.mapFrom(result).encode());
			}, error -> {
				log.error("Error creating table "+getTableName()+"\n"+error.getMessage());
			});
	}

	@Override
	public void insert(Message<JsonObject> message) {
		DataAccessMessage<User> userMessage = new DataAccessMessage<>(message.body());
		User user = userMessage.getModel();
		insert(user, next -> {
			if (isTransactionFailed(next, userMessage) == false){
				userMessage.setModel(next.result());
			}
			message.reply(JsonObject.mapFrom(userMessage));
		});
	}

	@Override
	public void batchInsert(Message<JsonObject> message) {
		DataAccessMessage<User> userMessage = new DataAccessMessage<>(message.body());
		List<JsonArray> batchParams = userMessage.getListJsonArray();
		batchInsert(batchParams, next -> {
			if (isTransactionFailed(next, userMessage) == false) {
				userMessage.setBatchResult(next.result());
			}
			message.reply(userMessage);
		});
		
	}
	
	@Override
	public void select(Message<JsonObject> message) {
		DataAccessMessage<User> userMessage = new DataAccessMessage<>(message.body());
		select(userMessage.getCriteria(), userMessage.getParameters(), next -> {
			if (isTransactionFailed(next, userMessage) == false){
				userMessage.setRecords(next.result());
			}
			DataAccessMessage<User> replyMessage = new DataAccessMessage<>(message.body());
			replyMessage.setRecords(userMessage.getRecords());
			message.reply(JsonObject.mapFrom(replyMessage));
		});
	}

	@Override
	public void update(Message<JsonObject> message) {
		DataAccessMessage<User> msgUser = new DataAccessMessage<>(message.body());
		msgUser.setKey(msgUser.getModel().getId().toString());
		String sql = "UPDATE "+getTableName()+" SET "
				+ "name=?, groupId=?, password=? "
				+ "WHERE id="+msgUser.getKey();
		update(msgUser.getKey(), sql, msgUser.getModel(), next -> {
			if (isTransactionFailed(next, msgUser) == false) {
				msgUser.setModel(next.result());
			}
			message.reply(JsonObject.mapFrom(msgUser));
		});
	}

	@Override
	public void delete(Message<JsonObject> message) {
		DataAccessMessage<User> msgUser = new DataAccessMessage<>(message.body());
		msgUser.setCriteria("id="+msgUser.getModel().getId().toString());
		
		delete("id=?", new JsonArray().add(msgUser.getModel().getId().toString()), 
			next -> {
				if (isTransactionFailed(next, msgUser) == false) {
					msgUser.setAffectedRecords(next.result());
				}
				message.reply(JsonObject.mapFrom(msgUser));
			});
	}

}

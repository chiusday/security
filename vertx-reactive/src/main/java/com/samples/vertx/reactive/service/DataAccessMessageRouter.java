package com.samples.vertx.reactive.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.enums.DBOperations;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.interfaces.IVertxSQLDataAccess;
import com.samples.vertx.reactive.model.User;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

@Service
public class DataAccessMessageRouter {
	private Logger log = LoggerFactory.getLogger(DataAccessMessageRouter.class);


	@Autowired
	private IVertxSQLDataAccess<User> userDataAccess;
	@Autowired
	private IVertxSQLDataAccess<HistoricalTicker> historicalTickerDAO;
	
	private Map<Class<?>, IVertxSQLDataAccess<?>> operators = 
			new ConcurrentHashMap<Class<?>, IVertxSQLDataAccess<?>>();
	
	public DataAccessMessageRouter(){ 
		super();
	}
	
	/**
	 * Builds the list of VertxDataAccess<? extends BaseModel> implementors 
	 * that will be available as the target of message routing.
	 * Establish data connections. Start all VertxDataAccess<> that can be routed.
	 * @param vertx
	 * 		Vertx context
	 */
	public void setDataConnections(final Vertx vertx){
		//build list of data access implementors
		operators.put(User.class, userDataAccess);
		operators.put(HistoricalTicker.class, historicalTickerDAO);

		//establish db connection for each
		//this a good use case for an observable interface
		operators.values().forEach(daImplementor -> {
			daImplementor.startBackend(vertx);
			daImplementor.executeCreate();
		});
	}

	public void routeMessage(final Message<JsonObject> message){
		DataAccessMessage<?> daMessage = new DataAccessMessage<>(message.body());
		DBOperations operation = daMessage.getOperation();
		switch(operation) {
		case select : 
			operators.get(daMessage.getType()).select(message);
			break;
		case insert : 
			operators.get(daMessage.getType()).insert(message);
			break;
		case batchInsert : 
			operators.get(daMessage.getType()).batchInsert(message);
			break;
		case update :
			operators.get(daMessage.getType()).update(message);
			break;
		case delete :
			operators.get(daMessage.getType()).delete(message);
			break;
		default :
			String error = operation.name() + " operation handler is not found";
			log.error(error);
			daMessage.setFailure(new JsonObject().put("message", error));
			message.reply(JsonObject.mapFrom(daMessage));
		}
	}
	
}

package com.samples.vertx.reactive.interfaces;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.DBConfig;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.sql.SQLConnection;
import io.vertx.reactivex.ext.sql.SQLRowStream;
import io.vertx.core.Future;
import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;

public abstract class VertxSQLDataAccess<T> implements IVertxSQLDataAccess<T> {
	private Logger log = LoggerFactory.getLogger(VertxSQLDataAccess.class);
	private final String SELECT_PREFIX;
	private final String DELETE_PREFIX;
	
	protected final Class<T> type;
	protected JDBCClient jdbc;
	protected JsonObject config;
	
	public VertxSQLDataAccess(Class<T> type, DBConfig config){
		super();
		this.type = type;
		this.config = JsonObject.mapFrom(config);
		this.SELECT_PREFIX = "SELECT * from " + getTableName() + " WHERE ";
		this.DELETE_PREFIX = "DELETE from " + getTableName() + " WHERE ";
	}
	
	public Class<T> getType(){
		return this.type;
	}
	
	protected abstract String getTableName();
	
	protected abstract String getInsertSql();

	/**
	 *  
	 *  Returns io.vertx.core.json.JsonArray containing all the fields of the model.
	 * @param T
	 * @return JsonArray
	 */
	public abstract JsonArray toJsonArray(T model);
	
	public abstract  JsonArray noKeyJsonArray(T model); 

	public void insert(T model,  Handler<AsyncResult<T>> next) {
		this.jdbc.getConnection(asyncConn -> {
			SQLConnection connection = asyncConn.result();
			connection.updateWithParams(getInsertSql(), toJsonArray(model), (result) -> {
				if (result.failed()){
					next.handle(Future.failedFuture(result.cause()));
				} else {
					next.handle(Future.succeededFuture(model));
				}
				connection.close();
			});
		});
	}
	
	public void batchInsert(List<JsonArray> batchParams, Handler<AsyncResult<List<Integer>>> next) {
		this.jdbc.getConnection(asyncConn -> {
			SQLConnection connection = asyncConn.result();
			connection.batchWithParams(getInsertSql(), batchParams, result -> {
				if (result.failed()) {
					next.handle(Future.failedFuture(result.cause()));
				} else {
					next.handle(Future.succeededFuture(result.result()));
				}
			});
			connection.close();
		});
	}

	/**
	 * Deletes multiple records based on the criteria Statement passed
	 * @param criteria - WHERE clause criteria to execute with '?' place holder for values. 
	 * @param parameters - JsonArray containing the values to replace '?' in the criteria statement. 
	 * 	Sequence is strictly followed.
	 * @param next - Async Result
	 * 
	 */
	public void delete(String criteria, JsonArray parameters, Handler<AsyncResult<Integer>> next) {
		this.jdbc.getConnection(conn -> {
			SQLConnection connection = conn.result();
			
			connection.updateWithParams(DELETE_PREFIX + criteria, parameters, 
					(asyncResult) -> {
						this.deleteResponse(asyncResult, connection, next);
					});
		});
		
	}
	
	private void deleteResponse(AsyncResult<UpdateResult> updateResult, SQLConnection connection,
			Handler<AsyncResult<Integer>> next){
		if(updateResult.failed()){
			next.handle(Future.failedFuture(updateResult.cause()));
			connection.close();
			return;
		}
		
		if (updateResult.result().getUpdated()==0){
			next.handle(Future.failedFuture("0 rows were updated"));
			connection.close();
			return;
		}
		next.handle(Future.succeededFuture(updateResult.result().getUpdated()));
		connection.close();
	}

	public void select(String criteria, JsonArray parameters, Handler<AsyncResult<List<JsonObject>>> next) {
		this.jdbc.getConnection(asyncConn -> {
			SQLConnection connection = asyncConn.result();
			connection.queryWithParams(SELECT_PREFIX + criteria, parameters, result -> {
				if (result.failed()){
					next.handle(Future.failedFuture(result.cause()));
				} else {
					next.handle(Future.succeededFuture(result.result().getRows()));
				}
				connection.close();
			});
		});
	}
	
	public void selectStreamedResult(String criteria, JsonArray parameters, Handler<AsyncResult<List<JsonArray>>> next){
		this.jdbc.getConnection(asyncConn -> {
			if (asyncConn.failed()){
				next.handle(Future.failedFuture(asyncConn.cause()));
				return;
			}
			
			SQLConnection connection = asyncConn.result();
			connection.queryStreamWithParams(criteria, parameters, stream ->{
				if (stream.failed()){
					next.handle(Future.failedFuture(stream.cause()));
					connection.close();
					return;
				}
				
				SQLRowStream sqlRows = stream.result();
				List<JsonArray> resultData = new LinkedList<JsonArray>();
				sqlRows.handler(row -> {
					resultData.add(row);
				}).endHandler(v -> {
					connection.close(done -> {
						if(done.failed()){
							next.handle(Future.failedFuture(done.cause()));
							return;
						}
						next.handle(Future.succeededFuture(resultData));
					});
				});
			});
		});
	}


	public void update(String key, String criteria, T model, Handler<AsyncResult<T>> next) {
		this.jdbc.getConnection(asyncConn -> {
			SQLConnection connection = asyncConn.result();
			connection.updateWithParams(criteria, noKeyJsonArray(model), result -> {
				if (result.failed()){
					next.handle(Future.failedFuture(result.cause()));
				} else if (result.result().getUpdated() == 0){
					next.handle(Future.failedFuture("Cannot find Process " + key));
				} else {
					next.handle(Future.succeededFuture(model));
				}
				connection.close();
			});
		});
	}
	
	public void callStoredProcedure(String callStatement, JsonArray in, JsonArray out, Handler<AsyncResult<ResultSet>> next){
		this.jdbc.getConnection(asyncConn -> {
			if (asyncConn.failed()){
				next.handle(Future.failedFuture(asyncConn.cause()));
				return;
			}
			
			SQLConnection connection = asyncConn.result();
			connection.callWithParams(callStatement, in, out, callResult -> {
				if (callResult.failed()){
					next.handle(Future.failedFuture(callResult.cause()));
					connection.close();
					return;
				}
				
				next.handle(Future.succeededFuture(callResult.result()));
				connection.close();
			});
		});
	}
	
	public void startBackend(Vertx vertx){
		this.jdbc = JDBCClient.createNonShared(vertx, this.config);

		Single<SQLConnection> conn = this.jdbc.rxGetConnection();
		if (conn==null) {
			System.out.println("JDBC connection failed");
		} else {
			System.out.println("JDBC connection successfully established");
		}
	}
	
	public void cleanUp(){
	}

	@SuppressWarnings("rawtypes")
	protected boolean isTransactionFailed(AsyncResult next, DataAccessMessage<T> daMessage){
		if (next.failed()){
			log.error(next.cause().getMessage());
			//failure json can be standardized
			daMessage.setFailure(new JsonObject().put(DataAccessMessage.FAILURE_MESSAGE, 
					next.cause().getMessage()));
		}
		return next.failed();
	}
}

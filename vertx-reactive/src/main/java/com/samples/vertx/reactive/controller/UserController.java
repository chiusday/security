package com.samples.vertx.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.samples.vertx.reactive.model.User;
import com.samples.vertx.reactive.service.UserService;
import com.samples.vertx.reactive.visitor.UserAddResponseVisitor;
import com.samples.vertx.reactive.visitor.UserDeleteResponseVisitor;
import com.samples.vertx.reactive.visitor.UserGetResponseVisitor;
import com.samples.vertx.reactive.visitor.UserUpdateResponseVisitor;
import com.samples.vertx.reactive.visitor.model.RxResponse;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAddResponseVisitor userAddResponseVisitor;
	
	@Autowired
	private UserGetResponseVisitor userGetResponseVisitor;
	
	@Autowired
	private UserUpdateResponseVisitor userUpdateResponseVisitor;
	
	@Autowired
	private UserDeleteResponseVisitor userDeleteResponseVisitor;
	
	@PostMapping("/user")
	public ResponseEntity<Object> insertUser(@RequestBody User user){
		RxResponse<User> userOpResponse = userService.add(user);
		userOpResponse.accept(userAddResponseVisitor);
		
		return userOpResponse.getResponseEntity();
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getUser(@PathVariable int id){
		RxResponse<User> userOpResponse = userService.get(id);
		userOpResponse.accept(userGetResponseVisitor);
		
		return userOpResponse.getResponseEntity();
	}
	
	@PutMapping("/user")
	public ResponseEntity<Object> update(@RequestBody User user){
		RxResponse<User> userOpResponse = userService.update(user);
		userOpResponse.accept(userUpdateResponseVisitor);
		
		return userOpResponse.getResponseEntity();
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Object> delete(@PathVariable int id){
		User user = new User();
		user.setId(new Long(id));
		RxResponse<User> userOpResponse = userService.delete(user);
		userOpResponse.accept(userDeleteResponseVisitor);
		
		return userOpResponse.getResponseEntity();
	}
}

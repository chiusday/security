package com.samples.vertx.reactive.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.samples.common.exception.model.dto.BaseExceptionResponse;
import com.samples.common.exception.model.dto.DataNotFoundException;

@RestController
@ControllerAdvice
public class SamplesExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(DataNotFoundException.class)
	public final ResponseEntity<Object> handleAllException
			(DataNotFoundException ex, WebRequest req) {
		
		BaseExceptionResponse exceptionResponse = new BaseExceptionResponse
				(ex.getMessage(), req.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, ex.getStatus());
	}
}

package ua.ferret.client.controller;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class ExceptionController {

	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class, JwtException.class})
	public Object handler(RuntimeException exception, HttpServletRequest request) {
		log.warn("[{}] {}: {}", request.getRemoteAddr(), exception.getClass().getSimpleName(), exception.getMessage());
		return ResponseEntity.status(418).build();
	}
	
}

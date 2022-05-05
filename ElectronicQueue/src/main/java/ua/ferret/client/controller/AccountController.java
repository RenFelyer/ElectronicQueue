package ua.ferret.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.ferret.client.service.AccountService;
import ua.ferret.client.service.TokenService;

@RestController
public class AccountController {
	
	private @Autowired AccountService accountS;
	private @Autowired TokenService tokenS;

	@GetMapping("/login")
	public Object login(HttpServletRequest request) {
		var account = accountS.searchAccount(request);
		var token = tokenS.createToken(account);
		return ResponseEntity.of(token);
	}

	@GetMapping("/registration")
	public Object registration(HttpServletRequest request) {
		var account = accountS.createAccount(request);
		var token = tokenS.createToken(account);
		return ResponseEntity.of(token);
	}
	
	@GetMapping("/validation")
	public Object validation(HttpServletRequest request) {
		var claims = tokenS.parseToken(request);
		var account = accountS.validationAccount(claims);
		return ResponseEntity.of(account);
	}
	
	@GetMapping("/edit")
	public Object edit(HttpServletRequest request) {
		var claims = tokenS.parseToken(request);
		var account = accountS.validationAccount(claims);
		account = accountS.edit(request, account);
		return ResponseEntity.of(account);
	}

}

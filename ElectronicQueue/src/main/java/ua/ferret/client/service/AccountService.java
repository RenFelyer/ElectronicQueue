package ua.ferret.client.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import ua.ferret.client.model.Account;
import ua.ferret.client.repository.AccountRepository;

@Service
public class AccountService {
	
	private @Autowired PasswordEncoder encoder;
	private @Autowired AccountRepository repository;
	
	public Optional<Account> createAccount(HttpServletRequest request) {
		Account account = new Account();
		Optional.of("phone").map(request::getParameter).ifPresent(account::setPhone);
		Optional.of("nickname").map(request::getParameter).ifPresent(account::setNickname);
		Optional.of("password").map(request::getParameter).ifPresent(account::setPassword);
		return Optional.of(account).filter(this::checkAccount).map(repository::save);
	}
	
	public Optional<Account> searchAccount(HttpServletRequest request) {
		return Optional.of("phone").map(request::getParameter).map(repository::findByPhone).orElseGet(Optional::empty).filter(account->
			Optional.of("password").map(request::getParameter).filter(value->encoder.matches(value, account.getPassword())).isPresent());
	}
	
	public Optional<Account> validationAccount(Optional<Claims> claims){
		return claims.map(value->{
			String phone = value.get("phone", String.class);
			String password = value.get("password", String.class);
			return repository.findByPhone(phone).filter(account->account.getPassword().equals(password));
		}).orElseGet(Optional::empty);
	}
	
	public Optional<Account> edit(HttpServletRequest request, Optional<Account> account) {
		return account.filter(value->{
			Optional.of("nickname").map(request::getParameter).ifPresent(value::setNickname);
			return true;
		}).map(repository::save);
	}
	
	private boolean checkAccount(Account account) {
		String phone = account.getPhone();
		String password = account.getPassword();
		
		if(repository.existsByPhone(phone))
			throw new IllegalArgumentException("User with this number is already registered");
		
		if(phone == null || phone.isBlank())
			throw new NoSuchElementException("Phone number data cannot be missing");

		if(password == null || password.isBlank())
			throw new NoSuchElementException("Password information cannot be missing");

		account.setPassword(encoder.encode(password));
		return true;
	}

}
